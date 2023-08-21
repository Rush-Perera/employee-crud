package com.jiat.employeecrud2.app.controllers;

import com.jiat.employeecrud2.app.dao.EmployeeDAO;
import com.jiat.employeecrud2.app.entity.Employee;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.List;

@Path("/employee")
public class EmployeeController {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEmployee(@PathParam("id") int id) {
        Employee employee = employeeDAO.getById(id);
        if (employee != null) {
            return "Name: " + employee.getName() + "\n"
                    + "Position: " + employee.getPosition() + "\n"
                    + "Department: " + employee.getDepartment() + "\n"
                    + "Hire Date: " + employee.getHireDate() + "\n"
                    + "Salary: " + employee.getSalary();
        } else {
            return "Employee not found.";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllEmployees() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        StringBuilder result = new StringBuilder();
        for (Employee employee : employees) {
            result.append("Employee ID: ").append(employee.getId()).append("\n")
                    .append("Name: ").append(employee.getName()).append("\n")
                    .append("Position: ").append(employee.getPosition()).append("\n")
                    .append("Department: ").append(employee.getDepartment()).append("\n")
                    .append("Hire Date: ").append(employee.getHireDate()).append("\n")
                    .append("Salary: ").append(employee.getSalary()).append("\n\n");
        }
        return result.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addEmployee(
            @FormParam("name") String name,
            @FormParam("position") String position,
            @FormParam("department") String department,
            @FormParam("hireDate") Date hireDate,
            @FormParam("salary") Double salary) {

        Employee employee = new Employee();

        employee.setName(name);
        employee.setPosition(position);
        employee.setDepartment(department);
        employee.setHireDate(hireDate);
        employee.setSalary(salary);

        employeeDAO.saveEmployee(employee);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateEmployee(
            @PathParam("id") int id,
            @FormParam("name") String name,
            @FormParam("position") String position,
            @FormParam("department") String department,
            @FormParam("hireDate") Date hireDate,
            @FormParam("salary") Double salary) {

        Employee existingEmployee = employeeDAO.getById(id);
        if (existingEmployee != null) {
            existingEmployee.setName(name);
            existingEmployee.setPosition(position);
            existingEmployee.setDepartment(department);
            existingEmployee.setHireDate(hireDate);
            existingEmployee.setSalary(salary);

            employeeDAO.updateEmployee(existingEmployee);
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteEmployee(@PathParam("id") int id) {
        employeeDAO.deleteEmployee(id);
    }
}
