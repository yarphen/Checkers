package com.fishteam.checkers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.pacman.json.GameInfo;



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
	
	@RequestMapping(value="/game",method = RequestMethod.PUT)
	public void createGame(int id){
		
	}
	
	@RequestMapping(value="/board",method = RequestMethod.POST)
	public void board(int id){
		
	}
	
	@RequestMapping(value="/active",method = RequestMethod.POST)
	public void active(int id){
		
	}
	
	@RequestMapping(value="/solve",method = RequestMethod.POST)
	public void solve(int id){
		
	}

}
