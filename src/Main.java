import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Николаев Александр Сергеевич");
        System.out.println("РИБО-01-21");

        String path;
        File file;
        while (true) {
            System.out.println();
            System.out.println("Введите путь к файлу:");
            path = scan();
            if (path.isEmpty() || path.isBlank()) {
                System.out.println("Путь необходимо заполнить!");
                continue;
            }
            file = new File(path);
            if (!file.exists()) {
                System.out.println("Файл не найден! Попробуйте снова.");
                continue;
            }
            if (file.isDirectory()) {
                System.out.println("Ошибка! Необходимо указать файл, а не директорию.");
                continue;
            }
            break;
        }

        String gamma;
        while (true) {
            System.out.println();
            System.out.println("Введите байты гаммы:");
            gamma = scan();
            if (gamma.isEmpty() || gamma.isBlank()) {
                System.out.println("Поле \"Гамма\" должно быть заполнено");
                continue;
            }
            break;
        }

        byte[] content;
        try {
            FileInputStream fis = new FileInputStream(file);
            content = new byte[fis.available()];
            fis.read(content);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] xorContent = xorOperation(content, hexStringToByteArray(gamma));

        File newFile = new File(file.getParent() + "\\result.txt");
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(xorContent);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Готово! Итоговый файл можно найти по пути:");
        System.out.println(newFile.getAbsolutePath());
    }

    public static byte[] xorOperation(byte[] content, byte[] gamma) {

        byte[] xorContent = new byte[content.length];

        for (int i=0; i<xorContent.length; i++) {
            for (byte g : gamma) {
                xorContent[i] = (byte) (content[i] ^ g);
            }
        }
        return xorContent;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Error hexstring");
        }
        byte[] data = new byte[len/2];
        for (int i=0; i<len; i+=2) {
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
            +Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String scan() {
        return new Scanner(System.in).nextLine();
    }
}