import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Field extends JPanel implements MouseListener, MouseMotionListener {
	private Timer timerDraw;
	private JButton newGameBtn,ExitBtn;
	private Image bg;
	private GameLogic game;
	
	Field()
	{
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		game = new GameLogic();
		
		try {
			bg = ImageIO.read(new File("D:\\solitaire-files\\bg.jpg"));
		} catch(Exception e) {}
		
		this.setLayout(null);
		
		newGameBtn = new JButton();
		newGameBtn.setText("Новая игра");
		newGameBtn.setForeground(Color.red);
		newGameBtn.setFont(new Font("serif", 0, 20));
		newGameBtn.setBounds(820,150,150,50);
		newGameBtn.addActionListener(e -> {
			game.start();
		});
		this.add(newGameBtn);
		
		ExitBtn = new JButton();
		ExitBtn.setText("Выход");
		ExitBtn.setForeground(Color.blue);
		ExitBtn.setFont(new Font("serif", 0, 20));
		ExitBtn.setBounds(820,50,150,50);
		ExitBtn.addActionListener(e -> {
			System.exit(0);
		});
		this.add(ExitBtn);
		
		timerDraw = new Timer(20, e -> {
			repaint();
		});
		timerDraw.start();
	}
	
	
	public void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		
		gr.drawImage(bg,0,0,1000,700,null);
		gr.setColor(Color.WHITE);
		for (int i=0;i<7;i++)
		{

			if (i!=2) gr.drawRect(30+i*110, 15, 72, 97);

		}
		
		 for (int i=0;i<7;i++)
		 {
	
			 gr.drawRect(30+i*110, 130, 72, 97);
	
		 }
		 
		 game.drawColumn(gr);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (!game.endGame)
		{
			int mX = e.getX();
			int mY = e.getY();
			
			game.mouseDragged(mX, mY);
		}
			
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		if (!game.endGame)
		{
			int mX = e.getX();
			int mY = e.getY();
			
			if ((e.getButton()==1)&&(e.getClickCount()==1))
			{
				game.mousePressed(mX, mY);
			}
			else if ((e.getButton()==1)&&(e.getClickCount()==2)) {
				game.mouseDoublePressed(mX, mY);
			}
		}
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (!game.endGame)
		{
			int mX = e.getX();
			int mY = e.getY();
		
			if (e.getButton()==1) 
			{
				game.mouseReleased(mX, mY);
			}
		
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	
}
