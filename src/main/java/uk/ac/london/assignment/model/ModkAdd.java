package uk.ac.london.assignment.model;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("modk-add")
public class ModkAdd extends Exercise {

	private Point p, q, r;
	
	public ModkAdd() {
		super();
	}

	public Point getP() {
		return p;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public Point getQ() {
		return q;
	}

	public void setQ(Point q) {
		this.q = q;
	}

	public Point getR() {
		return r;
	}

	public void setR(Point r) {
		this.r = r;
	}

	@Override
	public String getExercise() {
		return "modk-add";
	}

}
