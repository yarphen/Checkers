
package com.fishteam.checkers.logics;

import com.fishteam.checkers.interfaces.ProblemState;

public class GameState implements ProblemState {
	private Checker mainChecker = new Checker();
	private Board board = new Board();
	public Checker getMainChecker() {
		return mainChecker;
	}
	public void setMainChecker(Checker mainChecker) {
		this.mainChecker = mainChecker;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * Board variants doesn't matter for main checker
	 */
	@Override
	public String toString() {
		return "GameState [mainChecker=" + mainChecker + ", board=\r\n" + board + "\r\n]";
	}
	public boolean simpleEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (mainChecker == null) {
			if (other.mainChecker != null)
				return false;
		} else if (!mainChecker.equals(other.mainChecker))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((mainChecker == null) ? 0 : mainChecker.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		return deepEquals(obj);
	}
	public boolean deepEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (mainChecker == null) {
			if (other.mainChecker != null)
				return false;
		} else if (!mainChecker.equals(other.mainChecker))
			return false;
		return true;
	}
	
}