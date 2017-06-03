package uk.ac.london.assignment.service;

import org.springframework.context.ApplicationEvent;

public class StudentEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6136414682581383052L;
	
	public enum Type { ACTUAL, EXPECTED }

	private Type type;
	private String error;
	
	public StudentEvent(Object source) {
		super(source);
	}
	
	public StudentEvent(Object source, String error, Type type) {
		super(source);
		this.type = type;
		this.error = error;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	

}
