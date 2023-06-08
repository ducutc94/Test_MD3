package DAO;

import model.Department;
import model.Employee;
import service.DepartmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final Connection connection= MyConnection.getConnection();
    private final DepartmentService departmentService = DepartmentService.getInstance();
    private final String SELECT_ALL_EMPLOYEE = "select * from employee;";
    private final String SELECT_BY_ID_EMPLOYEE = "select * from employee where id = ?;";
    private final String INSERT_INTO_EMPLOYEE = "insert into employee(name,email,address,phone,salary,department_id ) value (?,?,?,?,?,?);";
    private final String UPDATE_BY_ID_EMPLOYEE = "update employee set name = ?,email = ?,address = ?,phone = ?,salary = ?,department_id = ?  where id = ?;";
    private final String DELETE_BY_ID_EMPLOYEE = "delete from employee where id = ?";
    private final String SEARCH_NAME = "select * from employee where name like ?;";

    public EmployeeDAO() {
    }
    public List<Employee> getEmployee() {
        List<Employee> employeeList=new ArrayList<>();
        try (PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_EMPLOYEE)){
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                String address=resultSet.getString("address");
                String phone=resultSet.getString("phone");
                Double salary=resultSet.getDouble("salary");
                int department_id=resultSet.getInt("department_id");
                Department department = departmentService.findById(department_id);
                Employee employee=new Employee(id,name,email,address,phone,salary,department);
                employeeList.add(employee);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }return employeeList;
    }
    public void addEmployee(Employee employee) {
        try(PreparedStatement preparedStatement=connection.prepareStatement(INSERT_INTO_EMPLOYEE)) {
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setString(2,employee.getEmail());
            preparedStatement.setString(3,employee.getAddress());
            preparedStatement.setString(4,employee.getPhone());
            preparedStatement.setDouble(5,employee.getSalary());
            preparedStatement.setInt(6,employee.getDepartment().getId());

            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update( Employee employee){
        try(PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_BY_ID_EMPLOYEE)){
            preparedStatement.setString(1,employee.getName());
            preparedStatement.setString(2,employee.getEmail());
            preparedStatement.setString(3,employee.getAddress());
            preparedStatement.setString(4,employee.getPhone());
            preparedStatement.setDouble(5,employee.getSalary());
            preparedStatement.setInt(6,employee.getDepartment().getId());
            preparedStatement.setInt(7,employee.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteById(int id) {
        try (PreparedStatement preparedStatement=connection.prepareStatement(DELETE_BY_ID_EMPLOYEE)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Employee getById(int id) {
        Employee employee=null;
        try (PreparedStatement preparedStatement=connection.prepareStatement(SELECT_BY_ID_EMPLOYEE)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String name=resultSet.getString("name");
                String email=resultSet.getString("email");
                String address=resultSet.getString("address");
                String phone=resultSet.getString("phone");
                Double salary=resultSet.getDouble("salary");
                int department_id=resultSet.getInt("department_id");
                Department department = departmentService.findById(department_id);
                employee=new Employee(id,name,email,address,phone,salary,department);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employee;

    }
    public List<Employee> searchName(String name){
        List<Employee> employeeList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_NAME)) {
            preparedStatement.setString(1,"%" + name + "%" );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name1 = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                double salary = resultSet.getDouble("salary");
                int department_id = resultSet.getInt("department_id");
                Department department = departmentService.findById(department_id) ;
                Employee employee = new Employee(id,name1,email,address,phone,salary,department);
                employeeList.add(employee);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employeeList;
    }
}
