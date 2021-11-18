package ru.hzerr.dev;

import ru.hzerr.HElias;
import ru.hzerr.file.BaseDirectory;

import java.io.IOException;

public class ClearLogTask {

    public static void main(String[] args) throws IOException {
        BaseDirectory logDir = HElias.getProperties().getLogDir();
        if (logDir.exists()) {
            try {
                logDir.clean();
                System.out.println("Лог-файлы успешно очищены!");
            } catch (IOException io) {
                System.err.println("Ошибка! Лог-файлы не очищены!");
                io.printStackTrace();
            }
        } else
            System.err.println("Директория логирования не найдена!");
    }
}
