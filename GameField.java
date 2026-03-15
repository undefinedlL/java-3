import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class GameField extends JPanel implements MouseListener, MouseMotionListener {
    private Timer timerDraw;
    private Image bg, paluba, killed, hurt, computer_winner, player_winner, bomb;
    private JButton begin_btn, quit_btn;
    private GameLogic game_logic;

    private int mX, mY;

    public GameField() {
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

        // Инициализация логики игры
        game_logic = new GameLogic();
        game_logic.start();

        // Загрузка изображений
        try {
            bg = ImageIO.read(new File("D:\\files\\bg.jpg"));
            paluba = ImageIO.read(new File("D:\\files\\paluba.png"));
            killed = ImageIO.read(new File("D:\\files\\ubit.png"));
            hurt = ImageIO.read(new File("D:\\files\\ranen.png"));
            computer_winner = ImageIO.read(new File("D:\\files\\end2.png"));
            player_winner = ImageIO.read(new File("D:\\files\\end1.png"));
            bomb = ImageIO.read(new File("D:\\files\\bomba.png"));
        } catch (Exception ex) {
            System.out.println("Какое-то изображение отсутствует.");
        }

        // Таймер перерисовки и для хода компьютера
        timerDraw = new Timer(100, e -> {
            if (game_logic.computer_turn && game_logic.end_game == 0) {
                game_logic.computer_turn = !game_logic.computerShot();
            }
            repaint();
        });
        timerDraw.start();

        this.setLayout(null);

        // Кнопка "Новая игра"
        begin_btn = new JButton("Новая игра");
        begin_btn.setForeground(Color.green);
        begin_btn.setFont(new Font("serif", Font.PLAIN, 30));
        begin_btn.setBounds(130, 450, 200, 80);
        begin_btn.addActionListener(e -> game_logic.start());
        this.add(begin_btn);

        // Кнопка "Выход"
        quit_btn = new JButton("Выход");
        quit_btn.setForeground(Color.red);
        quit_btn.setFont(new Font("serif", Font.PLAIN, 30));
        quit_btn.setBounds(530, 450, 200, 80);
        quit_btn.addActionListener(e -> System.exit(0));
        this.add(quit_btn);
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        // Фон
        gr.drawImage(bg, 0, 0, 900, 600, null);

        // Сетка и подписи
        drawGridAndLabels(gr);

        // Отрисовка клеток компьютера
        drawField(gr, game_logic.computer_array, 100, 100, false);

        // Отрисовка клеток игрока
        drawField(gr, game_logic.player_array, 500, 100, true);

        // Подсветка клетки мыши (для поля компьютера)
        if ((mX >= 100) && (mY >= 100) && (mX <= 400) && (mY <= 400)) {
            if ((game_logic.end_game == 0) && (!game_logic.computer_turn)) {
                int i = (mY - 100) / 30;
                int j = (mX - 100) / 30;
                if (game_logic.computer_array[i][j] <= 4) {
                    gr.setColor(new Color(255, 0, 0, 100));
                    gr.fillRect(100 + j * 30, 100 + i * 30, 30, 30);
                }
            }
        }

        // Сообщение об окончании игры
        if (game_logic.end_game == 1) {
            gr.drawImage(player_winner, 300, 200, 300, 100, null);
        } else if (game_logic.end_game == 2) {
            gr.drawImage(computer_winner, 300, 200, 300, 100, null);
        }
    }

    private void drawGridAndLabels(Graphics gr) {
        gr.setColor(Color.black);
        gr.setFont(new Font("serif", Font.BOLD, 40));
        gr.drawString("Компьютер", 150, 50);
        gr.drawString("Игрок", 590, 50);

        // сетка
        for (int i = 0; i <= 10; i++) {
            gr.drawLine(100 + i * 30, 100, 100 + i * 30, 400);
            gr.drawLine(100, 100 + i * 30, 400, 100 + i * 30);
            gr.drawLine(500 + i * 30, 100, 500 + i * 30, 400);
            gr.drawLine(500, 100 + i * 30, 800, 100 + i * 30);
        }

        // цифры и буквы
        gr.setFont(new Font("serif", Font.PLAIN, 25));
        for (int i = 1; i <= 10; i++) {
            gr.drawString("" + i, 73, i * 30 + 93);
            gr.drawString("" + i, 478, i * 30 + 93);
            gr.drawString("" + (char) ('A' + i - 1), 78 + i * 30, 93);
            gr.drawString("" + (char) ('A' + i - 1), 478 + i * 30, 93);
        }
    }

    private void drawField(Graphics gr, int[][] arr, int startX, int startY, boolean showShips) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                // бомба
                if (arr[i][j] >= 5) {
                    gr.drawImage(bomb, startX + j * 30, startY + i * 30, 30, 30, null);
                }

                // раненая палуба
                if (arr[i][j] >= 8 && arr[i][j] <= 11) {
                    gr.drawImage(hurt, startX + j * 30, startY + i * 30, 30, 30, null);
                }

                // убитая палуба
                if (arr[i][j] >= 15) {
                    gr.drawImage(killed, startX + j * 30, startY + i * 30, 30, 30, null);
                }

                // палубы корабля (если игрок или показываем поле игрока)
                if (showShips && arr[i][j] >= 1 && arr[i][j] <= 4) {
                    gr.drawImage(paluba, startX + j * 30, startY + i * 30, 30, 30, null);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getButton() == 1) && (e.getClickCount() == 1)) {
            mX = e.getX();
            mY = e.getY();

            if ((mX > 100) && (mY > 100) && (mX < 400) && (mY < 400)) {
                if ((game_logic.end_game == 0) && (!game_logic.computer_turn)) {
                    int i = (mY - 100) / 30;
                    int j = (mX - 100) / 30;
                    if (game_logic.computer_array[i][j] <= 4) {
                        game_logic.playerShot(i, j);
                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();

        if ((mX >= 100) && (mY >= 100) && (mX <= 400) && (mY <= 400)) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseDragged(MouseEvent e) {}
}