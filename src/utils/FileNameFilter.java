package utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameFilter {
    // метод поиска
    public static File[] findFiles(String dir, String ext) {
        File file = new File(dir);
        if (!file.exists()) {
            System.out.println(dir + " папка не существует");
            return (null);
        }
        File[] listFiles = file.listFiles(new MyFileNameFilter(ext));
        if (listFiles.length == 0) {
            System.out.println(dir + " не содержит файлов с расширением " + ext);
        }
        return (listFiles);
    }

    // Реализация интерфейса FileNameFilter
    public static class MyFileNameFilter implements FilenameFilter {

        private String ext;

        public MyFileNameFilter(String ext) {
            this.ext = ext.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }
}