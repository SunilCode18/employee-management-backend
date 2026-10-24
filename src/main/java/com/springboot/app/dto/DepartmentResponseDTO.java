package com.springboot.app.dto;

public class DepartmentResponseDTO {
	
	private Long id;
	private String department;
	
	
	public DepartmentResponseDTO() {}
	
	public DepartmentResponseDTO(Long id, String department) {
		super();
		this.id = id;
		this.department = department;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	

}
