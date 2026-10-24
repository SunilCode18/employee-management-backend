package com.springboot.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.app.dto.DepartmentCountDTO;
import com.springboot.app.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	
//	public List<Employee> findByNameContainingIgnoreCaseOrDesignationContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String designation, String department);
	@Query("""
	        SELECT e FROM Employee e
	        WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
	           OR LOWER(e.designation) LIKE LOWER(CONCAT('%', :keyword, '%'))
	           OR LOWER(e.department.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        """)
	List<Employee> searchEmployees(@Param("keyword") String keyword);
	
	public long count();
	
	@Query("""
	        SELECT COUNT(e)
	        FROM Employee e
	        WHERE e.department.name = :department
	        """)
	    long countByDepartment(@Param("department") String department);
	
	 
	
	@Query("""
	        SELECT new com.springboot.app.dto.DepartmentCountDTO(
	            e.department.name, COUNT(e))
	        FROM Employee e
	        GROUP BY e.department.name
	        """)
	List<DepartmentCountDTO> countEmployeesByDepartment();
	
	long countBystatus(String status);
	
	List<Employee> findByStatus(String status);
	
	@Query("""
		    SELECT e FROM Employee e
		    WHERE (:department IS NULL OR e.department.name = :department)
		      AND (:designation IS NULL OR e.designation = :designation)
		      AND (:status IS NULL OR e.status = :status)
		    """)
	List<Employee> searchByFilters(
		        @Param("department") String department,
		        @Param("designation") String designation,
		        @Param("status") String status
		);
}
