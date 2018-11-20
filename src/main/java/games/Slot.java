package games;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.random;
import static java.lang.Math.round;

public class Slot {

    public static void main(String... __) {

        // ставка
        int bet = 10;
        int balance = 100;
        int prize = 1_000;
        int countDrums = 3;

        // максимальное число на барабане
        int size = 6;

        // барабаны
        int[] counters = new int[countDrums];
        
        // чтобы была возможность вычесть ставку
        while ((balance - bet) >= 0) {

            System.out.println("У Вас " + balance + "$, ставка - " + bet + "$");
            System.out.println("Крутим барабаны! Розыгрыш принёс следующие результаты:");

            Random random = new Random();

            for (int i = 0; i < countDrums; i++) {
                counters[i] = (counters[i] + (int) round(random() * 100)) % size;
            }

            System.out.println("первый барабан - " + counters[0] + ", второй - " + counters[1]
                    + ", третий - " + counters[2]);

            if ((counters[0] == counters[1]) && (counters[1] == counters[2])) {
                System.out.println("Поздравляю! Вы выиграли " + prize + "$!");
                balance += prize;

            } else {
                balance -= bet;
                System.out.println("Проигрыш  " + bet + "$, ваш капитал теперь составляет: " + balance + "$");
            }
        }


    }
}
