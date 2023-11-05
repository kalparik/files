import java.util.Date;

/**
 * Логгер накапливающий логи
 */
public class Logger {
    private final StringBuilder log = new StringBuilder();

    /**
     * Записать в лог информационное сообщение
     *
     * @param message сообщение
     */
    public void info(String message) {
        log.append(String.format("INFO [%s] %s\n", new Date(), message));
    }

    /**
     * Получить накопленные логи
     *
     * @return накопленные логи
     */
    public String getLog() {
        return log.toString();
    }
}
