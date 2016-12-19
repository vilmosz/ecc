package uk.ac.london.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "exercise")
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = ModkAdd.class, name = "modk-add"),
	@JsonSubTypes.Type(value = ModkMul.class, name = "modk-mul") 
})
public abstract class Exercise {

	@JsonIgnore
	public abstract String getExercise();
	
}
