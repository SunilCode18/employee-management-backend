package com.springboot.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;

@Entity(name = "employee_leave_balance")
public class EmployeeLeaveBalance {
	
	private long id;
	
	@JoinTable(name = "employee_id")
	private Employee employee_id;

}
