public class GameLogic {

    public int[][] player_array;
    public int[][] computer_array;

    public boolean computer_turn = false;
    public int end_game;

    public GameLogic() {
        player_array = new int[10][10];
        computer_array = new int[10][10];
    }

    public void start() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                player_array[i][j] = 0;
                computer_array[i][j] = 0;
            }
        }

        end_game = 0;
        computer_turn = false;

        placeShips(player_array);
        placeShips(computer_array);
    }

    private void placeShips(int[][] arr) {

        createShip(arr, 4);

        for (int i = 0; i < 2; i++)
            createShip(arr, 3);

        for (int i = 0; i < 3; i++)
            createShip(arr, 2);

        for (int i = 0; i < 4; i++)
            createShip(arr, 1);
    }

    private boolean testArrayPos(int i, int j) {
        return i >= 0 && i < 10 && j >= 0 && j < 10;
    }

    private void createShip(int[][] arr, int size) {

        while (true) {

            int i = (int) (Math.random() * 10);
            int j = (int) (Math.random() * 10);
            int direction = (int) (Math.random() * 2); // 0 горизонталь

            boolean canPlace = true;

            for (int k = 0; k < size; k++) {

                int x = i;
                int y = j;

                if (direction == 0)
                    y += k;
                else
                    x += k;

                if (!testArrayPos(x, y) || arr[x][y] != 0) {
                    canPlace = false;
                    break;
                }

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {

                        int nx = x + dx;
                        int ny = y + dy;

                        if (testArrayPos(nx, ny) && arr[nx][ny] > 0) {
                            canPlace = false;
                        }
                    }
                }

                if (!canPlace)
                    break;
            }

            if (!canPlace)
                continue;

            for (int k = 0; k < size; k++) {

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

    private void isKilled(int[][] arr, int i, int j) {

        if (arr[i][j] == 8) {

            arr[i][j] += 7;
            spaceHurted(arr, i, j);

        } else if (arr[i][j] == 9)
            checkKilled(arr, i, j, 2);

        else if (arr[i][j] == 10)
            checkKilled(arr, i, j, 3);

        else if (arr[i][j] == 11)
            checkKilled(arr, i, j, 4);
    }

    private void checkKilled(int[][] arr, int i, int j, int size) {

        int count = 0;

        for (int k = i - (size - 1); k <= i + (size - 1); k++) {
            for (int g = j - (size - 1); g <= j + (size - 1); g++) {

                if (testArrayPos(k, g) && arr[k][g] == size + 7)
                    count++;
            }
        }

        if (count == size) {

            for (int k = i - (size - 1); k <= i + (size - 1); k++) {
                for (int g = j - (size - 1); g <= j + (size - 1); g++) {

                    if (testArrayPos(k, g) && arr[k][g] == size + 7) {

                        arr[k][g] += 7;
                        spaceHurted(arr, k, g);
                    }
                }
            }
        }
    }

    private void setSpaceHurted(int[][] arr, int i, int j) {

        if (testArrayPos(i, j)) {

            if (arr[i][j] == -1 || arr[i][j] == 6)
                arr[i][j]--;
        }
    }

    private void spaceHurted(int[][] arr, int i, int j) {

        setSpaceHurted(arr, i - 1, j - 1);
        setSpaceHurted(arr, i - 1, j);
        setSpaceHurted(arr, i - 1, j + 1);

        setSpaceHurted(arr, i, j + 1);

        setSpaceHurted(arr, i + 1, j + 1);
        setSpaceHurted(arr, i + 1, j);
        setSpaceHurted(arr, i + 1, j - 1);

        setSpaceHurted(arr, i, j - 1);
    }

    public void playerShot(int i, int j) {

        if (!testArrayPos(i, j))
            return;

        if (computer_array[i][j] >= 7)
            return;

        computer_array[i][j] += 7;

        isKilled(computer_array, i, j);
        isEndGame();

        if (computer_array[i][j] < 8) {

            computer_turn = true;

            while (computer_turn)
                computer_turn = computerShot();
        }
    }

    private void isEndGame() {

        int killed_player = 0;
        int killed_comp = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (player_array[i][j] >= 15)
                    killed_player++;

                if (computer_array[i][j] >= 15)
                    killed_comp++;
            }
        }

        if (killed_player == 20)
            end_game = 2;

        if (killed_comp == 20)
            end_game = 1;
    }

    private boolean shoot(int i, int j) {

        player_array[i][j] += 7;
        isKilled(player_array, i, j);

        return player_array[i][j] >= 8;
    }

    boolean computerShot() {

        int[][] dirs = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1}
        };

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (player_array[i][j] >= 9 && player_array[i][j] <= 11) {

                    for (int[] d : dirs) {

                        int ni = i + d[0];
                        int nj = j + d[1];

                        if (testArrayPos(ni, nj) &&
                                player_array[ni][nj] <= 4) {

                            boolean result = shoot(ni, nj);
                            isEndGame();
                            return result;
                        }
                    }
                }
            }
        }

        for (int s = 0; s < 100; s++) {

            int i = (int) (Math.random() * 10);
            int j = (int) (Math.random() * 10);

            if (player_array[i][j] <= 4) {

                boolean result = shoot(i, j);
                isEndGame();
                return result;
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (player_array[i][j] <= 4) {

                    boolean result = shoot(i, j);
                    isEndGame();
                    return result;
                }
            }
        }

        return false;
    }
}