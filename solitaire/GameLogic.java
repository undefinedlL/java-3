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
	// Номер стопки захваченной пользователем
	private int pileNumber;
	// Номер карты в стопке захваченной пользователем
	private int cardNumber;
	// Смещения координат курсора мыши
	//относительно координат карты
	private int dx, dy;
	// Координаты карты до начала переноса мышью
	private int oldX, oldY;
	// Таймер для эффекта окончания игры
	private Timer endGameTimer;
	
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
		
		endGameTimer = new Timer(100, e -> {
			for (int i = 2; i <= 5; i++)
			{
				// Получаем самую нижнюю карту
				Card gotCard = piles[i].get(0);
				// Нижнюю карту добавляем наверх
				piles[i].add(gotCard);
				// Удаляем нижнюю карту
				piles[i].remove(0);
			}
		});
		
		start();
	}
	
	// Проверка возможности переноса и перенос,
	// если возможно это сделать
	// nom1 - стопка ИЗ которой перенос
	// nom2 - стопка В которую перенос

	private boolean isValidMove(int n1, int n2)
	{
	    // Результат проверки
	    boolean res = false;

	    // Карта, которая переносится
	    Card gotCard1 = piles[n1].get(cardNumber);
	    Card gotCard2 = null;

	    // Если есть карты в стопке
	    if (piles[n2].size() > 0)
	    {
	        // Получаем верхнюю карту
	    	gotCard2 = piles[n2].get(piles[n2].size() - 1);
	    }

	    // Если четыре домашние стопки
	    if ((n2 >= 2) && (n2 <= 5))
	    {
	        if (cardNumber == (piles[n1].size() - 1))
	        {
	            // Если стопка была пустая
	            if (gotCard2 == null)
	            {
	                // Если переносимая карта ТУЗ
	                if (gotCard1.rank == 12) res = true;
	            }
	            // Если в домашней стопке ТУЗ, переносится
	            // ДВОЙКА и масти совпадают
	            else if ((gotCard2.rank == 12)
	                    && (gotCard1.suit == gotCard2.suit)
	                    && (gotCard1.rank == 0))
	            {
	                res = true;
	            }
	            // Если в домашней стопке не ТУЗ,
	            // но масти совпадают
	            else if ((gotCard2.rank >= 0)
	                    && (gotCard2.rank < 11)
	                    && (gotCard1.suit == gotCard2.suit))
	            {
	                // Если переносимая карта по рангу выше на один
	                if ((gotCard2.rank + 1 == gotCard1.rank))
	                {
	                    res = true;
	                }
	            }

	            // Если результат проверки положительный
	            if (res == true)
	            {
	                // Переносим карту в домашнюю стопку
	            	gotCard1.x = (110 * (n2 + 1)) + 30;
	            	gotCard1.y = 15;
	                piles[n2].add(gotCard1);
	                piles[n1].remove(cardNumber);

	                checkEndGame();
	            }
	        }
	    }

	    // Если перенос в нижние стопки
	    if ((n2 >= 6) && (n2 <= 12))
	    {
	        int x = 30 + (n2 - 6) * 110;
	        int y = 130;

	        // Если нижняя стопка была пустая
	        if (gotCard2 == null)
	        {
	            // Если переносится КОРОЛЬ
	            if (gotCard1.rank == 11) res = true;
	        }
	        else // Если была НЕ пустая
	        {
	            // Если верхняя карта открыта
	            if (gotCard2.isFaceUp)
	            {
	                // Если переносим НЕ на ТУЗА
	                if (gotCard2.rank != 12)
	                {
	                    // Если переносимая карта на один младше или
	                    // ТУЗ переносится на двойку
	                    if ((gotCard2.rank == gotCard1.rank + 1) ||
	                        ((gotCard2.rank == 0) && (gotCard1.rank == 12)))
	                    {
	                        // Если одна масть ЧЕРНАЯ, а другая КРАСНАЯ
	                        if (gotCard2.isRed != gotCard1.isRed)
	                        {
	                            y = gotCard2.y + 20;
	                            res = true;
	                        }
	                    }
	                }
	            }
	        }

	        // Если результат проверки положительный
	        if (res)
	        {
	            // Добавляем все карты в новую стопку
	            for (int i = cardNumber; i < piles[n1].size(); i++)
	            {
	                Card gotCard_ = piles[n1].get(i);
	                gotCard_.x = x;
	                gotCard_.y = y;
	                piles[n2].add(gotCard_);
	                y += 20;
	            }

	            // Удаляем все карты из старой стопки
	            for (int i = piles[n1].size() - 1; i >= cardNumber; i--)
	            {
	            	piles[n1].remove(i);
	            }
	        }
	    }

	    // Возвращаем результат
	    return res;
	}
	
	private void checkEndGame()
	{
		//Проверяем, что во всех четырех домашних стопках по 13 карт
		if (
			(piles[2].size() == 13) &&
			(piles[3].size() == 13) &&
			(piles[4].size() == 13) &&
			(piles[5].size() == 13)
		)
		{
			// Признак окончания игры
			endGame = true;
			endGameTimer.start();
		}
	}
	
	private void openCard()
	{
		for (int i = 6; i <= 12; i++)
		{
			if (piles[i].size() > 0)
			{
				// Номер последней карты в стопке
				int lastCardNumber = piles[i].size() - 1;
				// Получаем последнюю карту
				Card gotCard = piles[i].get(lastCardNumber);
				// Если карты отображается рубашкой,то открываем ее
				if (gotCard.isFaceUp==false) gotCard.isFaceUp = true;
			}
		}
	}
	
	private void setSelected(int number, int mX, int mY)
	{
		// Если верхние стопки (1,2,3,4,5)
		if ((number>=1) && (number<=5))
		{
			// Если в стопке есть карты
			if (piles[number].size()>0)
			{
				// Получаем номер верхней 
				int lastCardNumber = piles[number].size() - 1;
				//Получаем верхнюю карту
				Card gotCard = piles[number].get(lastCardNumber);
				// Устанавливаем признак выбранной карты
				gotCard.isSelected = true;
				// Номер выбранной карты
				cardNumber = lastCardNumber;
				// Номер выбранной стопки
				pileNumber = number;
				// Смещения курсора мыши
				dx = mX - gotCard.x;
				dy = mY - gotCard.y;

				//Запоминаем текущие координаты карты
				
				 oldX = gotCard.x;
				 oldY = gotCard.y;
			}
		}
		
		// Если нижние семь стопок
		else if ((number>=6) && (number<=12))
		{
			// Если в стопке есть карты 
			if (piles[number].size()>0)
			{
				// Получаем номер верхней карты
				int lastCardNumber = piles[number].size() -1 ;
				// Получаем верхнюю карту
				Card gotCard = piles[number].get(lastCardNumber);
				int selectedNumber = -1;
				// Если выбрана самая верхняя карта
				if ((mY>=gotCard.y)&&(mY<=(gotCard.y+97)))
				{
					selectedNumber = lastCardNumber;
				} 
				else if (mY<gotCard.y)
				{
					// Вычисляем номер выбранной карты
					selectedNumber = (mY-130)/20;
					if (piles[number].get(selectedNumber).isFaceUp==false)
					{
						selectedNumber = -1;
		
					}
				}
				// Если карта выбрана
				if (selectedNumber!=-1)
				{// Получаем выбранную карту
					Card gotSelectedCard = piles[number].get(selectedNumber);
					// Если карта открыта рубашкой
					if (gotSelectedCard.isFaceUp)
					{
						//Устанавливаем признак выбранной 
						gotSelectedCard.isSelected = true;
						// Номер выбранной карты
						cardNumber = selectedNumber;
						//Номер выбранной стопки
						pileNumber = number;
						//Смещения курсора мыши
						dx = mX - gotSelectedCard.x;
						dy = mY - gotSelectedCard.y;
						//Запоминаем текущие координаты карты
						 oldX = gotSelectedCard.x;
						 oldY = gotSelectedCard.y;
					}
				}
			}
		}
	}
	
	 // При захвате карты мышью
	public void mouseDragged(int mX, int mY)
	{
		if (pileNumber >= 0)// Если стопка выбрана
		{
			// Получаем выбранную карту
			Card gotCard = piles[pileNumber].get(cardNumber);
			// Изменяем координаты карты по курсору мыши
			gotCard.x = mX-dx;
			gotCard.y = mY-dy;
			// Ограничение области переноса карт
			 if (gotCard.x<0) gotCard.x = 0;
			 if (gotCard.x>720) gotCard.x = 720;
			 if (gotCard.y<0) gotCard.y = 0;
			 if (gotCard.y>650) gotCard.y = 650;
			 // Все остальные карты в переносимой группе карт 
			 // размещаем со сдвигом вниз на 20пикселей
			  int y=20;

			  for (int i=cardNumber+1;i<piles[pileNumber].size();i++)
			  {
				   piles[pileNumber].get(i).x = gotCard.x;
				   piles[pileNumber].get(i).y = gotCard.y + y;

				   y += 20;
			  }
		}
	}

	// При одиночном нажатии левой кнопки мыши
	public void mousePressed(int mX, int mY)
	{
		//Определяем номер стопки
		int number = getNumberColumnPress(mX, mY);
		//Устанавливаем выбранную карту
		setSelected(number, mX, mY);
	}

	// При двойном щелчке левой клавишей мыши
	public void mouseDoublePressed(int mX, int mY)
	{
		//Определяем номер стопки
		int number = getNumberColumnPress(mX, mY);
		// Если это нижняя стопка или с номером 1
		if ((number==1) || ((number>=6)&&(number<=12)))
		{
			// Если в стопке есть карты
			if (piles[number].size()>0)
			{
				// Номер верхней карты
				int lastCardNumber = piles[number].size()-1;
				// Получаем верхнюю карту
				Card gotCard = piles[number].get(lastCardNumber);
				if ((mY>=gotCard.y)&&(mY<=(gotCard.y+97)))
				{
					// Перебираем четыре домашние стопки
					for (int i=2;i<=5;i++)
					{
						 // Результат поиска подходящей домашней стопки
						int res = -1;
						// Если домашняя стопка пустая
						if (piles[i].size()==0)
						{
							// Если переносимая карта -туз
							if (gotCard.rank==12) 
							{
								//Запоминаем номер домашней стопки 
								res = i; 
							}
						}
						// Если домашняя стопка уже не пустая
						else 
						{
							// Получаем номер последней карты в домашней стопке
							int homeCardNumber = piles[i].size()-1;
							// Получаем саму карту
							Card gotHomeCard = piles[i].get(homeCardNumber);
							// Если эта карта в домашней стопке - туз, а 
							//переносим двойку и их масти совпадают
							if (
								(gotHomeCard.rank==12)&&
								(gotCard.suit==gotHomeCard.suit)&&
								(gotCard.rank==0)
							) {
								// Запоминаем номер домашней стопки
								res = i;
							}
							// Если эта карта в домашней стопке НЕ туз, 
							// а их масти совпадают 
							 else if ((gotHomeCard.rank>=0)&&
									 (gotHomeCard.rank<11)&&
									 (gotCard.suit==gotHomeCard.suit)
									 )
							 {
								 // Если переносимая карта на один уровень старше
								 if ((gotHomeCard.rank+1==gotCard.rank))
								 {
									 res = i;
								 }
							 }
						}
						
						// Если удалось найти подходящую домашнюю стопку
						if (res>=0)
						{
							// Изменяем координаты на домашнюю стопку
							gotCard.x = (110*(res+1))+30;
							gotCard.y = 15;
							// Добавляем в домашнюю стопку
							piles[res].add(gotCard);
							// Удаляем из старой стопки
							piles[number].remove(lastCardNumber);
							// Проверяем конец игры
							checkEndGame();
							//  Прерываем цикл
							break;
						}
					}
				}
			}
		}
		//Открываем верхнюю карту
		openCard();
	}

	// При отпускании левой кнопки мыши
	public void mouseReleased(int mX, int mY)
	{
		int number = getNumberColumnPress(mX, mY);
		// Если какая-то стопка выбрана в режиме переноса
		if (pileNumber != -1)
		{
			// Убираем признак у выбранной карты
			piles[pileNumber].get(cardNumber).isSelected = false;
			
			// Если после переноса стопка не выбрана или перенос
			// оказался ошибочным
			if ((number==-1)||(isValidMove(pileNumber, number)==false))
			{
				int y = 0;
				//Возвращаем все переносимые карты назад
				for (int i=cardNumber;i<piles[pileNumber].size();i++)
				{
					// Получаем карту
					Card gotCard = piles[pileNumber].get(i);
					// Устанавливаем координаты X,Y до переноса
					gotCard.x = oldX;
					gotCard.y = oldY + y;
					y+= 20;
				}
			}
			// Сброс выбранной карты
			pileNumber = -1;
			cardNumber = -1;
			// Открытие верхней карты
			openCard();
		}
		else 
		{
			// Если верхняя левая стопка
			if (number==0)
			{
				// Делаем выдачу карты
				initialDeal();
			}
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

	// раздача карт в нижние семь стопок
	private void dealCards()
	{
		// начальная координата
		int x = 30;
		
		// Перебираем все стопки нижние семь стопок
		for (int i = 6; i < 13; i++)
		{
			//Добавление карт в стопку
			for (int j=6;j<=i;j++)
			{
				//Получаем номер случайной карты из верхней левой стопки
				int rnd = (int)(Math.random()*piles[0].size());
				Card gotCard = piles[0].get(rnd);
				
				// Если карта не самая верхняя,
				// то показываем ее рубашкой
				if (j < i) gotCard.isFaceUp = false;
				else gotCard.isFaceUp = true;
				// Координата по X
				gotCard.x = x;
				// Каждую следующую карту располагаем ниже на 20пикселей
				gotCard.y = 130+piles[i].size()*20;
				// Добавляем карту в нижнюю стопку
				piles[i].add(gotCard);
				// Удаляем карту из верхней левой стопки
				piles[0].remove(rnd); 
				
				
			}
			//Увеличиваем координату по X
				// (смещаемся правее)
			x+=110;
		}
	}
	
	// Выдача карт из верхней левой стопки
	private void initialDeal()
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
		dealCards();
		endGame = false;
		firstDeal = true;
		
		cardNumber = -1;
		pileNumber = -1;
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
		
		for (int i = 2; i <= 5; i++)
		{
			if (piles[i].size() > 1)
			{
				// получаем и рисуем вторую сверху карту
				piles[i].get(piles[i].size() - 2).draw(gr);
			} else if (piles[i].size() == 1) 
			{
				piles[i].get(piles[i].size() - 1).draw(gr);
			}
		}
		
		for (int i = 6; i < 13; i++)
		{
			//Если в стопке есть карты 
			if (piles[i].size() > 0)
			{
				// Перебираем все карты из стопки
				for (int j = 0; j < piles[i].size(); j++)
				{
					// Если находим выбранную карту, то прерываем цикл
					if (piles[i].get(j).isSelected) break;
					// рисуем карты
					piles[i].get(j).draw(gr);
				}
			}
		}
		
		// ПЕРЕНОСИМЫЕ МЫШЬЮ КАРТЫ
		// Если имеется выбранная стопка
		if (pileNumber != -1)
		{
			// Перебираем карты от выбранной и до конца стопки
			for (int i = cardNumber; i < piles[pileNumber].size(); i++)
			{
				// Рисуем карты
				piles[pileNumber].get(i).draw(gr);
			}
		}
	}
}












