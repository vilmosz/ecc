package uk.ac.london.assignment.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Assessment")
public class Assessment extends Student {

    public Assessment() {
        super();
    }

    public Assessment(final Student student) {
        super(student);
    }

}
