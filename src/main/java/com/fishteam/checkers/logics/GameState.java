package com.fishteam.checkers.logics;

import com.fishteam.checkers.interfaces.ProblemState;

public class GameState implements ProblemState {
	private Checker mainChecker;
	private Board board;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mainChecker == null) ? 0 : mainChecker.hashCode());
		return result;
	}
	/**
	 * Board variants doesn't matter for main checker
	 */
	@Override
	public boolean equals(Object obj) {
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
}
