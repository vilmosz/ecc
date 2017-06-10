package uk.ac.london.assignment.service;

import org.springframework.context.ApplicationEvent;

public class AssessmentEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6136414682581383052L;
	
	private String prefix;
	private String error;
	
	public AssessmentEvent(Object source) {
		super(source);
	}
	
	public AssessmentEvent(Object source, String prefix, String error) {
		super(source);
		this.prefix = prefix;
		this.error = error;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	

}
