package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.demo.models.Employee
import com.example.demo.service.EmployeeService

@RestController
@CrossOrigin(origins=["http://localhost:3000", "http://62.3.58.13"])
@RequestMapping("/api/employees")
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping("")
    fun getAllEmployees(): ResponseEntity<List<Employee>> =
        ResponseEntity(employeeService.findAll(), HttpStatus.OK)

    @PostMapping("")
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        val createdEmployee = employeeService.createEmployee(employee)
        return if (createdEmployee != null) ResponseEntity(createdEmployee, HttpStatus.CREATED)
            else ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/name/{un}")
    fun getEmployeeByEmployeename(@PathVariable("un") employeename: String): ResponseEntity<Employee> {
        val employee = employeeService.findByUsername(employeename)
        return if (employee != null) ResponseEntity(employee, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable("id") employeeId: Int): ResponseEntity<Employee> {
        val employee = employeeService.findById(employeeId)
        return if (employee != null) ResponseEntity(employee, HttpStatus.OK)
               else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("/{id}")
    fun updateEmployeeById(@PathVariable("id") uId: Int, @RequestBody employee: Employee): ResponseEntity<Employee> {
        val employee = employeeService.updateById(uId, employee)
        return if (employee != null) ResponseEntity(employee, HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/{id}")
    fun deleteEmployeeById(@PathVariable("id") uId: Int): ResponseEntity<Employee> {
        return if (employeeService.deleteById(uId)) ResponseEntity(HttpStatus.NO_CONTENT)
        else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}