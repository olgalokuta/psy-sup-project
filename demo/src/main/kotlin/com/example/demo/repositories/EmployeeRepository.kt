package com.example.demo.repositories

import org.springframework.data.repository.CrudRepository
import com.example.demo.models.Employee


interface EmployeeRepository : CrudRepository<Employee, Int>  {
    fun findByUsername(uname : String) : List<Employee>
    fun findByEmail(email: String): Employee?
}