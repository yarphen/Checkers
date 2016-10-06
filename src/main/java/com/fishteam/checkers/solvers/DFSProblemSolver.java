package com.fishteam.checkers.solvers;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.fishteam.checkers.interfaces.*;

import java.util.HashSet;
import java.util.LinkedList;

public class DFSProblemSolver implements ProblemSolver{
	public List<ProblemState> solve(final Problem problem){
		Set<ProblemState> visited = new HashSet<ProblemState>();
		return findFromHead(problem.getStartState(), new Function<ProblemState, List<ProblemState>>() {
			public List<ProblemState> apply(ProblemState t) {
				return problem.getChildren(t);
			}
		}, visited, problem.getGoalState());
	}
	private List<ProblemState> findFromHead(ProblemState current, Function<ProblemState, List<ProblemState>> childFunction, Set<ProblemState> visited, ProblemState goal){
		if (current.equals(goal)){
			LinkedList<ProblemState> result = new LinkedList<ProblemState>();
			result.add(current);
			return result;
		}
		List<ProblemState> children = childFunction.apply(current);
		for(ProblemState state: children){
			if (!visited.contains(state)){
				visited.add(state);
				List<ProblemState> way = findFromHead(state, childFunction, visited, goal);
				if (way!=null){
					way.add(current);
					return way;
				}
			}
		}
		return null;
	}
}
