package org.reassembler.shackleford;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.reassembler.shackleford.model.Card;
import org.reassembler.shackleford.model.Deck;
import org.reassembler.syn.AppTask;
import org.reassembler.syn.ClickTask;
import org.reassembler.syn.log.LogFactory;
import org.reassembler.syn.log.Logger;

import java.io.IOException;

public class Deal extends Activity {
    private Logger log = LogFactory.getLogger(Deal.class.getName());
    private ImageView mCardView;
    private Deck deck;
    private long lastClickTime = System.currentTimeMillis();
    private long minTimeBetweenCards = 2000L;
    private ImageView mLed;
    private TextView mCardText;
    private int lastId;
    public static final String PREFERENCES_KEY = "org.reassembler.shackleford";
    public static final String TIME_BETWEEN_CARDS_KEY = "timeBetweenCards";

    private AppTask nextCard = new AppTask() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long clickTime = currentTime - lastClickTime;

            if (clickTime < minTimeBetweenCards) {
                return;
            }

            lastClickTime = System.currentTimeMillis();

            if (deck == null || !deck.hasCard()) {
                Toast.makeText(Deal.this, "New deck", Toast.LENGTH_SHORT).show();
                deck = new Deck();
                lastId = 0;
            }

            Card card = deck.getNextCard();
            int id = card.getResourceId();

            if (lastId == id) {
                Toast.makeText(Deal.this, "Duplicate", Toast.LENGTH_LONG).show();
                playDupeSound.run();
            }

            lastId = id;

            log.d("showing id: " + id);

            Animation cardFadeOut = new AlphaAnimation(1, .5F);
            cardFadeOut.setDuration(500);
            mCardView.setAnimation(cardFadeOut);
            cardFadeOut.startNow();


            mCardView.setImageResource(id);

            Animation cardFadeIn = new AlphaAnimation(.5f, 1f);
            cardFadeIn.setDuration(500);
            mCardView.setAnimation(cardFadeIn);
            cardFadeIn.start();

            mLed.setImageResource(R.drawable.red);
            Animation animation = new AlphaAnimation(1.0f, 0f);
            animation.setDuration(minTimeBetweenCards);
            mLed.setAnimation(animation);
            animation.start();

            mCardText.setText(String.format("%d cards left", deck.getCardsLeft()));
            animation = new AlphaAnimation(1.0f, 0f);
            animation.setDuration(1000);
            mCardText.setAnimation(animation);

            animation.start();

        }
    };


    private AppTask playDupeSound = new AppTask() {
        @Override
        public void run() {
            log.d("dupe");
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(Deal.this, alert);
                final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int sound = AudioManager.STREAM_RING;
                if (audioManager.getStreamVolume(sound) != 0) {
                    player.setAudioStreamType(sound);
                    player.setLooping(false);
                    player.prepare();
                    player.start();
                    player.stop();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.deal);

        mCardView = (ImageView) findViewById(R.id.imgCard);
        mCardView.setImageResource(R.drawable.deck);
        mLed = (ImageView) findViewById(R.id.imgLed);
        mLed.setVisibility(View.INVISIBLE);
        mCardText = (TextView) findViewById(R.id.txtOverLay);
        mCardText.setVisibility(View.INVISIBLE);

        new ClickTask(R.id.imgCard, this, nextCard);
    }



    private void loadPreferences() {
        System.out.println("LOADING PREFS");
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);
        minTimeBetweenCards = prefs.getInt(TIME_BETWEEN_CARDS_KEY, 2) * 1000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.deal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newDeck:
                deck = null;
                mCardView.setImageResource(R.drawable.deck);

                break;

            case R.id.viewStats:
                Intent intent = new Intent(this, DeckStats.class);
                startActivity(intent);
                break;

            case R.id.editPrefs:
                startActivity(new Intent(this, Preferences.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}