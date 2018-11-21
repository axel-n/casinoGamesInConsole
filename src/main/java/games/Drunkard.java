package games;

import java.util.Arrays;

public class Drunkard {
    private static final int PARS_TOTAL_COUNT = Par.values().length; // 9
    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length; // 36

    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = new int[2];

    private static int[] deck1 = new int[CARDS_TOTAL_COUNT / 2];
    private static int[] deck2 = new int[CARDS_TOTAL_COUNT / 2];

    public static void main(String... __) {
        int counter = 1;

        /////////////////////
        // for debug
        // fast winner
//        int[][] playersCards2 = {
//                {0, 1, 2, 3, 4, 5, 6, 7, 0, 9, 10, 11, 12, 13, 14, 15, 16, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
//                {19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
//        };
//
//        System.arraycopy(playersCards2[0], 0, playersCards[0], 0, 18);
//        System.arraycopy(playersCards2[1], 0, playersCards[1], 0, 18);
//
//
//        deck1 = playersCards[0];
//        deck2 = playersCards[1];

        /////////////////////////


        // раздаем карты
        distributeCards();

        int firstPlayerPointer = 0;
        int secondPlayerPointer = 0;


        while (!isEnd()) {

            System.out.println(Arrays.toString(deck1));
            System.out.println(Arrays.toString(deck2));

            System.out.println("-------------");

            System.out.printf("Итерация №%d ", counter);


            System.out.printf("игрок №1 карта: %s, игрок №2 карта: %s%n", toString(deck1[0]), toString(deck2[0]));

            int card1 = getIndexSuite(deck1[0]);
            int card2 = getIndexSuite(deck2[0]);

            if (card1 > card2) {

                System.out.printf("Выиграл игрок №1!%n");

                deck1[playersCardTails[0]] = deck1[0];
                deck1[0] = -1;

                deck1[playersCardTails[0] + 1] = deck2[0];
                deck2[0] = -1;

                playersCardTails[0]++;
                playersCardTails[1]--;

                System.arraycopy(deck1, 1, deck1, 0, playersCardTails[0]);
                System.arraycopy(deck2, 1, deck2, 0, playersCardTails[1]);

                deck1[playersCardTails[0]] = -1;
                deck2[playersCardTails[1]] = -1;

                System.out.printf("У игрока №1 %d карт, у игрока №2 %d карт%n", playersCardTails[0], playersCardTails[1]);

            } else if (card1 == card2) {
                System.out.printf("Спор - каждый остаётся при своих!%n");

                deck1[playersCardTails[0]] = deck1[0];
                deck2[playersCardTails[1]] = deck2[0];

                deck1[0] = -1;
                deck2[0] = -1;

                System.arraycopy(deck1, 1, deck1, 0, playersCardTails[0]);
                System.arraycopy(deck2, 1, deck2, 0, playersCardTails[1]);

                deck1[playersCardTails[0]] = -1;
                deck2[playersCardTails[1]] = -1;

                System.out.printf("У игрока №1 %d карт, у игрока №2 %d карт%n", playersCardTails[0], playersCardTails[1]);


            } else {
                System.out.printf("Выиграл игрок №2!%n");

                deck2[playersCardTails[1]] = deck2[0];
                deck2[0] = -1;

                deck2[playersCardTails[1] + 1] = deck1[0];
                deck1[0] = -1;

                playersCardTails[0]--;
                playersCardTails[1]++;

                System.arraycopy(deck1, 1, deck1, 0, playersCardTails[0]);
                System.arraycopy(deck2, 1, deck2, 0, playersCardTails[1]);

                deck1[playersCardTails[0]] = -1;
                deck2[playersCardTails[1]] = -1;

                System.out.printf("У игрока №1 %d карт, у игрока №2 %d карт%n", playersCardTails[0], playersCardTails[1]);

            }

            counter++;

        }

        System.out.printf("Выиграл %s игрок. Количество произведённых итераций: %d.", getWinner(), counter);


    }

    enum Suit {
        SPADES, // пики
        HEARTS, // червы
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK, // Валет
        QUEEN, // Дама
        KING, // Король
        ACE // Туз
    }

    private static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static Suit getSuite(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    private static int getIndexSuite(int cardNumber) {
        return cardNumber % PARS_TOTAL_COUNT;

        // return Suit.values()[cardNumber % PARS_TOTAL_COUNT];
    }


    private static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuite(cardNumber);
    }

    private static void distributeCards() {

        int[] deck = new int[36];

        // "создаем" колоду
        for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
            deck[i] = i;
        }

        // перемешиваем колоду
        //MathArrays.shuffle(deck);

        // отмечаем пустые ячейки спец значением
        Arrays.fill(playersCards[0], -1);
        Arrays.fill(playersCards[1], -1);

        System.arraycopy(deck, 0, playersCards[0], 0, CARDS_TOTAL_COUNT / 2);
        System.arraycopy(deck, CARDS_TOTAL_COUNT / 2, playersCards[1], 0, CARDS_TOTAL_COUNT / 2);

        deck1 = playersCards[0];
        deck2 = playersCards[1];

        playersCardTails[0] = CARDS_TOTAL_COUNT / 2;
        playersCardTails[1] = CARDS_TOTAL_COUNT / 2;
    }


    private static boolean isEnd() {

        if (playersCardTails[0] > 0 || playersCardTails[1] > 0) {
            if (playersCardTails[0] == CARDS_TOTAL_COUNT || playersCardTails[1] == CARDS_TOTAL_COUNT) {
                return true;
            }
        }
        return false;
    }

    private static String getWinner() {

        return playersCardTails[0] == CARDS_TOTAL_COUNT ? "первый" : "второй";

    }
}