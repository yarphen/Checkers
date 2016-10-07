package com.fishteam.checkers.logics;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import com.fishteam.checkers.interfaces.Problem;
import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.interfaces.ProblemState;

public class Game implements Problem{
	private GameState startState;
	private GameState goalState;
	private String id = new Random().nextLong()+"";
	public Way solveWith(ProblemSolver problemSolver) {
		Way way = new Way();
		List<ProblemState> list = problemSolver.solve(this);
		way.setStates(list.toArray(new GameState[0]));
		return way;
	}

	public String getId() {
		return id ;
	}

	public ProblemState getStartState() {
		return startState;
	}

	public ProblemState getGoalState() {
		return goalState;
	}

	public List<ProblemState> getChildren(ProblemState father) {
		List<Function<Point, Point>> functions = new LinkedList<Function<Point,Point>>();
		final List<ProblemState> children = new LinkedList<ProblemState>();
		final GameState state = (GameState)father;
		functions.add(new Function<Point, Point>() {
			public Point apply(Point t) {
				return state.getBoard().rightTopPoint(t);
			}
		});
		functions.add(new Function<Point, Point>() {
			public Point apply(Point t) {
				return state.getBoard().rightBottomPoint(t);
			}
		});
		functions.add(new Function<Point, Point>() {
			public Point apply(Point t) {
				return state.getBoard().leftBottomPoint(t);
			}
		});
		functions.add(new Function<Point, Point>() {
			public Point apply(Point t) {
				return state.getBoard().leftTopPoint(t);
			}
		});
		functions.forEach(new Consumer<Function<Point, Point>>() {
			public void accept(Function<Point, Point> t) {
				children.add(createGameState(t, state));
			}
		});
		return children;
	}
	public GameState createGameState(Function<Point,Point> creating, GameState current){
		Point point = creating.apply(current.getMainChecker().getLocation());
		GameState gameState = new GameState();
		if (current.getBoard().isOpened(point.getX(), point.getY())){
			gameState.setBoard(current.getBoard());
			gameState.setMainChecker(new Checker());
			gameState.getMainChecker().setColor(current.getMainChecker().isColor());
			gameState.getMainChecker().setLocation(point);
			return gameState;
		}else{
			if (current
					.getBoard()
					.getCell(point.getX(), point.getY())
					==
					Board.oppositeSide(current
							.getMainChecker()
							.isColor())){
				Point oldpoint = point;
				point = creating.apply(point);
				if (current.getBoard().isOpened(point.getX(), point.getY())){
					try {
						gameState.setBoard((Board) current.getBoard().clone());
						gameState.getBoard().setCell(oldpoint.getX(), oldpoint.getY(), Board.NONE);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					gameState.setMainChecker(new Checker());
					gameState.getMainChecker().setColor(current.getMainChecker().isColor());
					gameState.getMainChecker().setLocation(point);
					return gameState;
				}
			}
		}
		return null;
	}
	public List<ProblemState> getFathers(ProblemState child) {
		throw new UnsupportedOperationException();
	}

	public int getWeight(ProblemState selected) {
		GameState state = (GameState) selected;
		Point goal = goalState.getMainChecker().getLocation();
		Point current = state.getMainChecker().getLocation();
		return Math.abs(goal.getX()-current.getX())+Math.abs(goal.getY()+current.getY());
	}
}
