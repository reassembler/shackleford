package org.reassembler.shackleford;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.reassembler.shackleford.model.Deck;
import org.reassembler.syn.AppTask;
import org.reassembler.syn.ClickTask;
import org.reassembler.syn.log.LogFactory;
import org.reassembler.syn.log.Logger;

public class Deal extends Activity {
    private Logger log = LogFactory.getLogger(Deal.class.getName());
    private ImageView mCardView;
    private Deck deck;
    private long lastClickTime = System.currentTimeMillis();
    private long minTimeBetweenCards = 2000L;
    private ImageView mLed;
    private TextView mCardText;

    private AppTask nextCard = new AppTask() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long clickTime = currentTime - lastClickTime;

            if (clickTime < minTimeBetweenCards) {
                //mOverlayText.setText("Too Soon");
                return;
            }

            lastClickTime = System.currentTimeMillis();

            if (deck == null || !deck.hasCard()) {
                Toast.makeText(Deal.this, "New deck", Toast.LENGTH_SHORT).show();
                deck = new Deck();
                mCardText.setVisibility(View.VISIBLE);
            }

            int id = deck.getNextCardId();

            log.d("showing id: " + id);

            mCardView.setImageResource(id);

            mLed.setVisibility(View.VISIBLE);
            mLed.setImageResource(R.drawable.red);

            mCardText.setText(String.format("%d cards left", deck.getCardsLeft()));

            mLed.postDelayed(startTimer, minTimeBetweenCards);
        }
    };

    public AppTask startTimer = new AppTask() {
        @Override
        public void run() {
            mLed.setVisibility(View.INVISIBLE);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.deal);

        mCardView = (ImageView) findViewById(R.id.imgCard);
        mCardView.setImageResource(R.drawable.deck);
        mLed = (ImageView) findViewById(R.id.imgLed);
        mLed.setAlpha(200);
        mCardText = (TextView) findViewById(R.id.txtOverLay);
        mCardText.setVisibility(View.INVISIBLE);

        new ClickTask(R.id.imgCard, this, nextCard);
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
                mCardText.setVisibility(View.INVISIBLE);
                deck = null;
                mCardView.setImageResource(R.drawable.deck);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}