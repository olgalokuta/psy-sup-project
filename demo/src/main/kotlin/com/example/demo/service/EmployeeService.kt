package com.example.demo.service

import com.example.demo.models.Employee
import com.example.demo.repositories.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(
  private val employeeRepository: EmployeeRepository
) {
  fun createEmployee(employee: Employee): Employee? {
    val found = employeeRepository.findByEmail(employee.email)
    return if (found == null) {
      employeeRepository.save(employee)
      employee
    } else null
  }
  fun findById(uid: Int): Employee? =
    employeeRepository.findById(uid).orElse(null)
  fun findAll(): List<Employee> =
    employeeRepository.findAll().toList()
  fun findByUsername(username: String): Employee? {
    val found = employeeRepository.findByUsername(username).toList()
    return if (found != null) {
        found[0]
    } else null
  }
  fun updateById(employeeId: Int, employee: Employee) : Employee? {
    val existingEmployee = employeeRepository.findById(employeeId).orElse(null)
        if (existingEmployee == null) {
            return null
        }
        val updatedEmployee = existingEmployee.copy(username = employee.username, 
            email = employee.email, phone = employee.phone, password = employee.password,
            name = employee.name, role = employee.role)
        employeeRepository.save(updatedEmployee)
        return updatedEmployee
  }
  fun deleteById(uid: Int): Boolean {
    if (!employeeRepository.existsById(uid)) {
      return false
    }
    employeeRepository.deleteById(uid)
    return true
  }
}