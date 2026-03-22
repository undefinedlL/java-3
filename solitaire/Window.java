import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
	
	public Window() 
	{
		Field game = new Field();
		
		Container cont = this.getContentPane();
		cont.add(game);
		
		this.setTitle("Игра: \"Пасьянс-Косынка\"");
		this.setBounds(0,0,1000,700);
		this.setResizable(false);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
	
	