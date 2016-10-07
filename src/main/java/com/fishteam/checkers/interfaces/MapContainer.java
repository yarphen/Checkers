package com.fishteam.checkers.interfaces;

import java.util.Map;

import com.fishteam.checkers.logics.Game;

public interface MapContainer {
	Map<String, Game> getGameMap();
	Map<String, ProblemSolver> getSolverMap();
}
