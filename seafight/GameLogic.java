
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
		
		player_array[0][0] = 1;
		
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
	
}








