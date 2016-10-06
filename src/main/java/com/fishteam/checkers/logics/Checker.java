package com.fishteam.checkers.logics;

import com.fishteam.checkers.interfaces.ProblemState;

public class Checker{
	
	private Point location;
	/**
	 * FALSE IS WHITE
	 * TRUE IS BLACK
	 * STOP RACISM!
	 */
	private boolean color;
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
	}
	@Override
	public int hashCode() {
		final int prime = 97;
		int result = 1;
		result = prime * result + (color ? 997 : 113);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		Checker other = (Checker) obj;
		if (color != other.color)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	
}
