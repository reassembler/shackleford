package org.reassembler.shackleford.model;

import org.reassembler.shackleford.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {

    private Random random = new Random(System.currentTimeMillis());

    private List<Integer> cards = new ArrayList<Integer>();

    public Deck() {
        int[] cardIds = new int[]{
                R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.five,
                R.drawable.seven,
                R.drawable.eight,
                R.drawable.ten,
                R.drawable.eleven,
                R.drawable.twelve,
                R.drawable.sorry,
        };

        for (int cardId : cardIds) {
            for (int i = 0; i < 4; i++) {
                cards.add(cardId);
            }
        }
    }

    public boolean hasCard() {
        return cards.size() > 0;
    }

    public int getNextCardId() {
        return cards.remove(random.nextInt(cards.size()));
    }

    public int getCardsLeft() {
        return cards.size();
    }
}
