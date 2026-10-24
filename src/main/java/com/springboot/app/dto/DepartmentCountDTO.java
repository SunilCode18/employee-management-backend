package com.springboot.app.dto;

public class DepartmentCountDTO {
	
	private String departmentName;
	private long employeeCount;
	
	
	public DepartmentCountDTO(String departmentName, long employeeCount) {
		super();
		this.departmentName = departmentName;
		this.employeeCount = employeeCount;
	}
	
	public DepartmentCountDTO() {}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public long getEmployeeCount() {
		return employeeCount;
	}
	public void setEmployeeCount(long employeeCount) {
		this.employeeCount = employeeCount;
	}
	
	
	

}
