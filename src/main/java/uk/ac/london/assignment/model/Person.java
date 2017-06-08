package uk.ac.london.assignment.model;

import java.awt.Point;

public class Person {
	
	private Integer d;
	private Point q;
	
	public Person() {
		// empty constructor
	}
	
	public Person(Integer d) {
		this.d = d;
	}

	public Integer getD() {
		return d;
	}

	public Point getQ() {
		return q;
	}

	public void setD(Integer d) {
		this.d = d;
	}

	public void setQ(Point q) {
		this.q = q;
	}		

}
