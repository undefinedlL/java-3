
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








