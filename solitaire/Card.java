import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Card {

	public int x, y;
	
	public Image img;
	 // Вид расположения рубашкой или картинкой
	public boolean isFaceUp;
	//Изображение рубашки карты
	public Image cardBackImage;
	// Масть карты
	public int suit;
	// Тип карты(король, туз и т.д.)
	public int rank;
	// Признак захвата карты мышью
	public boolean isSelected;
	// Признак красной или черной масти
	public boolean isRed;
	
	Card(String path, Image cardBackImage, int number)
	{
		isSelected = false;
		this.cardBackImage = cardBackImage;
		try {
			img = ImageIO.read(new File(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		x = 30;
		y = 15;
		
		isFaceUp  = false;
		
		suit = (number-1)%4;
		
		rank = (number-1)/4;
		
		isRed = (suit == 1 || suit == 2);
		
	}
	
	public void draw(Graphics gr)
	{
		if (isFaceUp)
		{
			gr.drawImage(img, x , y, 72, 97, null);
		}
		else 
		{
			gr.drawImage(cardBackImage, x , y, 72, 97, null);
		}
		
		if (isSelected == true)
		{
			 gr.setColor(Color.YELLOW);

			 gr.drawRect(x, y, 72, 97);
		}
	}
}













