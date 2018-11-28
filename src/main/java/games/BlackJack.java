package games;

import org.slf4j.Logger;

import java.io.IOException;

class BlackJack {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

    private static int[] commonDeck;
    private static int commonDeckCursor;

    private static int[][] playersCards;
    private static int[] playersCursors;

    private static int[] playersMoney = {100, 100};

    private static final int MAX_VALUE = 21;
    private static final int MAX_VALUE_FOR_HUMAN = 20;
    private static final int MIN_VALUE_FOR_HUMAN = 11;
    private static final int MAX_VALUE_FOR_AI = 16;
    private static final int MAX_CARDS_COUNT = 8;

    private static final int ID_USER = 0;
    private static final int ID_AI = 1;

    private static final int BET = 10;

    private static int value(int card) {
        switch (CardUtils.getPar(card)) {
            case JACK:
                return 2;
            case QUEEN:
                return 3;
            case KING:
                return 4;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case ACE:
            default:
                return 11;
        }
    }

    private static void initRound() {
        log.info("У Вас {}$, у компьютера {}$. Начинаем новый раунд!", playersMoney[ID_USER], playersMoney[ID_AI]);
        commonDeck = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        commonDeckCursor = 0;
    }

    private static int addCard2Player(int player) {

        int card = commonDeck[commonDeckCursor];

        playersCards[player][playersCursors[player]] = card;
        playersCursors[player]++;
        commonDeckCursor++;

        String text;
        if (player == ID_USER)
            text = "Вам выпала карта";
        else
            text = "Компьютер решил взять и ему выпала карта";

        log.info("{} {}", text, CardUtils.toString(card));

        return getFinalSum(player);
    }

    private static int sumPointsPlayer(int player) {

        int sum = 0;

        for (int i = 0; i < playersCursors[player]; i++) {
            sum += value(playersCards[player][i]);
        }

        return sum;
    }

    // получение суммы игрока, если она валидная
    // 0, если превысили порог MAX_VALUE
    private static int getFinalSum(int player) {
        int sum = sumPointsPlayer(player);

        return sum <= MAX_VALUE ? sum : 0;
    }

    private static boolean existWinner() {
        return ((playersMoney[ID_AI] - BET) < 0) || ((playersMoney[ID_USER] - BET) < 0);
    }

    static void main() throws IOException {

        while (!existWinner()) {

            initRound();

            int sum;

            do {
                sum = addCard2Player(ID_USER);

                System.out.println(sum);
            }
            while (sum != 0
                    && (sum < MIN_VALUE_FOR_HUMAN
                    || (sum < MAX_VALUE_FOR_HUMAN
                    && Choice.confirm("Берёте ещё?"))));

            do
                sum = addCard2Player(ID_AI);
            while (sum != 0 && sum < MAX_VALUE_FOR_AI);

            result();
        }

        String final_text = playersMoney[ID_USER] > 0 ?
                "Вы выиграли! Поздравляем!"
                : "Вы проиграли. Соболезнуем...";
        log.info(final_text);


    }

    private static void result() {

        int sum1 = getFinalSum(ID_USER);
        int sum2 = getFinalSum(ID_AI);

        log.info("Сумма ваших очков - {}, компьютера - {}", sum1, sum2);

        if (sum1 > sum2) {
            log.info("Вы выиграли раунд! Получаете {}$", BET);
            playersMoney[ID_USER] += BET;
            playersMoney[ID_AI] -= BET;
        } else if (sum1 == sum2) {
            log.info("Ничья! Все остаются при своих");
        } else {
            log.info("Вы проиграли раунд! Теряете {}$", BET);
            playersMoney[ID_USER] -= BET;
            playersMoney[ID_AI] += BET;
        }
    }
}