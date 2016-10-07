package com.fishteam.checkers.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fishteam.checkers.interfaces.MapContainer;
import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.logics.Action;
import com.fishteam.checkers.logics.Board;
import com.fishteam.checkers.logics.Checker;
import com.fishteam.checkers.logics.Game;
import com.fishteam.checkers.logics.GameState;
import com.fishteam.checkers.logics.Point;
import com.fishteam.checkers.logics.Way;


/**
 * This is controller to interact with js on page
 */
@Controller
public class ActionController {

	@Autowired
	private MapContainer mapContainer;

	@RequestMapping(value="/game",method = RequestMethod.PUT)
	public @ResponseBody Game newGame(){
		Game game = new Game();
		mapContainer.getGameMap().put(game.getId(), game);
		return game;
	}
	@RequestMapping(value="/game/{id}",method = RequestMethod.DELETE)
	public void removeGame(@RequestParam(value="id") String id){
		mapContainer.getGameMap().remove(id);
	}

	@RequestMapping(value="/board/{id}",method = RequestMethod.POST)
	public void board(@RequestParam(value="id") String id, @RequestBody Action action){
		Game game = mapContainer.getGameMap().get(id);
		GameState state = (GameState)game.getStartState();
		Board board = state.getBoard();
		board.setCell(action.getX(), action.getY(), action.getCheckerState());
	}
	@RequestMapping(value="/target/{id}",method = RequestMethod.POST)
	public void target(@RequestParam(value="id") String id, @RequestBody Action action){
		Game game = mapContainer.getGameMap().get(id);
		GameState state = (GameState)game.getGoalState();
		GameState startState = (GameState)game.getStartState();
		Checker checker = state.getMainChecker();
		checker.setColor(startState.getMainChecker().isColor());
		checker.setLocation(new Point(action.getX(), action.getY()));
	}

	@RequestMapping(value="/active/{id}",method = RequestMethod.POST)
	public void active(@RequestParam(value="id") String id, @RequestBody Action action){
		Game game = mapContainer.getGameMap().get(id);
		GameState state = (GameState)game.getStartState();
		Checker checker = state.getMainChecker();
		checker.setLocation(new Point(action.getX(), action.getY()));
		int colorCode = action.getCheckerState();
		switch (colorCode) {
		case Board.BLACK:
			checker.setColor(true);
			break;
		case Board.WHITE:
			checker.setColor(false);
			break;
		}
		state.setMainChecker(checker);
	}

	@RequestMapping(value="/solve/{id}",method = RequestMethod.POST)
	public Way solveGame(@RequestParam(value="id") String id, @RequestBody Action action){
		return mapContainer.getGameMap().get(id)
				.solveWith(mapContainer.getSolverMap().get(action.getAlgorythm()));
	}
}
