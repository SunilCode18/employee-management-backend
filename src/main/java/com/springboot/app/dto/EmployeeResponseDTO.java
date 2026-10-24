package com.springboot.app.dto;

import java.time.LocalDateTime;


public class EmployeeResponseDTO {
	
	private long id;
	private String name;
	private String phone;
	private DepartmentResponseDTO department;
	private String designation;
	private double salary;
	private String status;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public EmployeeResponseDTO() {
	}
	
	

	public EmployeeResponseDTO(long id, String name, String phone, DepartmentResponseDTO department, String designation, double salary, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.department = department;
		this.designation = designation;
		this.salary = salary;
		this.createdAt = createdAt;
        this.updatedAt = updatedAt;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public DepartmentResponseDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentResponseDTO department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public LocalDateTime getCreatedAt() {
	    return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
	    this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
	    return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
	    this.updatedAt = updatedAt;
	}
	
	

}
