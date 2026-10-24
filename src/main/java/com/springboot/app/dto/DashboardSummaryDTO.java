package com.springboot.app.dto;

import java.util.List;

public class DashboardSummaryDTO {
	
	private long totalEmployees;
	private long activeEmployees;
	private long inactiveEmployees;
	private List<DepartmentCountDTO> departmentCounts;
	
	
	public DashboardSummaryDTO(long totalEmployees, long activeEmployees, long inactiveEmployees,
			List<DepartmentCountDTO> departmentCounts) {
		super();
		this.totalEmployees = totalEmployees;
		this.activeEmployees = activeEmployees;
		this.inactiveEmployees = inactiveEmployees;
		this.departmentCounts = departmentCounts;
	}
	
	
	public long getTotalEmployees() {
		return totalEmployees;
	}

	public long getActiveEmployees() {
		return activeEmployees;
	}

	public long getInactiveEmployees() {
		return inactiveEmployees;
	}

	public List<DepartmentCountDTO> getDepartmentCounts() {
		return departmentCounts;
	}

	
	

}
