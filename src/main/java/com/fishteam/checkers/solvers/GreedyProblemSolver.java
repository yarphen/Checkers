package com.fishteam.checkers.solvers;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import com.fishteam.checkers.interfaces.Problem;
import com.fishteam.checkers.interfaces.ProblemSolver;
import com.fishteam.checkers.interfaces.ProblemState;


public class GreedyProblemSolver implements ProblemSolver{

	@Override
	public List<ProblemState> solve(Problem problem) {
		List<ProblemState> path = new LinkedList<ProblemState>();
		Set<ProblemState> visited = new HashSet<ProblemState>();
		PriorityQueue<Node> queue = new PriorityQueue<Node>(new NodeComparator());
		Node head = new Node(problem.getStartState(), null, problem.getWeight(problem.getStartState()));
		queue.add(head);
		visited.add(head.element);
		int expanded = 0;
		int known = 0;
		outer:while(!queue.isEmpty()){
			Node current = queue.poll();
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
						Node node = new Node(state, current,  problem.getWeight(state));
						visited.add(state);
						queue.add(node);
					}
				}
			}
			expanded++;
		}
		System.err.println("Greedy: expanded: "+expanded+"; known: "+known+".");
		return path;
	}
	private class Node{
		private final ProblemState element;
		private Node father;
		private final int weight;
		public Node(ProblemState element, Node father, int weight) {
			super();
			this.element = element;
			this.father = father;
			this.weight = weight;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((element == null) ? 0 : element.hashCode());
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
			if (element == null) {
				if (other.element != null)
					return false;
			} else if (!element.equals(other.element))
				return false;
			return true;
		}
		private GreedyProblemSolver getOuterType() {
			return GreedyProblemSolver.this;
		}
		
	}
	private class NodeComparator implements Comparator<Node>{

		@Override
		public int compare(Node arg0, Node arg1) {
			return Integer.compare(arg0.weight, arg1.weight);
		}
		
	}
}
