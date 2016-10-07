package com.fishteam.checkers.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.logics.Action;
import com.fishteam.checkers.logics.Game;
import com.fishteam.checkers.logics.Way;


/**
 * This is controller to interact with js on page
 */
@Controller
public class ActionController {

//	@Autowired
//	private GameMap queueMap;
	@Autowired
	private ProblemSolver bfs_solver;
	@Autowired
	private ProblemSolver dfs_solver;
	
	
	@RequestMapping(value="/board",method = RequestMethod.POST)
	public void board(int id){
		
	}
	
	@RequestMapping(value="/active",method = RequestMethod.POST)
	public void active(int id){
		
	}
	
	@RequestMapping(value="/solve",method = RequestMethod.POST)
	public void solve(int id){
		
	}

	@Autowired
//	@Qualifier("gameMap")
	private Map<String, Game> gameMap;
	@Autowired
//	@Qualifier("solverMap")
	private Map<String, ProblemSolver> solverMap;
	@RequestMapping(value="/game",method = RequestMethod.PUT)
	public @ResponseBody Game newGame(){
		Game game = new Game();
		gameMap.put(game.getId(), game);
		return game;
	}
	@RequestMapping(value="/game",method = RequestMethod.POST)
	public Way solveGame(Action action){
		return gameMap.get(action.getId()).solveWith(solverMap.get(action.getAlgorythm()));
	}
}
