package com.springboot.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.entity.Department;
import com.springboot.app.entity.Employee;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	
	public Optional<Employee> findByName(String name);

}
