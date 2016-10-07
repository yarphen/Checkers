package com.fishteam.checkers.interfaces;

import java.util.List;

public interface ProblemSolver {
	List<ProblemState> solve(Problem problem);
}
