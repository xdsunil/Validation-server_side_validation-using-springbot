package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	StudentRepository repository;

	public Student findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public void register(Student student) {
		repository.save(student);

	}

}
