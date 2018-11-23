package games;

import java.util.Arrays;

import static games.CardUtils.CARDS_TOTAL_COUNT;
import static games.CardUtils.PARS_TOTAL_COUNT;


public class Drunkard {

    // чтобы можно было скопировать карту свою и игрока2 в конец перед победой
    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT + 1];
    private static int[] playersCardTails = new int[2];

    private static final int ID_USER1 = 0;
    private static final int ID_USER2 = 1;

    public static void main(String... __) {
        int counter = 1;

        distributeCards();

        playersCardTails[ID_USER1] = CARDS_TOTAL_COUNT / 2;
        playersCardTails[ID_USER2] = CARDS_TOTAL_COUNT / 2;

        while (!existWinner()) {

            System.out.printf("Итерация №%d ", counter);
            System.out.printf("игрок №1 карта: %s, игрок №2 карта: %s%n", CardUtils.toString(playersCards[ID_USER1][0]), CardUtils.toString(playersCards[ID_USER2][0]));

            int card1 = getIndexSuite(playersCards[ID_USER1][0]);
            int card2 = getIndexSuite(playersCards[ID_USER2][0]);

            int winner = checkCards(card1, card2);
            processingDecks(winner);

            switch (winner) {
                case 0: {
                    System.out.printf("Выиграл игрок №1!%n");
                    break;
                }
                case 1: {
                    System.out.printf("Выиграл игрок №2!%n");
                    break;
                }
                case 2: {
                    System.out.printf("Спор - каждый остаётся при своих!%n");
                    break;
                }            }

            System.out.printf("У игрока №1 %d карт, у игрока №2 %d карт%n", playersCardTails[ID_USER1], playersCardTails[ID_USER2]);

            counter++;
        }

        System.out.printf("Выиграл %s игрок. Количество произведённых итераций: %d.", getWinner(), counter);

    }

    private static void processingDecks(int winner) {

        switch (winner) {
            case 0: {
                playersCards[ID_USER1][playersCardTails[ID_USER1]] = playersCards[ID_USER1][0];
                playersCards[ID_USER1][playersCardTails[ID_USER1] + 1] = playersCards[ID_USER2][0];

                playersCardTails[ID_USER1]++;
                playersCardTails[ID_USER2]--;

                moveCards1left();
                break;
            }

            case 1: {
                playersCards[ID_USER2][playersCardTails[ID_USER2]] = playersCards[ID_USER2][0];
                playersCards[ID_USER2][playersCardTails[ID_USER2] + 1] = playersCards[ID_USER1][0];

                playersCardTails[ID_USER1]--;
                playersCardTails[ID_USER2]++;

                moveCards1left();
                break;
            }

            case 2: {
                // спор
                playersCards[ID_USER1][playersCardTails[ID_USER1]] = playersCards[ID_USER1][0];
                playersCards[ID_USER2][playersCardTails[ID_USER2]] = playersCards[ID_USER2][0];

                moveCards1left();
                break;
            }
        }
    }

    private static void markFieldsSpecialValue() {
        Arrays.fill(playersCards[ID_USER1], -1);
        Arrays.fill(playersCards[ID_USER2], -1);
    }

    private static void distributeCards() {

        int[] deck = CardUtils.getShaffledCards();

        markFieldsSpecialValue();

        System.arraycopy(deck, 0, playersCards[ID_USER1], 0, CARDS_TOTAL_COUNT / 2);
        System.arraycopy(deck, CARDS_TOTAL_COUNT / 2, playersCards[ID_USER2], 0, CARDS_TOTAL_COUNT / 2);
    }

    private static int getIndexSuite(int cardNumber) {
        return cardNumber % PARS_TOTAL_COUNT;
    }

    public static void moveCards1left() {

        System.arraycopy(playersCards[ID_USER1], 1, playersCards[ID_USER1], 0, playersCardTails[ID_USER1]);
        System.arraycopy(playersCards[ID_USER2], 1, playersCards[ID_USER2], 0, playersCardTails[ID_USER2]);

        playersCards[ID_USER1][playersCardTails[ID_USER1]] = -1;
        playersCards[ID_USER2][playersCardTails[ID_USER2]] = -1;
    }

    private static boolean existWinner() {

        return playersCardTails[ID_USER1] <= 0 || playersCardTails[ID_USER2] <= 0;
    }

    private static String getWinner() {

        return playersCardTails[ID_USER1] == CARDS_TOTAL_COUNT ? "первый" : "второй";
    }

    // узнаем чья карта старше без учета масти
    // возращем код победетеля (или 2, если спор)
    private static int checkCards(int card1, int card2) {

        // проверяем случай, чтобы "шестерка" была больше "туза"
        if ((card1 == 0) && (card2 == 8)) {
            return 0;
        }

        if ((card2 == 0) && (card1 == 8)) {
            return 1;
        }

        if (card1 > card2) {
            return 0;

        } else if (card1 == card2) {
            // спор
            return 2;
        } else return 1;

    }
}