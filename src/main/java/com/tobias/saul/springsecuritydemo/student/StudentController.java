package com.tobias.saul.springsecuritydemo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
				new Student(1L, "Saul Tobias"),
				new Student(2L, "Brenda Tobias"),
				new Student(3L, "Kareli tobias")
			);
	
	@GetMapping("/{studentId}")
	public Student getStudent(@PathVariable("studentId") Long studentId) {
		
		return STUDENTS.stream()
				.filter(student -> student.getStudentId().equals(studentId))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Student [" + studentId + "] does not exist"));
		
	}

}
