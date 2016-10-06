package com.fishteam.checkers.logics;

public class Action {
	private String id;
	private int x,y;
	private int checkerState;
	private String algorythm;
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
	public String getAlgorythm() {
		return algorythm;
	}
	public void setAlgorythm(String algorythm) {
		this.algorythm = algorythm;
	}
	
}
