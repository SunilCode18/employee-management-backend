package com.springboot.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.entity.Department;
import com.springboot.app.repository.DepartmentRepository;
import com.springboot.app.service.DepartmentService;

@RestController
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
	
	private DepartmentService departmentService;
	
	public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
		this.departmentService = departmentService;
		this.departmentRepository = departmentRepository;
	}
	
	@GetMapping("/admin/departments")
	public ResponseEntity<List<Department>> getAllDepartments(){
		return ResponseEntity.ok(departmentService.getAllDepartments());
	}
	
	@PostMapping("/admin/departments")
	public ResponseEntity<Department> addDepartment(@RequestParam String dept) {
		Department department = departmentService.addDepartment(dept);
		return ResponseEntity.ok(department);
		
	}
	
	@DeleteMapping("/admin/departments/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable long id) {
		departmentService.deleteDepartment(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/admin/departments/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable long id, @RequestParam String dept) {
		Department department = departmentService.updateDepartment(id, dept);
		return ResponseEntity.ok(department);
	}

}
