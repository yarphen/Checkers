
package com.fishteam.checkers.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fishteam.checkers.interfaces.MapContainer;
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
		System.out.println("PUT /game");
		System.out.println();
		return game;
	}
	@RequestMapping(value="/game/{id}",method = RequestMethod.DELETE)
	public @ResponseBody String removeGame(@PathVariable(value="id") String id){
		mapContainer.getGameMap().remove(id);
		System.out.println("DELETE /game/"+id);
		System.out.println();
		return "";
	}

	@RequestMapping(value="/board/{id}",method = RequestMethod.POST)
	public @ResponseBody String board(@PathVariable(value="id") String id, @RequestBody Action action){
		Game game = mapContainer.getGameMap().get(id);
		GameState state = (GameState)game.getStartState();
		Board board = state.getBoard();
		board.setCell(action.getX(), action.getY(), action.getCheckerState());
		System.out.println("POST /board/"+id);
		System.out.println("Action: "+action);
		System.out.println();
		return "";
	}
	@RequestMapping(value="/target/{id}",method = RequestMethod.POST)
	public @ResponseBody String target(@PathVariable(value="id") String id, @RequestBody Action action){
		Game game = mapContainer.getGameMap().get(id);
		GameState state = (GameState)game.getGoalState();
		GameState startState = (GameState)game.getStartState();
		Checker checker = state.getMainChecker();
		checker.setColor(startState.getMainChecker().isColor());
		checker.setLocation(new Point(action.getX(), action.getY()));
		System.out.println("POST /target/"+id);
		System.out.println("Action: "+action);
		System.out.println();
		return "";
	}

	@RequestMapping(value="/active/{id}",method = RequestMethod.POST)
	public @ResponseBody String active(@PathVariable(value="id") String id, @RequestBody Action action){
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
		System.out.println("POST /active/"+id);
		System.out.println("Action: "+action);
		System.out.println();
		return "";
	}

	@RequestMapping(value="/solve/{id}",method = RequestMethod.POST)
	public @ResponseBody Way solveGame(@PathVariable(value="id") String id, @RequestBody Action action){
		Way w = mapContainer.getGameMap().get(id)
				.solveWith(mapContainer.getSolverMap().get(action.getAlgorithm()));

		System.out.println("POST /solve/"+id);
		System.out.println("Action: "+action);
		System.out.println();
		return w;
	}
}
