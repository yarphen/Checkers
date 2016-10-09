package com.fishteam.checkers.solvers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fishteam.checkers.interfaces.Problem;
import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.interfaces.ProblemState;


public class DFSProblemSolverV2 implements ProblemSolver{

	@Override
	public List<ProblemState> solve(Problem problem) {
		List<ProblemState> path = new LinkedList<ProblemState>();
		Set<ProblemState> visited = new HashSet<ProblemState>();
		LinkedList<Node> listAsStack = new LinkedList<Node>();
		Node head = new Node(problem.getStartState(), null);
		listAsStack.add(head);
		visited.add(head.element);
		int expanded = 0;
		int known = 0;
		outer:while(!listAsStack.isEmpty()){
			Node current = listAsStack.pollLast();
			List<ProblemState> children = problem.getChildren(current.element);
			for(ProblemState state: children){
				if (!visited.contains(state)){
					if (state.simpleEquals(problem.getGoalState())){
						path.add(state);
						path.add(current.element);
						while(current.father != null){
							current = current.father;
							path.add(current.element);
						}
						break outer;
					}else{
						known++;
						Node node = new Node(state, current);
						visited.add(state);
						listAsStack.addLast(node);
					}
				}
			}
			expanded++;
		}
		System.err.println("DFSv2: expanded: "+expanded+"; known: "+known+".");
		return path;
	}

	private class Node{
		private final ProblemState element;
		private final Node father;
		public Node(ProblemState element, Node father) {
			super();
			this.element = element;
			this.father = father;
		}
	}
}
