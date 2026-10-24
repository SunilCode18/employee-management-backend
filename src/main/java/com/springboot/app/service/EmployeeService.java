package com.springboot.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.app.dto.DashboardSummaryDTO;
import com.springboot.app.dto.DepartmentCountDTO;
import com.springboot.app.dto.DepartmentResponseDTO;
import com.springboot.app.dto.EmployeeRequestDTO;
import com.springboot.app.dto.EmployeeResponseDTO;
import com.springboot.app.entity.Department;
import com.springboot.app.entity.Employee;
import com.springboot.app.exception.EmployeeNotFoundException;
import com.springboot.app.repository.DepartmentRepository;
import com.springboot.app.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final DepartmentRepository departmentRepository;
	private EmployeeRepository employeeRepository;
	
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(EmployeeService.class);
	
	public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {	
		this.employeeRepository = employeeRepository;
		this.departmentRepository = departmentRepository;
	}
	
	public List<EmployeeResponseDTO> getAllEmployees() {
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeResponseDTO> empdtos = new ArrayList<EmployeeResponseDTO>();
		
		for(Employee emp : employees) {
			empdtos.add(convertToResponseDTO(emp));
		}
        return empdtos;
    }
	
	
	public EmployeeResponseDTO getEmployee(long id) {	
		Employee emp =  employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee with Id "+id+" Not Found"));
		return convertToResponseDTO(emp);
	}
	
	
	public EmployeeResponseDTO addEmployee(EmployeeRequestDTO empdto) {
		
		logger.info("Adding New Employee : {}", empdto.getName());
		
		Employee emp = new Employee();
		emp.setName(empdto.getName());
		emp.setPhone(empdto.getPhone());
		emp.setDesignation(empdto.getDesignation());
		emp.setSalary(empdto.getSalary());
		emp.setStatus("ACTIVE");
		
		Department department = departmentRepository
	            .findById(empdto.getDepartmentId())
	            .orElseThrow(() ->
	                    new IllegalArgumentException(
	                            "Department not found with id: "
	                                    + empdto.getDepartmentId()));
		emp.setDepartment(department);
//		emp.setDepartmentEntity(department);
		employeeRepository.save(emp);
		EmployeeResponseDTO empresdto = convertToResponseDTO(emp);
		logger.info("Employee Create Successfully with Name {}",emp.getName() );
		return empresdto;
	}
	
	public void deleteEmployee(long id) {
		Employee emp = employeeRepository.findById(id).orElseThrow(()-> new EmployeeNotFoundException("Employee with Id "+id+" Not Found"));
		employeeRepository.delete(emp);
	}
	
	public EmployeeResponseDTO updateEmployee(long id, EmployeeRequestDTO empdto) {
		Employee emp = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee with Id "+id+" Not Found"));
		Department department = departmentRepository.findById(empdto.getDepartmentId()).orElseThrow(() -> new IllegalArgumentException(
		        "Department not found with id: "
		                + empdto.getDepartmentId()));
		emp.setName(empdto.getName());
		emp.setPhone(empdto.getPhone());
		emp.setDepartment(department);
		emp.setDesignation(empdto.getDesignation());
		emp.setSalary(empdto.getSalary());
		return convertToResponseDTO(employeeRepository.save(emp));
	}
	
	private EmployeeResponseDTO convertToResponseDTO(Employee emp) {
		EmployeeResponseDTO empdto = new EmployeeResponseDTO();
		
		empdto.setId(emp.getId());
		empdto.setName(emp.getName());
		empdto.setPhone(emp.getPhone());
//		empdto.setDepartment(emp.getDepartment());
		Department department = emp.getDepartment();
		if(department != null) {
			DepartmentResponseDTO deptDTO = new DepartmentResponseDTO(department.getId(), department.getName());
			empdto.setDepartment(deptDTO);
		}
		
		empdto.setDesignation(emp.getDesignation());
		empdto.setSalary(emp.getSalary());
		empdto.setStatus(emp.getStatus());
		empdto.setCreatedAt(emp.getCreatedAt());
		empdto.setUpdatedAt(emp.getUpdatedAt());
		return empdto;
	}
	
	public List<EmployeeResponseDTO> searchEmployees(String Keyword) {
		List<Employee> employees = employeeRepository.searchEmployees(Keyword);
		
		List<EmployeeResponseDTO> response = new ArrayList<>();
		
		for(Employee emp : employees) {
			response.add(convertToResponseDTO(emp));
		}
		
		return response;
	}
	
	
	public long getTotalEmployees() {
		return employeeRepository.count();
	}
	
	public List<DepartmentCountDTO> getDepartmentsCount(){
		return employeeRepository.countEmployeesByDepartment();
	}
	
	public long getActiveEmployeeCount() {
		return employeeRepository.countBystatus("ACTIVE");
	}
	
	public long getInactiveEmployeeCount() {
		return employeeRepository.countBystatus("INACTIVE");
	}

	public EmployeeResponseDTO updateEmployeeStatus(long id, String status) {
		Employee emp = employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Employee with Id "+id+" Not Found"));
		emp.setStatus(status);
		employeeRepository.save(emp);
		return convertToResponseDTO(emp);
		
	}
	
	public DashboardSummaryDTO getDashboardSummary() {
		return new DashboardSummaryDTO(getTotalEmployees(), getActiveEmployeeCount(), getInactiveEmployeeCount(), getDepartmentsCount());
	}
	
	public List<EmployeeResponseDTO> filterBySearch(String department, String designation, String status){
		if (status != null &&
		        !status.equalsIgnoreCase("ACTIVE") &&
		        !status.equalsIgnoreCase("INACTIVE")) {
		    throw new IllegalArgumentException(
		            "Status must be ACTIVE or INACTIVE");
		}
		
		List<Employee> emps = employeeRepository.searchByFilters(department, designation, status);
		
		List<EmployeeResponseDTO> dtos = new ArrayList<>();
		
		for(Employee e : emps) {
			dtos.add(convertToResponseDTO(e));
		}
		return dtos;
		
	}
	
	
	public Page<EmployeeResponseDTO> getEmployeesWithPagination(int page, int size, String sortBy, String direction){
		Sort sort;
		
		if(direction.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}else {
			sort = Sort.by(sortBy).ascending();
		}
		
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Employee> employeesPage = employeeRepository.findAll(pageable);
		
		return employeesPage.map(this::convertToResponseDTO);
	}
	
	
	
	

}
