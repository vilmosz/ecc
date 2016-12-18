package uk.ac.london.assignment.model;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("modk-mul")
public class ModkMul extends Exercise {

	private Point p, q;
	private Integer n;
	
	public ModkMul() {
		super();
	}

	public Point getP() {
		return p;
	}
	
	@JsonIgnore
	public Point getQ() {
		return q;
	}
 
	public Integer getN() {
		return n;
	}

	public void setP(Point p) {
		this.p = p;
	}

	public void setQ(Point q) {
		this.q = q;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	@Override
	public String getExercise() {
		return "modk-mul";
	}
	
}