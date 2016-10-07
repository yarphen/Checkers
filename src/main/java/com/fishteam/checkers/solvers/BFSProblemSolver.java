package com.fishteam.checkers.solvers;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.interfaces.Problem;
import com.fishteam.checkers.interfaces.ProblemState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class BFSProblemSolver implements ProblemSolver{
	public List<ProblemState> solve(final Problem problem){
		Set<ProblemState> visited = new HashSet<ProblemState>();
		Node root = new Node(null);
		root.state = problem.getStartState();
		Node goalNode =  findFromHead(root, new Function<ProblemState, List<ProblemState>>() {
			public List<ProblemState> apply(ProblemState t) {
				return problem.getChildren(t);
			}
		}, visited, problem.getGoalState());
		return asList(goalNode);
	}
	private List<ProblemState> asList(Node node) {
		LinkedList<ProblemState> list = new LinkedList<ProblemState>();
		while(node!=null){
			list.addLast(node.state);
			node=node.father;
		}
		return list;
	}
	private class Node{
		private ProblemState state;
		private Node father;
		private Set<Node> children = new HashSet<Node>();
		public Node(Node father) {
			this.father = father;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((state == null) ? 0 : state.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (state == null) {
				if (other.state != null)
					return false;
			} else if (!state.equals(other.state))
				return false;
			return true;
		}
		private BFSProblemSolver getOuterType() {
			return BFSProblemSolver.this;
		}

	}
	private Node findFromHead(Node head, Function<ProblemState, List<ProblemState>> childFunction, Set<ProblemState> visited, ProblemState goal){
		Set<Node> current = new HashSet<Node>();
		Set<Node> children = new HashSet<Node>();
		current.add(head);
		return findInChildren(childFunction, current, children, visited, goal);
	}
	private Node findInChildren(Function<ProblemState, List<ProblemState>> childFunction, Set<Node> current, Set<Node> children, Set<ProblemState> visited, ProblemState goal){
		children.clear();
		if (current.isEmpty())return null;
		for(Node node:current){
			List<ProblemState> states = childFunction.apply(node.state);
			for(ProblemState state:states){
				if (!visited.contains(state)){
					Node newNode = new Node(node);
					newNode.state = state;
					node.children.add(newNode);
					if (state.equals(goal)){
						return newNode;
					}else{
						visited.add(state);
						System.out.println(state);
						children.add(newNode);
					}
				}
			}
		}
		return findInChildren(childFunction, children, current, visited, goal);
	}
}
