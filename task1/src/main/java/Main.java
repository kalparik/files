public class Main {

    /**
     * Путь к вручную созданной папке Games
     */
    public static final String GAMES_PATH = "Games";

    public static void main(String[] args) {
        Logger logger = new Logger();
        FilesMaker.make(logger, maker -> maker
            .setGamesDirectory(GAMES_PATH)
            .makeDirectoryToGames("/src")
            .makeDirectoryToGames("/src/main")
            .createFileToGames("/src/main/Main.java")
            .createFileToGames("/src/main/Utils.java")
            .makeDirectoryToGames("/src/test")
            .makeDirectoryToGames("/res")
            .makeDirectoryToGames("/res/drawables")
            .makeDirectoryToGames("/res/vectors")
            .makeDirectoryToGames("/res/icons")
            .makeDirectoryToGames("/savegames")
            .makeDirectoryToGames("/temp")
            .createFileToGames("/temp/temp.txt")
            .saveLogToFile("/temp/temp.txt")
        );
    }
}
