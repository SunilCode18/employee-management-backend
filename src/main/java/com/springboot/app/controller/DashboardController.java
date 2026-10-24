package com.springboot.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.dto.DashboardSummaryDTO;
import com.springboot.app.dto.DepartmentCountDTO;
import com.springboot.app.dto.EmployeeResponseDTO;
import com.springboot.app.service.EmployeeService;

@RestController
public class DashboardController {
	
	private EmployeeService employeeService;
	
	public DashboardController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/admin/dashboard/totalEmployees")
	public long getTotalEmployees() {
		return employeeService.getTotalEmployees();
	}
	
	@GetMapping("/admin/dashboard/departmentCount")
	public List<DepartmentCountDTO> getDepartmentcount(){
		return employeeService.getDepartmentsCount();
	}
	
	@GetMapping("/admin/dashboard/activeEmployees")
	public long getActiveEmployeeCount() {
		return employeeService.getActiveEmployeeCount();
	}
	
	@GetMapping("/admin/dashboard/inactiveEmployees")
	public long getInactiveEmployeeCount() {
		return employeeService.getInactiveEmployeeCount();
	}
	
	@PutMapping("/admin/dashboard/setEmployeeStatus/{id}")
	public EmployeeResponseDTO setEmployeeStatus(@PathVariable long id,@RequestParam String status) {
		return employeeService.updateEmployeeStatus(id, status);
		
	}
	
	@GetMapping("/admin/dashboard/summary")
	public DashboardSummaryDTO getDashboard() {
		return employeeService.getDashboardSummary();
		
	}

}
