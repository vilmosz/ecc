package uk.ac.london.assignment.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assessment")
public class Assessment {

	private @Id String id;

	private String name;
	
	private Student expected;
	private Student actual;
	
    public Assessment() {
    	// empty constructor
    }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Student getExpected() {
		return expected;
	}

	public Student getActual() {
		return actual;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setExpected(Student expected) {
		this.expected = expected;
	}

	public void setActual(Student actual) {
		this.actual = actual;
	}

}
