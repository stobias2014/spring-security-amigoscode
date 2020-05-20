package com.tobias.saul.springsecuritydemo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1L, "Saul Tobias"),
			new Student(2L, "Brenda Tobias"),
			new Student(3L, "Kareli tobias")
		);
	
	@GetMapping
	//annotation based
	//hasRole('ROLE_') hasAnyRole('ROLE_') hasAuthority(''permission) hasAnyAuthority('permission')
	@PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_ADMIN_TRAINEE')")
	public List<Student> getAllStudents() {
		System.out.println("getAllStudents requested");
		return STUDENTS;
	}
	
	@PostMapping
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println("registeredNewStudent requested");
		System.out.println("Student: " + student);
	}
	
	@DeleteMapping("/{studentId}")
	public void deleteStudent(@PathVariable("studentId") Long studentId) {
		System.out.println("deleteStudent requested");
		System.out.println("Student ID: " + studentId);
	}
	
	@PutMapping("{studentId}")
	public void updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
		System.out.println("updateStudent requested");
		System.out.println(String.format("Student ID: %s Student: %s", studentId, student));
	}

}
