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
