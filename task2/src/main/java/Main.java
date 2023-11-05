import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static final String SAVE_PATH = "Games/savegames/";

    public static void main(String[] args) {
        // Создать три экземпляра класса GameProgress
        var gamedata = List.of(
            new GameProgress(100, 5, 1, 50.5),
            new GameProgress(80, 50, 2, 10.855),
            new GameProgress(1, 34, 3, 0.53)
        );

        // Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        List<String> filenames = new ArrayList<>();
        for (int i = 0; i < gamedata.size(); i++) {
            String name = SAVE_PATH + "game" + (i + 1) + ".dat";
            saveGame(name, gamedata.get(i));
            filenames.add(name);
        }

        // Созданные файлы сохранений из папки savegames запаковать в архив zip.
        zipFiles(SAVE_PATH + "save.zip", filenames);

        // Удалить файлы сохранений, лежащие вне архива.
        deleteSavedGames(filenames);
    }

    /**
     * Удалить файлы сохранений
     *
     * @param filenames
     */
    private static void deleteSavedGames(List<String> filenames) {
        for (int i = 0; i < filenames.size(); i++) {
            String name = filenames.get(i);
            File file = new File(name);
            if (file.delete()) {
                System.out.println("Файл удален");
            }
        }
    }

    /**
     * Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
     *
     * @param path Полный путь к файлу сохранения
     * @param game Сохраняемая игра
     */
    public static void saveGame(String path, GameProgress game) {
        try (FileOutputStream baos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(game);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Созданные файлы сохранений из папки savegames запаковать в архив zip
     *
     * @param path      путь к создаваемому архиву
     * @param filenames имена фуйлов для упаковки
     */
    public static void zipFiles(String path, List<String> filenames) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (int i = 0; i < filenames.size(); i++) {
                String name = filenames.get(i);
                addFileToZipStream(zout, name);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Добавить файл в zip-стрим
     *
     * @param zipOutputStream zip-стрим куда добавлять
     * @param path            путь к добавляемому файлу
     * @throws IOException
     */
    private static void addFileToZipStream(
        ZipOutputStream zipOutputStream, String path
    ) throws IOException {
        try (FileInputStream fis = new FileInputStream(path)) {
            File file = new File(path);
            ZipEntry entry = new ZipEntry(file.getName());
            zipOutputStream.putNextEntry(entry);
            
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // добавляем содержимое к архиву
            zipOutputStream.write(buffer);

            // закрываем текущую запись для новой записи
            zipOutputStream.closeEntry();
        }
    }
}
