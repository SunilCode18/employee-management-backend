package com.springboot.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.app.entity.Department;
import com.springboot.app.entity.Employee;
import com.springboot.app.repository.DepartmentRepository;
import com.springboot.app.repository.EmployeeRepository;

@Service
public class DepartmentService {
	
	private DepartmentRepository departmentRepository;
	private EmployeeRepository employeeRepository;
	
	public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
		this.departmentRepository = departmentRepository;
		this.employeeRepository = employeeRepository;
	}
	
	public Department addDepartment(String name) {
		Department dept = new Department(name);
		return departmentRepository.save(dept);
	}
	
	public List<Department> getAllDepartments(){
		return departmentRepository.findAll();
	}

	public void deleteDepartment(long id) {
		
		Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
		        "Department not found with id: "+id));
		
		Boolean hasEmployees = employeeRepository.existsByDepartmentId(id);
		if(hasEmployees) {
			throw new IllegalArgumentException("Cannot delete department because employees are assigned to it");
		}
		departmentRepository.delete(department);
		
	}

	public Department updateDepartment(long id, String dept) {
		Department department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(
		        "Department not found with id: "+id));
		department.setName(dept);
		return departmentRepository.save(department);
	}

}
