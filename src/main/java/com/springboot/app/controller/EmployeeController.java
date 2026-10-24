package com.springboot.app.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.dto.EmployeeRequestDTO;
import com.springboot.app.dto.EmployeeResponseDTO;
import com.springboot.app.entity.Employee;
import com.springboot.app.repository.EmployeeRepository;
import com.springboot.app.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(
	    name = "Employee Management",
	    description = "APIs for managing employees"
	)
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
	
	private EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository) {
		this.employeeService = employeeService;
		this.employeeRepository = employeeRepository;
	}
	
	
	@Operation(
		    summary = "Get all employees",
		    description = "Returns a list of all employees"
		)
	@GetMapping("/getAllEmployees")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(){
		List<EmployeeResponseDTO> employees =  employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}
	
	
	@Operation(
		    summary = "Get employee by ID",
		    description = "Returns an employee based on the given employee ID"
		)
	@GetMapping("/getEmployee/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable long id) {
		EmployeeResponseDTO employee = employeeService.getEmployee(id);
		return ResponseEntity.ok(employee);
	}
	
	
	@Operation(
		    summary = "Add a new employee",
		    description = "Creates a new employee in the database"
		)
	@PostMapping("/addEmployee")
	public ResponseEntity<EmployeeResponseDTO> addEmployee(@Valid @RequestBody EmployeeRequestDTO empdto) {
		EmployeeResponseDTO employee = employeeService.addEmployee(empdto);
		
//		return new ResponseEntity<>(employee, HttpStatus.CREATED);
		return ResponseEntity.status(HttpStatus.CREATED).body(employee);
	}
	
	
	@Operation(
		    summary = "Delete an employee",
		    description = "Deletes an employee using the employee ID"
		)
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@Operation(
		    summary = "Update an employee",
		    description = "Updates an existing employee using the employee ID"
		)
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@PathVariable long id,@Valid @RequestBody EmployeeRequestDTO emp){
		EmployeeResponseDTO employee = employeeService.updateEmployee(id, emp);
		return ResponseEntity.ok(employee);
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<EmployeeResponseDTO>> searchEmployee(@RequestParam String keyword) {
		List<EmployeeResponseDTO> employees = employeeService.searchEmployees(keyword);
		return ResponseEntity.ok().body(employees);
	}
	
	@GetMapping("/search/filter")
	public List<EmployeeResponseDTO> filterEmployees(@RequestParam(required = false) String department, @RequestParam(required=false) String designation, @RequestParam(required = false) String status){
		return employeeService.filterBySearch(department, designation, status);
		
	}
	
	@GetMapping("/admin/employees/page")
	public ResponseEntity<Page<EmployeeResponseDTO>> getEmployeesWithPagination(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction
			){
		
		Page<EmployeeResponseDTO> emplPage = employeeService.getEmployeesWithPagination(page, size, sortBy, direction);
		return ResponseEntity.ok(emplPage);
	}
	

}
