package com.springboot.app.entity;

import jakarta.persistence.Entity;

@Entity(name = "leave_type")
public class LeaveType {
	
	private Long id;
	private String name;
	private long maxDays;
	private boolean paid;
	
	
	public LeaveType() {}
	
	
	public LeaveType(Long id, String name, long maxDays, boolean paid) {
		super();
		this.id = id;
		this.name = name;
		this.maxDays = maxDays;
		this.paid = paid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnme() {
		return name;
	}
	public void setAnme(String name) {
		this.name = name;
	}
	public long getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(long maxDays) {
		this.maxDays = maxDays;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	
	

}
