# Current Classes
## SeaFight.java
```java

public class SeaFight {

	public static void main(String[] argv) {
		new Window();
	}
	
}
```
## Window.java
```java
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
	
	public Window() 
	{
		GameField game = new GameField();
		
		Container cont = this.getContentPane();
		cont.add(game);
		
		this.setTitle("Игра: \"Морской бой\"");
		this.setBounds(0,0,900,600);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
```
## GameField.java
```java
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
```
## GameLogic.java
```java
public class GameLogic {
	public int[][] player_array;
	public int[][] computer_array;
	public boolean computer_turn = false;
	public int end_game;

	public GameLogic()
	{
		player_array = new int[10][10];
		computer_array = new int[10][10];
	}
	
	public void start()
	{
		// очищаем игровое поле игрока
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				player_array[i][j] = 0;
				computer_array[i][j] = 0;
			}
		}
		end_game = 0;
		computer_turn = false;
		
		placeShips(player_array);
		placeShips(computer_array);
		
	}
	
	private void placeShips(int[][] arr) 
	{
		createShip(arr, 4);
		
		for (int i = 1; i <=2; i++)
		{
			createShip(arr, 3);
		}
		for (int i = 1; i <= 3; i++)
		{
			createShip(arr, 2);
		}
		
		for (int i = 1; i <= 4; i++)
		{
			createShip(arr, 1);
		}
	}
	
	private boolean testArrayPos(int i, int j)
	{
		if ( ( (i >= 0) && (i <= 9) ) && ( (j >= 0) && (j <= 9) ) )
		{
			return true;
		}
		return false;
	}
	
	private void setArrayPos(int[][] arr, int i, int j, int val)
	{
		// Если не происходит выход за границы массива
		if (testArrayPos(i, j))
		{
			arr[i][j] = val;
		}
	}
	
	private void setSpace(int[][] arr, int i, int j, int val)
	{
		if (testArrayPos(i, j))
		{
			setArrayPos(arr, i, j, val);
		}
		
	}
	
	private void spaceBegin(int[][] arr, int i, int j, int val)
	{
		setSpace(arr, i-1, j-1, val);
		setSpace(arr, i-1, j, val);
		setSpace(arr, i-1, j+1, val);
		setSpace(arr, i, j+1, val);
		setSpace(arr, i+1, j+1, val);
		setSpace(arr, i+1, j, val);
		setSpace(arr, i+1, j-1, val);
		setSpace(arr, i, j-1, val);
	}
	
	private void spaceEnd(int[][] arr) 
	{
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (arr[i][j] == -2)
				{
					arr[i][j] = -1;
				}
			}
		}
	}
	
	private boolean testNewPaluba(int[][] arr, int i, int j) 
	{
		if (testArrayPos(i, j) == false) 
		{
			return false;
		}
		
		if ( (arr[i][j] == 0) || (arr[i][j] == -2) )
		{
			return true;
		}
		return false;
	}
	
	private void createShip(int[][] arr, int size)
	{
	    while (true)
	    {
	        int i = (int)(Math.random() * 10);
	        int j = (int)(Math.random() * 10);
	        int direction = (int)(Math.random() * 2); // 0 = горизонталь, 1 = вертикаль

	        boolean canPlace = true;

	        for (int k = 0; k < size; k++)
	        {
	            int x = i;
	            int y = j;

	            if (direction == 0)
	                y += k;
	            else
	                x += k;

	            if (!testArrayPos(x, y) || arr[x][y] != 0)
	            {
	                canPlace = false;
	                break;
	            }

	            // проверяем окружение
	            for (int dx = -1; dx <= 1; dx++)
	            {
	                for (int dy = -1; dy <= 1; dy++)
	                {
	                    int nx = x + dx;
	                    int ny = y + dy;

	                    if (testArrayPos(nx, ny) && arr[nx][ny] > 0)
	                    {
	                        canPlace = false;
	                        break;
	                    }
	                }
	            }

	            if (!canPlace)
	                break;
	        }

	        if (!canPlace)
	            continue;

	        // размещаем корабль
	        for (int k = 0; k < size; k++)
	        {
	            int x = i;
	            int y = j;

	            if (direction == 0)
	                y += k;
	            else
	                x += k;

	            arr[x][y] = size;
	        }

	        break;
	    }
	}
	
	public void playerShot(int i, int j)
	{
		
	}
	
}
```

