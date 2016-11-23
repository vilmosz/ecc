package uk.ac.london.assignment.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "exercise")
@JsonSubTypes({ @JsonSubTypes.Type(value = ModkAdd.class, name = "modk-add") })
public abstract class Exercise {

}