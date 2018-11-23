package games;

import org.slf4j.Logger;
import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);
    
    private static final int BET = 10;
    private static final int PRIZE = 1_000;
    private static final int COUNT_DRUMS = 3;
    
    private static final int MAX_NUMBER_ON_DRUM = 6;

    private static int[] drums = new int[COUNT_DRUMS];
    private static int balance = 100;

    public static void main(String... __) {

        // чтобы была возможность вычесть ставку
        while ((balance - BET) >= 0) {

            log.info("У Вас {}$, ставка - {}$", balance, BET);
            log.info("Крутим барабаны! Розыгрыш принёс следующие результаты:");

            for (int i = 0; i < COUNT_DRUMS; i++) {
                drums[i] = (drums[i] + (int) round(random() * 100)) % MAX_NUMBER_ON_DRUM;
            }

            log.info("первый барабан - {}, второй - {}, третий - {}", drums[0], drums[1], drums[2]);

            if ((drums[0] == drums[1]) && (drums[1] == drums[2])) {
                log.info("Поздравляю! Вы выиграли {}$!", PRIZE);
                balance += PRIZE;

            } else {
                balance -= BET;
                log.info("Проигрыш {}$, ваш капитал теперь составляет: {}$", BET, balance);
            }
        }
    }
}
