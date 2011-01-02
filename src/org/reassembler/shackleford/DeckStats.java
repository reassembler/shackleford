package org.reassembler.shackleford;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.reassembler.shackleford.model.Card;
import org.reassembler.shackleford.model.CardAdaptor;
import org.reassembler.shackleford.model.Deck;

import java.util.List;

public class DeckStats extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.deck_stats);

        ListView listView = (ListView) findViewById(R.id.lstCards);

        final CardAdaptor cards = new CardAdaptor(this);

        listView.setAdapter(cards);
        final Deck deck = new Deck();

        runOnUiThread(new Runnable() {
            public void run() {
                while (deck.hasCard()) {
                    cards.addItem(deck.getNextCard());
                    System.out.println(deck);
                }

                if (cards.getCount() != 44) {
                    Toast.makeText(DeckStats.this, "Deck Is Bad: wrong size: " + cards.getCount(), Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    List<Integer> l = Deck.getCardIds();
                    for (Card card : cards.getItems()) {
                        l.remove((Object) card.getResourceId());
                    }

                    if (l.size() > 0) {
                        Toast.makeText(DeckStats.this, "Deck Is Bad: Missing Cards", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(DeckStats.this, "Deck Is Good", Toast.LENGTH_LONG).show();
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    Toast.makeText(DeckStats.this, "Deck Is Bad: Too Many Cards: " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView view = (TextView) findViewById(R.id.lblDeckInfo);
        view.setText(deck.toString());
    }
}