package org.reassembler.shackleford.model;

import org.reassembler.shackleford.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private Random random = new Random(System.currentTimeMillis());

    private List<Card> cards = new ArrayList<Card>();

    private static int[] cardIds = new int[]{
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


    public Deck() {
        int id = 1;

        for (int cardId : cardIds) {
            for (int i = 0; i < 4; i++) {
                Card card = new Card(id++, cardId);
                cards.add(card);
            }
        }

        // now shuffle
        List<Card> tempDeck = new ArrayList<Card>();
        for (int i = 0; i < 10; i++) {
            while (cards.size() > 0) {
                tempDeck.add(cards.remove(random.nextInt(cards.size())));
            }

            cards.addAll(tempDeck);
            tempDeck.clear();
        }
    }

    public static List<Integer> getCardIds() {
        List<Integer> l = new ArrayList<Integer>();
        for (Integer i : cardIds) {
            for (int j = 0; i < 4; i++) {
                l.add(i);
            }
        }

        return l;
    }

    public boolean hasCard() {
        return cards.size() > 0;
    }

    public Card getNextCard() {
        return cards.remove(random.nextInt(cards.size()));
    }

    public int getCardsLeft() {
        return cards.size();
    }
}
