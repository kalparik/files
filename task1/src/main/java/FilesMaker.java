import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;

public class FilesMaker {

    /**
     * Логгер
     */
    Logger logger;

    /**
     * Путь к папке с игрой
     */
    private String gamesDirectory;

    private FilesMaker(Logger logger) {
        this.logger = logger;
    }

    /**
     * Создать структуру файлов игры
     *
     * @param logger логгер
     * @param block  функция создания структуры папок
     */
    public static void make(Logger logger, Consumer<FilesMaker> block) {
        FilesMaker maker = new FilesMaker(logger);

        try {
            block.accept(maker);
        } finally {
            System.out.println(logger.getLog());
        }
    }

    /**
     * Указать папку к директории с игрой
     *
     * @param path путь к папке
     * @return
     */
    public FilesMaker setGamesDirectory(String path) {
        this.logger.info(String.format("Set games directory to %s", path));
        this.gamesDirectory = new File(path).getAbsolutePath();
        return this;
    }

    /**
     * Создать папку в папке с игрой
     *
     * @param path Путь к папке (внутри папки игры)
     * @return
     */
    public FilesMaker makeDirectoryToGames(String path) {
        String fullPath = gamesDirectory + path;

        this.logger.info(String.format("Make directory: %s", fullPath));
        File dir = new File(fullPath);

        if (dir.exists()) {
            this.logger.info("Directory already exists");
            return this;
        }

        boolean result = dir.mkdir();
        if (!result) {
            this.logger.info("Directory wasn't made");
            return this;
        }

        this.logger.info("Directory was made");

        return this;
    }

    /**
     * Создать файл в папке с игрой
     *
     * @param path Путь к файлу (внутри папки игры)
     * @return
     */
    public FilesMaker createFileToGames(String path) {
        String fullPath = gamesDirectory + path;

        this.logger.info(String.format("Create file: %s", fullPath));
        File file = new File(fullPath);

        if (file.exists()) {
            this.logger.info("File already exists");
            return this;
        }

        boolean result = false;
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            this.logger.info(String.format("Exception on file creating: %s", e.getMessage()));
        } finally {
            if (!result) {
                this.logger.info("File wasn't created");
                return this;
            }
        }

        this.logger.info("File was created");

        return this;
    }

    /**
     * Сохранить логи в файл
     *
     * @param path Путь к файлу (внутри папки игры)
     * @return
     */
    public FilesMaker saveLogToFile(String path) {
        String fullPath = gamesDirectory + path;

        this.logger.info(String.format("Save log to: %s", fullPath));
        File file = new File(fullPath);

        if (!file.exists()) {
            this.logger.info("File is not exists");
            return this;
        }

        if (!file.canWrite()) {
            this.logger.info("File is not available to write log");
            return this;
        }

        boolean result = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(logger.getLog());
        } catch (IOException e) {
            this.logger.info(String.format("Exception on saving log: %s", e.getMessage()));
            return this;
        }

        this.logger.info("Log was saved");

        return this;
    }
}
