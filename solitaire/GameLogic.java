import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class GameLogic {
	public Image cardBackImage;
	private CardPile[] piles;
	private boolean firstDeal;
	public boolean endGame;
	
	GameLogic()
	{
		try {
			cardBackImage = ImageIO.read(new File("D:\\solitaire-files\\c0.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		piles = new CardPile[13];
		
		for (int i=0; i < 13; i++)
		{
			piles[i] = new CardPile();
		}
		
		start();
	}
	
	 // При захвате карты мышью

	public void mouseDragged(int mX, int mY)

	{

	}

	// При одиночном нажатии левой кнопки мыши

	public void mousePressed(int mX, int mY)

	{

	}

	// При двойном щелчке левой клавишей мыши

	public void mouseDoublePressed(int mX, int mY)

	{

	}

	// При отпускании левой кнопки мыши

	public void mouseReleased(int mX, int mY)
	{
		int number = getNumberColumnPress(mX, mY);
		
		if (number == 0)
		{
			// Делаем выдачу карты
			deal();
		}
	}

	// Определение стопки на которую нажали мышью

	private int getNumberColumnPress(int mX, int mY)
	{

		 // Если стопка не выбрана

		int number=-1;

		// Если курсор находится в зоне верхних стопок
		if ((mY>=15)&& (mY<=(15+97)))
		{
			if ((mX>=30) && (mX<=(30+72))) number = 0;
	
			if ((mX>=140) && (mX<=(140+72))) number = 1;
	
			if ((mX>=360) && (mX<=(360+72))) number = 2;
	
			if ((mX>=470) && (mX<=(470+72))) number = 3;
	
			if ((mX>=580) && (mX<=(580+72))) number = 4;
	
			if ((mX>=690) && (mX<=(690+72))) number = 5;
		}
		// Если курсор находится в зоне нижних стопок
		else if ((mY>=130) && (mY<=(700)))
		{
			if ((mX>=30) && (mX<=110*7))
			{
					if (((mX-30)%110)<=72)
					{
						number = (mX-30)/110;

						number += 6;
					}
			}
		}
		return number; 
	}

	// Выдача карт из верхней левой стопки
	private void deal()
	{
		if (piles[0].size() > 0)
		{
			int number;
			
			if (firstDeal)
			{
				number = (int)(Math.random()*piles[0].size());
			}
			else 
			{
				number = piles[0].size()-1;
			}
			Card gotCard = piles[0].get(number);
			
			gotCard.isFaceUp = true;
			gotCard.x += 110;
			piles[1].add(gotCard);
			piles[0].remove(number);
			
		} 
		else 
		{
			int next = piles[1].size()-1;
			
			for (int i = next; i>=0; i--)
			{
				Card gotCard = piles[1].get(i);
				gotCard.isFaceUp = false;
				gotCard.x -= 110;
				piles[0].add(gotCard);
			}
			
			piles[1].clear();
			firstDeal = false;
		}
	}

	// Старт игры - Новая игра

	public void start()
	{
		for (int i=0;i<13;i++)
		{
			piles[i].clear();
		}
		load();
		endGame = false;
		firstDeal = true;
	}

	// Загрузка изображений колоды

	private void load()
	{
		for (int i = 1; i <= 52; i++)
		{
			piles[0].add(new Card("D:\\solitaire-files\\c"+(i)+".png", cardBackImage, i));
		}
	}

	// Метод отрисовки всех стопок карт

	public void drawColumn(Graphics gr)
	{
		if (piles[0].size() > 0)
		{
			piles[0].get(piles[0].size() - 1).draw(gr);
		}
		if (piles[1].size()>1)
		{
			 // Получаем и рисуем вторую сверху карту

			piles[1].get(piles[1].size()-2).draw(gr);

			// Получаем и рисуем самую верхнюю карту

			piles[1].get(piles[1].size()-1).draw(gr); 
		}
		else if (piles[1].size()==1)
		{
			 // Получаем и рисуем самую верхнюю карту
			piles[1].get(piles[1].size()-1).draw(gr);
		}
		
	}
}
