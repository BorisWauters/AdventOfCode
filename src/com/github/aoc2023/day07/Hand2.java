package com.github.aoc2023.day07;

import java.util.EnumMap;
import java.util.Map;

public class Hand2 implements Comparable<Hand2>{
    private final Card[] cards;
    private HandType handType;

    private final int bet;

    public Hand2(String hand, int bet) {
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
    public int compareTo(Hand2 o) {
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
        int jokers = 0;

        for (Card card : cards) {
            if (card == Card.JOKER) {
                jokers++;
            } else {
                cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
            }
        }

        this.handType = getHandType(cardCount, jokers);
    }

    private HandType getHandType(Map<Card, Integer> cardCount, int jokers) {
        if (cardCount.containsValue(5) || jokers >= 4) {
            return HandType.FIVE_OF_A_KIND;
        }

        if (cardCount.containsValue(4)) {
            if (jokers == 1) {
                return HandType.FIVE_OF_A_KIND;
            }
            return HandType.FOUR_OF_A_KIND;
        }

        if (cardCount.containsValue(3) && cardCount.containsValue(2)) {
            return HandType.FULL_HOUSE;
        }

        if (cardCount.containsValue(3)) {
            if (jokers == 2) {
                return HandType.FIVE_OF_A_KIND;
            }
            if (jokers == 1) {
                return HandType.FOUR_OF_A_KIND;
            }
            return HandType.THREE_OF_A_KIND;
        }

        if (cardCount.containsValue(2)) {
            int pairCount = 0;
            for (int count : cardCount.values()) {
                if (count == 2) {
                    pairCount++;
                }
            }

            if (pairCount == 2) {
                if (jokers == 1) {
                    return HandType.FULL_HOUSE;
                }

                return HandType.TWO_PAIRS;
            }

            if (jokers == 3) {
                return HandType.FIVE_OF_A_KIND;
            }

            if (jokers == 2) {
                return HandType.FOUR_OF_A_KIND;
            }

            if (jokers == 1) {
                return HandType.THREE_OF_A_KIND;
            }

            return HandType.ONE_PAIR;

        }

        if (jokers == 3) {
            return HandType.FOUR_OF_A_KIND;
        }
        if (jokers == 2) {
            return HandType.THREE_OF_A_KIND;
        }
        if (jokers == 1) {
            return HandType.ONE_PAIR;
        }
        return HandType.HIGH_CARD;
    }

    enum Card {
        JOKER('J'),
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
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
