package com.fishteam.checkers.logics;

import java.util.Map;

import com.fishteam.checkers.interfaces.MapContainer;
import com.fishteam.checkers.interfaces.ProblemSolver;

public class MapContainerImpl implements MapContainer{
	private Map<String, Game> gameMap;
	private Map<String, ProblemSolver> solverMap;
	public Map<String, Game> getGameMap() {
		return gameMap;
	}
	public void setGameMap(Map<String, Game> gameMap) {
		this.gameMap = gameMap;
	}
	public Map<String, ProblemSolver> getSolverMap() {
		return solverMap;
	}
	public void setSolverMap(Map<String, ProblemSolver> solverMap) {
		this.solverMap = solverMap;
	}
}
