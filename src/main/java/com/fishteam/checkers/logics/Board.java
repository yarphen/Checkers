package com.fishteam.checkers.logics;

import java.util.Arrays;


public class Board {
	public static final int NONE = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	public static final int SIZE = 8;
	/**
	 * Fill it according to constants NONE/BLACK/WHITE
	 */
	private int [][] cells = new int[SIZE][SIZE];
	public int getCell(int x, int y){
		return cells[x][y];
	}
	public void setCell(int x, int y, int cellState){
		switch (cellState) {
		case NONE:
		case BLACK:
		case WHITE:
			cells[x][y]=cellState;
		default:
			throw new IllegalArgumentException();
		}
	}
	public boolean isOpened(int x, int y){
        return exists(x,y) && cells[y][x]==NONE;
    }

    /** Check if cell exists */
    private boolean exists(int x, int y){
        return !(x < 0 || y < 0 || y > cells.length-1 || x > cells[y].length-1 );
    }

    
    public Point rightTopPoint(Point current){
    	return new Point(current.getX() + 1,current.getY() - 1);
    }

	public Point rightBottomPoint(Point current){
        return new Point(current.getX() + 1,current.getY() + 1);
    }

    public Point leftBottomPoint(Point current){
        return new Point(current.getX() - 1,current.getY() + 1);
    }

    public Point leftTopPoint(Point current){
        return new Point(current.getX() - 1,current.getY() - 1);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
    	Board board = new Board();
    	for(int i=0; i<SIZE; i++){
        	for(int j=0; j<SIZE; j++){
        		board.cells[i][j]=cells[i][j];
        	}
    	}
		return board;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(cells);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(cells, other.cells))
			return false;
		return true;
	}
	public static int oppositeSide(boolean color) {
		return color?1:0;
	}
}
