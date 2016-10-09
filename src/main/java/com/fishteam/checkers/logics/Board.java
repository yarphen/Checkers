package com.fishteam.checkers.logics;

import java.util.Arrays;


public class Board {
	public static final int NOT_EXISTS = -1;
	public static final int NONE = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	public static final int SIZE = 8;
	/**
	 * Fill it according to constants NONE/BLACK/WHITE
	 */
	private int [][] cells = new int[SIZE][SIZE];
	public int[][] getCells() {
		return cells;
	}
	public void setCells(int[][] cells) {
		this.cells = cells;
	}
	public int getCell(int x, int y){
		return (exists(x,y))?cells[x][y]:NOT_EXISTS;
	}
	public void setCell(int x, int y, int cellState){
		switch (cellState) {
		case NONE:
		case BLACK:
		case WHITE:
			cells[x][y]=cellState;
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
	public boolean isOpened(int x, int y){
        return getCell(x,y)==NONE;
    }

    /** Check if cell exists */
    private boolean exists(int x, int y){
        return !(x < 0 || y < 0 || x > SIZE-1 || y > SIZE-1 );
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
		return color?2:1;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<SIZE; i++){
        	for(int j=0; j<SIZE; j++){
        		builder.append(cells[i][j]);
        	}
    		if (i!=SIZE-1){
    			builder.append("\r\n");
    		}
    	}
		return builder.toString();
	}
}
