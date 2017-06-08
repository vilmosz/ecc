package uk.ac.london.assignment.model;

import java.awt.Point;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Exercise {
	
	public enum Type { 
		MODK_ADD("modk-add"), MODK_MUL("modk-mul"), KEY_EXCHANGE("key_exchange");
		private String value;
	
		Type(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}				
	}

	private Point p, q, r, s;
	private Integer n;
	private Type type;
	
	public Exercise() {
		// empty constructor
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

	@JsonIgnore
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Point getS() {
		return s;
	}

	public void setS(Point s) {
		this.s = s;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}		
	
}
