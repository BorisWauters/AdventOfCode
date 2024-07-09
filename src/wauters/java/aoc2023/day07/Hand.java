package com.github.aoc2023.day07;

import java.util.EnumMap;
import java.util.Map;

public class Hand implements Comparable<Hand>{
    private final Card[] cards;
    private HandType handType;

    private final int bet;

    public Hand(String hand, int bet) {
        this.cards = new Card[5];
        this.bet = bet;
        for (int i = 0; i < 5; i++) {
            this.cards[i] = Card.valueOf(hand.charAt(i));
        }
        setHandType();
    }

    public int getBet() {
        return bet;
    }

    @Override
    public int compareTo(Hand o) {
        if (this.handType != o.handType) {
            return Integer.compare(this.handType.ordinal(), o.handType.ordinal());
        }

        for (int i = 0; i < this.cards.length; i++) {
            if (this.cards[i].getValue() != o.cards[i].getValue()) {
                return Integer.compare(this.cards[i].ordinal(), o.cards[i].ordinal());
            }
        }
        return 0;
    }

    private void setHandType() {
        Map<Card, Integer> cardCount = new EnumMap<>(Card.class);

        for (Card card : cards) {
            cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
        }

        if (cardCount.containsValue(5)) {
            this.handType = HandType.FIVE_OF_A_KIND;
        } else if (cardCount.containsValue(4)) {
            this.handType = HandType.FOUR_OF_A_KIND;
        } else if (cardCount.containsValue(3) && cardCount.containsValue(2)) {
            this.handType = HandType.FULL_HOUSE;
        } else if (cardCount.containsValue(3)) {
            this.handType = HandType.THREE_OF_A_KIND;
        } else if (cardCount.containsValue(2)) {
            int pairCount = 0;
            for (int count : cardCount.values()) {
                if (count == 2) {
                    pairCount++;
                }
            }
            if (pairCount == 2) {
                this.handType = HandType.TWO_PAIRS;
            } else {
                this.handType = HandType.ONE_PAIR;
            }
        } else {
            this.handType = HandType.HIGH_CARD;
        }
    }

    enum Card {
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
        JACK('J'),
        QUEEN('Q'),
        KING('K'),
        ACE('A');

        private final char value;

        Card(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }

        public static Card valueOf(char value) {
            for (Card card : Card.values()) {
                if (card.getValue() == value) {
                    return card;
                }
            }
            throw new IllegalArgumentException("Invalid card value: " + value);
        }
    }
}
