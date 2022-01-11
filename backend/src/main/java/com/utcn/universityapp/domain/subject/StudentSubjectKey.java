package com.utcn.universityapp.domain.subject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class StudentSubjectKey implements Serializable {

    private static final long serialVersionUID = 6212007342698449914L;

    @Column(name = "student_id")
    private long studentId;

    @Column(name = "subject_id")
    private long subjectId;

}