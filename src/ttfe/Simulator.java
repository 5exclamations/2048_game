package ttfe;

import java.util.Random;

public class Simulator implements SimulatorInterface {
    private int width;
    private int height;
    private int[][] board;
    private int numMoves;
    private int points;
    private Random random;

    public Simulator(int width, int height, Random random) {
        if (width < 2) throw new IllegalArgumentException("Width can't be less than 2");
        if (height < 2) throw new IllegalArgumentException("Height can't be less than 2");
        if (random == null) throw new IllegalArgumentException("Random is null");
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        this.numMoves = 0;
        this.points = 0;
        this.random = random;
        addPiece();
        addPiece();
    }

    @Override
    public void addPiece(){
        if (!isSpaceLeft()) {
            throw new IllegalStateException("No free space left on the board");
        }
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int num;
        if (random.nextDouble() < 0.9) 
        num = 2;
        else num = 4;
        while (board[x][y] != 0){
            x = random.nextInt(width);
            y = random.nextInt(height);
        }
        board[x][y] = num;
    }

    @Override
    public int getBoardWidth(){
        return width;
    }

	@Override
	public int getBoardHeight(){
        return height;
    }

	@Override
	public int getNumMoves(){
        return numMoves;
    }

	@Override
	public int getNumPieces(){
        int num = 0;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if (board[x][y] != 0) num++;
            }
        }
        return num;
    }

	@Override
	public int getPieceAt(int x, int y){
        if (x >= width) throw new IllegalArgumentException("X is greater than width");
        if (x < 0) throw new IllegalArgumentException("X is less than zero");
        if (y >= height) throw new IllegalArgumentException("Y is greater than height");
        if (y < 0) throw new IllegalArgumentException("Y is less than zero");
        return board[x][y];
    }

	@Override
	public int getPoints(){
        return points;
    }

	@Override
	public boolean isMovePossible(){
        if(!isMovePossible(MoveDirection.EAST) && !isMovePossible(MoveDirection.WEST) 
        && !isMovePossible(MoveDirection.NORTH) && !isMovePossible(MoveDirection.SOUTH))  return false;
        return true;
    }

	@Override
	public boolean isMovePossible(MoveDirection direction){
        switch (direction) {
            case MoveDirection.NORTH:
                for (int x = 0; x < width; x++) {
                    for (int y = 1; y < height; y++) {
                        if (board[x][y] != 0 && (board[x][y - 1] == 0 || board[x][y - 1] == board[x][y])) {
                            return true;
                        }
                    }
                }
                break;
            case MoveDirection.SOUTH:
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height - 1; y++) {
                        if (board[x][y] != 0 && (board[x][y + 1] == 0 || board[x][y + 1] == board[x][y])) {
                            return true;
                        }
                    }
                }
                break;
            case MoveDirection.WEST:
                for (int y = 0; y < height; y++) {
                    for (int x = 1; x < width; x++) {
                        if (board[x][y] != 0 && (board[x - 1][y] == 0 || board[x - 1][y] == board[x][y])) {
                            return true;
                        }
                    }
                }
                break;
            case MoveDirection.EAST:
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width - 1; x++) {
                        if (board[x][y] != 0 && (board[x + 1][y] == 0 || board[x + 1][y] == board[x][y])) {
                            return true;
                        }
                    }
                }
                break;
                default:
                throw new IllegalArgumentException("Invalid direction");
        }
        return false;
    }

	@Override
	public boolean isSpaceLeft(){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if (board[x][y] == 0) return true;
            }
        }
        return false;
    }

	@Override
	public boolean performMove(MoveDirection direction){
        if (direction == null) throw new IllegalArgumentException("There is no value for direction");
        else {
        boolean moved = false;
        switch (direction) {
            case MoveDirection.NORTH:
                for (int x = 0; x < width; x++) {
                    int[] newColumn = new int[height];
                    int index = 0;
                    boolean merged = false;
                    for (int y = 0; y < height; y++) {
                        if (board[x][y] != 0) {
                            if (index > 0 && newColumn[index - 1] == board[x][y] && !merged) {
                                newColumn[index - 1] *= 2;
                                points += newColumn[index - 1];
                                merged = true;
                            } else {
                                newColumn[index++] = board[x][y];
                                merged = false;
                            }
                            if (board[x][y] != newColumn[y]) {
                                moved = true;
                            }
                        }
                    }
                    for (int y = 0; y < height; y++) {
                        board[x][y] = newColumn[y];
                    }
                }
                break;
            case MoveDirection.SOUTH:
                for (int x = 0; x < width; x++) {
                    int[] newColumn = new int[height];
                    int index = 0;
                    boolean merged = false;
                    for (int y = height - 1; y >= 0; y--) {
                        if (board[x][y] != 0) {
                            if (index > 0 && newColumn[index - 1] == board[x][y] && !merged) {
                                newColumn[index - 1] *= 2;
                                points += newColumn[index - 1];
                                merged = true;
                            } else {
                                newColumn[index++] = board[x][y];
                                merged = false;
                            }
                            if (board[x][y] != newColumn[height - 1 - y]) {
                                moved = true;
                            }
                        }
                    }
                    for (int y = 0; y < height; y++) {
                        board[x][height - 1 - y] = newColumn[y];
                    }
                }
                break;
            case MoveDirection.WEST:
                for (int y = 0; y < height; y++) {
                    int[] newRow = new int[width];
                    int index = 0;
                    boolean merged = false;
                    for (int x = 0; x < width; x++) {
                        if (board[x][y] != 0) {
                            if (index > 0 && newRow[index - 1] == board[x][y] && !merged) {
                                newRow[index - 1] *= 2;
                                points += newRow[index - 1];
                                merged = true;
                            } else {
                                newRow[index++] = board[x][y];
                                merged = false;
                            }
                            if (board[x][y] != newRow[x]) {
                                moved = true;
                            }
                        }
                    }
                    for (int x = 0; x < width; x++) {
                        board[x][y] = newRow[x];
                    }
                }
                break;
            case MoveDirection.EAST:
                for (int y = 0; y < height; y++) {
                    int[] newRow = new int[width];
                    int index = 0;
                    boolean merged = false;
                    for (int x = width - 1; x >= 0; x--) {
                        if (board[x][y] != 0) {
                            if (index > 0 && newRow[index - 1] == board[x][y] && !merged) {
                                newRow[index - 1] *= 2;
                                points += newRow[index - 1];
                                merged = true;
                            } else {
                                newRow[index++] = board[x][y];
                                merged = false;
                            }
                            if (board[x][y] != newRow[width - 1 - x]) {
                                moved = true;
                            }
                        }
                    }
                    for (int x = 0; x < width; x++) {
                        board[width - 1 - x][y] = newRow[x];
                    }
                }
                break;
        }
        return true;
    }
    }

	@Override
	public void run(PlayerInterface player, UserInterface ui){
        if (player == null) throw new IllegalArgumentException("player value is null");
        if (ui == null) throw new IllegalArgumentException("ui value is null");
        ui.updateScreen(this);
        do {
                    MoveDirection move = player.getPlayerMove(this, ui); 
                    if (performMove(move)) {
                        numMoves++;
                        if (isSpaceLeft()) addPiece();
                        ui.updateScreen(this);
                    } 
                } while (isMovePossible());
                ui.showGameOverScreen(this);
    }

	@Override
	public void setPieceAt(int x, int y, int piece){
        if (x >= width) throw new IllegalArgumentException("X is greater than width");
        if (x < 0) throw new IllegalArgumentException("X is less than zero");
        if (y >= height) throw new IllegalArgumentException("Y is greater than height");
        if (y < 0) throw new IllegalArgumentException("Y is less than zero");
        if (piece < 0) throw new IllegalArgumentException("Piece value is less than zero");
        board[x][y] = piece;
    }
}
