package games;

import org.slf4j.Logger;
import java.io.IOException;

public class Choice {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Choice.class);

    static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String[] args) throws IOException {
        log.info("Выберите игру: 1 - однорукий бандит, 2 - пьяница, 3 - очко");
        char codeGame = getCharacterFromUser();
        switch (codeGame) {
            case '1':
                Slot.main();
                break;
            case '2':
                Drunkard.main();
                break;
            case '3':
                BlackJack.main();
                break;
            default:
                log.info("Игры с таким номером нет!");
        }
    }

    static char getCharacterFromUser() throws IOException {
        byte[] input = new byte[1 + LINE_SEPARATOR.length()];
        if (System.in.read(input) != input.length) {
            log.error("Пользователь ввёл недостаточное кол-во символов");
            throw new RuntimeException("Пользователь ввёл недостаточное кол-во символов");
        }
        return (char) input[0];
    }

    static boolean confirm(String message) throws IOException {
        log.info("{} да - 'Y', (любой другой символ) - нет (Что бы выйти из игры, нажмите Ctrl + C)", message);

        switch (getCharacterFromUser()) {
            case 'Y':
            case 'y':
                return true;
            default:
                return false;
        }
    }
}
