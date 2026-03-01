
public class GameLogic {
	public int[][] player_array;
	
	public GameLogic()
	{
		player_array = new int[10][10];
		
	}
	
	public void start()
	{
		// очищаем игровое поле игрока
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				player_array[i][j] = 0;
			}
		}
		
		create4P(player_array);
		create1p(player_array);
		
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
	// генерация четырехпалубного корабля
	private void create4P(int[][] arr) 
	{
		// координаты головы корабля
		int i = 0, j = 0;
		
		// создание первой палубы 4-х палубного корабля
		i = (int)(Math.random()*10);
		j = (int)(Math.random()*10);
		
		// помещаем значение
		arr[i][j] = 4;
		
		spaceBegin(arr, i, j, -2);
		
		// определение направления
		// 0 - вверх 1 - вправо, 2 - вниз, 3 - влево 
		int direction = (int)(Math.random()*4);
		
		if (direction == 0) 
		{
			if (testNewPaluba(arr, i-3, j) == false)
			{
				direction = 2;
			}
		} 
		else if (direction == 1)
		{
			if (testNewPaluba(arr, i, j+3) == false)
			{
				direction = 3;
			}
		}
		else if (direction == 2)
		{
			if (testNewPaluba(arr, i+3, j) == false)
			{
				direction = 0;
			}
		}
		else if (direction == 3)
		{
			if (testNewPaluba(arr, i, j-3) == false)
			{
				direction = 1;
			}
		}
		
		if (direction == 0)
		{
			for (int r = 3; r >= 1; r--)
			{
				arr[i-r][j] = 4;
				if (r == 3) {
					spaceBegin(arr, i-r, j, -2);					
				}
			}
		} 
		else if (direction == 1) // right
		{
			for (int c = 3; c >= 1; c--)
			{
				arr[i][j+c] = 4;
				if (c == 3) {
					spaceBegin(arr, i, j+c, -2);					
				}
				
			}
		} 
		else if (direction == 2) // вниз
		{
			for (int r = 3; r >= 1; r--)
			{
				arr[i+r][j] = 4;
				if (r == 3) {
					spaceBegin(arr, i+r, j, -2);					
				}
			}
		}
		else if (direction == 3) // влево
		{
			for (int c = 3; c >= 1; c--)
			{
				arr[i][j-c] = 4;
				if (c == 3) {
					spaceBegin(arr, i, j-c, -2);					
				}
			}
		} 
		
		spaceEnd(arr);
	}
	
	private void create1p(int arr[][])
	{
		for (int k = 1; k <= 4; k++)
		{
			while (true)
			{
				// находим случайную позицию на игорвом поле
				int i = (int)(Math.random() * 10);
				int j = (int)(Math.random() * 10);
				
				// проверка
				if (arr[i][j] == 0)
				{
					arr[i][j] = 1;
					spaceBegin(arr, i, j, -1);
					break;
				}
			}
		}
	}
}








