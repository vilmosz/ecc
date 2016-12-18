package uk.ac.london.assignment.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import uk.ac.london.ecc.Ecc;

@Document(collection = "Student")
public class Student {

	private @Id String id;

	private String name;
	private Ecc ecc;
	private Map<String, Exercise> assignment;

	public Student() {}

	public void addAssignment(Exercise exercise) {
		if (assignment == null)
			assignment = new HashMap<>();
		assignment.put(exercise.getExercise(), exercise);		
	}
	
	public String getId() {
		return id;
	}
	
	public String getSrn() {
		return getId();
	}

	public Ecc getEcc() {
		return ecc;
	}

	public void setEcc(Ecc ecc) {
		this.ecc = ecc;
	}

	public Map<String, Exercise> getAssignment() {
		return assignment;
	}

	public void setAssignment(Map<String, Exercise> assignment) {
		this.assignment = assignment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", ecc=" + ecc + "]";
	}

}
