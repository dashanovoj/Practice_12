import javax.imageio.ImageIO; // импортируем для работы с изображениями
import javax.swing.*; // импортируем для работы с графическим интерфейсом
import java.awt.*; // импортируем для работы с графикой и событиями
import java.awt.event.ActionEvent; // импортируем для работы с событиями
import java.awt.event.ActionListener; // импортируем для работы с событиями
import java.awt.image.BufferedImage; // импортируем для работы с буферизованными изображениями
import java.io.File; // импортируем для работы с файлами
import java.io.IOException; // импортируем для обработки ошибок ввода-вывода


// Определяем класс Animation, который наследуется от JPanel и реализует интерфейс ActionListener
public class Animation extends JPanel implements ActionListener {

    // Поля класса
    private BufferedImage[] spriteSheets; // массив буферизованных изображений для анимации (для плавности её отображения)
    private int frameWidth, frameHeight; // ширина и высота кадра анимации
    private int currentFrame = 0; // текущий кадр анимации
    private int currentImage = 0; // текущее изображение анимации
    private int totalFrames; // общее количество кадров анимации
    private int rows, columns; // количество строк и столбцов в спрайте
    private Timer timer; // таймер для анимации

    // Конструктор класса Animation, который принимает массив путей к спрайтам, ширину и высоту кадра, количество строк и столбцов в спрайте
    public Animation(String[] spriteSheetsPaths, int frameWidth, int frameHeight, int rows, int columns) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.rows = rows;
        this.columns = columns;
        this.totalFrames = rows * columns; // расчёт общего количество кадров анимации

        // Создаём массив буферизованных изображений.
        spriteSheets = new BufferedImage[spriteSheetsPaths.length];
        for (int i = 0; i < spriteSheetsPaths.length; i++) { // загружаем спрайты
            try {
                spriteSheets[i] = ImageIO.read(new File(spriteSheetsPaths[i])); // с помощью метода read класса ImageIO загружаем из файла изображение
            } catch (IOException e) { // при возникновении ошибки загрузки
                System.out.println("Ошибка загрузки спрайта");
            }
        }

        /* Устанавливаем изображение на всё окно: для этого используем метод setPreferredSize -
        установка предпочтительного размера компонента, в который передаем объект класса Dimension
        с размерами, равными размерам экрана (размеры экрана получаем через метод Toolkit) */
        setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

        // Создаём таймер с интервалом 20 миллисекунд
        timer = new Timer(20, this);
        timer.start(); // запускаем таймер
    }


    // Переопределяем метод paintComponent для отрисовки анимации
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Очищаем область рисования и подготавливаем ее для отрисовки компонента

        // Рисуем текущее изображение анимации на всю область панели (рисуем с левого верхнего угла - начала координат)
        g.drawImage(spriteSheets[currentImage], 0, 0, getWidth(), getHeight(),
                0, 0, spriteSheets[currentImage].getWidth(), spriteSheets[currentImage].getHeight(), this);
    }

    // Переопределяем метод для обработки событий таймера
    @Override
    public void actionPerformed(ActionEvent e) {
        /* Увеличение текущего кадра анимации и переход к следующему кадру при достижении конца анимации
        (необходимо для циклической анимации, чтобы она не выходила за пределы общего количества кадров). */
        currentFrame = (currentFrame + 1) % totalFrames;
        /* Если текущий кадр = 1, увеличиваем текущее изображение на 1 и берем остаток от деления на общее количество изображений в массиве
        (необходимо для циклической анимации, чтобы она не выходила за пределы общего количества изображений).*/
        if (currentFrame == 0) {
            currentImage = (currentImage + 1) % spriteSheets.length;
        }
        repaint(); // обновляем отображение анимации на экране
    }

    public static void main(String[] args) {
        /*  Создаем фрейм окна с помощью конструктора.
        Конструктор берет параметр – название окна */
        JFrame frame = new JFrame("Анимация");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // устанавливаем реакцию окна на закрытие по умолчанию
        // Cоздаем массив строк, содержащий пути к файлам изображений для анимации
        String[] spriteSheetPaths = {"1.png", "2.png", "3.png", "4.png", "5.png", "6.png"};
        // Создаём экземпляр класса анимации, передает массив путей файлов, ширину и высоту окна, количество строк и столбцов в спрайт-листе
        Animation animation = new Animation(spriteSheetPaths, 500, 500, 2, 4);
        frame.add(animation); // добавляем к фрейму анимацию
        frame.pack(); // подгоняем размеры окна под содержимое
        frame.setVisible(true); // делаем окно видимым на экране
    }
}