package service;

import DAO.DepartmentDAO;
import DAO.EmployeeDAO;
import model.Employee;

import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO;
    private static EmployeeService employeeService;

    private EmployeeService() {
        employeeDAO = new EmployeeDAO();
    }
    public  static EmployeeService getInstance(){
        if (employeeService == null){
            employeeService = new EmployeeService();
        }
        return employeeService;
    }
    public Employee getById(int id) {
        return employeeDAO.getById(id);
    }
    public void deleteById(int id) {
        employeeDAO.deleteById(id);
    }
    public void update( Employee employee){
        employeeDAO.update(employee);
    }
    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }
    public List<Employee> getEmployee() {
        return employeeDAO.getEmployee();
    }
    public List<Employee> searchName(String name){
        return employeeDAO.searchName(name);
    }
}
