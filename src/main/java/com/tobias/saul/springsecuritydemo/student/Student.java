package com.tobias.saul.springsecuritydemo.student;

public class Student {
	
	private final Long studentId;
	private final String name;
	
	public Student(Long studentId, String name) {
		super();
		this.studentId = studentId;
		this.name = name;
	}

	public Long getStudentId() {
		return studentId;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + "]";
	}

	
	

}
