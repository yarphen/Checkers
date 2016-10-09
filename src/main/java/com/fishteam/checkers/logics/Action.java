package com.fishteam.checkers.logics;

public class Action {
	private String id;
	private Integer x,y;
	private Integer checkerState;
	private String algorithm;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getCheckerState() {
		return checkerState;
	}
	public void setCheckerState(int checkerState) {
		this.checkerState = checkerState;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	@Override
	public String toString() {
		return "Action [id=" + id + ", x=" + x + ", y=" + y + ", checkerState=" + checkerState + ", algorithm="
				+ algorithm + "]";
	}
	
}
