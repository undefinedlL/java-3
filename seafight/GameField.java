import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class GameField extends JPanel {
	private Timer timerDraw;
	private Image bg, paluba, killed, hurt, computer_winner, player_winner, bomb;
	private JButton begin_btn, quit_btn;
	private GameLogic game_logic;
	
	public GameField()
	{
		// game logic
		game_logic = new GameLogic();
		game_logic.start();
		
		// загрузка изображений
		try {
			bg = ImageIO.read(new File("D:\\files\\bg.jpg"));
			paluba = ImageIO.read(new File("D:\\files\\paluba.png"));
			killed = ImageIO.read(new File("D:\\files\\ubit.png"));
			hurt = ImageIO.read(new File("D:\\files\\ranen.png"));
			computer_winner = ImageIO.read(new File("D:\\files\\end2.png"));
			player_winner = ImageIO.read(new File("D:\\files\\end1.png"));
			bomb = ImageIO.read(new File("D:\\files\\bomba.png"));

		} catch(Exception ex) {
			System.out.println("Какое-то изображение отсутствует.");
		}
		// запуск таймера
		timerDraw = new Timer(50, (e) -> {
			repaint();
		});
		timerDraw.start();
		
		this.setLayout(null);
		
		// кнопки
		// Кнопка "Новая игра"
		begin_btn = new JButton();
		begin_btn.setText("Новая игра");
		begin_btn.setForeground(Color.green);
		begin_btn.setFont(new Font("serif", 0, 30));
		begin_btn.setBounds(130, 450, 200, 80);
		begin_btn.addActionListener(e -> {
	
			game_logic.start();
	
		});
		this.add(begin_btn);
		
		// Кнопка "Выход"
		quit_btn = new JButton();
		quit_btn.setText("Выход");
		quit_btn.setForeground(Color.red);
		quit_btn.setFont(new Font("serif", 0, 30));
		quit_btn.setBackground(Color.white); // new Color(0,0,0)  [ 0-255 ]
		quit_btn.setBounds(530, 450, 200, 80);
		quit_btn.addActionListener(e -> {
			System.exit(0);
		});
		this.add(quit_btn);
	}
	
	public void paintComponent(Graphics gr) 
	{
		super.paintComponent(gr);
	// labels
		gr.drawImage(bg, 0, 0, 900, 600, null);
		gr.setFont(new Font("serif", Font.BOLD, 40));
		gr.setColor(Color.black);
		gr.drawString("Компьютер", 150, 50);
		gr.drawString("Игрок", 590, 50);
	
		// сетка
		gr.setColor(Color.black);
		for (int i = 0; i <= 10; i++) 
		{
			// сетка компьютера
			gr.drawLine(100+i*30, 100, 100+i*30, 400);
			gr.drawLine(100, 100+i*30, 400, 100+i*30);
			// сетка игрока
			gr.drawLine(500+i*30, 100, 500+i*30, 400);
			gr.drawLine(500, 100+i*30, 800, 100+i*30);
		}
		
		gr.setColor(Color.black);
		gr.setFont(new Font("serif", Font.PLAIN, 25));
		for (int i = 1; i <= 10; i++) 
		{
			// цифры
			gr.drawString(""+i, 73, i*30+93);
			gr.drawString(""+i, 478, i*30+93);
			
			// буквы
			gr.drawString(""+(char)('A'+i-1), 78+i*30, 93);
			gr.drawString(""+(char)('A'+i-1), 478+i*30, 93);
		}
		
		
		// player
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				// Если это палуба корабля
				if ( (game_logic.player_array[i][j] >= 1) && (game_logic.player_array[i][j] <= 4))
				{
					gr.drawImage(paluba, 500+j*30, 100 + i * 30, 30, 30, null);
				}
			}
		}
	}
	
}









