import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Picture {
    public static void main(String[] args) {
        // Если путь не был передан
        if (args.length != 1) {
            System.err.println("Ошибка! Отсутствует путь!");
            System.exit(1); // аварийный выход
        }

        String path = args[0]; // путь - первый элемент массива аргументов
        File image = new File(path); // экземпляр класса file для получения файла из переданного пути

        // Если файл не найден или не является им
        if (!image.exists() || !image.isFile()) {
            System.err.println("Ошибка! Файл не найден или не является им!");
            System.exit(1); // аварийный выход
        }

        /*  Создаем фрейм окна с помощью конструктора.
        Конструктор берет параметр – название окна */
        JFrame frame = new JFrame("Отображение изображения");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // устанавливаем реакцию окна на закрытие по умолчанию

        JLabel imageLabel = new JLabel();
        // Создаём экземпляр класса для отображения изображения
        ImageIcon imageIcon = new ImageIcon(path);
        // Добавляем и отображаем во фрейме полученное изображение
        imageLabel.setIcon(imageIcon);
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);

        frame.pack(); // упаковываем фрейм
        frame.setVisible(true); // отображаем фрейм
    }
}

