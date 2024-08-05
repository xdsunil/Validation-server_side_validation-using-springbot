package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Student;
import com.example.service.StudentService;

import jakarta.validation.Valid;

@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	StudentService service;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/register")
	private String register(Model model) {
		model.addAttribute("student", new Student());
		return "register";
	}

	@PostMapping("/register")
	private String register(@Valid @ModelAttribute Student student, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "register";

		}
		if (!student.getAgreement()) {
			result.rejectValue("agreement", "error.student", "You must agree to the terms and conditions");
			return "register";
		}
		Student existingStudent = service.findByEmail(student.getEmail());
		if (existingStudent != null) {
			model.addAttribute("message", "student already exist");
			return "register";
		}
		service.register(student);
		model.addAttribute("message", "student register successful..!");
		return "login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("student", new Student());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Student student, Model model) {

		Student existingStudent = service.findByEmail(student.getEmail());
		if (existingStudent == null || !existingStudent.getPassword().equals(student.getPassword())) {
			model.addAttribute("message", "Invalid email or password");
			return "login";
		}
		model.addAttribute("name", "welcome " + student.getEmail());
		model.addAttribute("message", "succefull login..!");
		return "success";
	}

}
