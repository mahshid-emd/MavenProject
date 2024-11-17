package tamrin.jdbc.session6;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static final String URL = "jdbc:h2:tcp://localhost:9092/~/testdb";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
    public static void main( String[] args ) throws SQLException{
    	
    	int depId= 1;
    	int locId = 2;
    	String first_name = "ali";
    	String last_name = "khalili";
    	String email = "aliKhalili@gmail.com";
    	String phone_number = "12345678";
    	Date hire_date = new Date(4/2/2024);
    	int job_id = 1;
    	int salary = 4000;
    	Object manager_id = null;
    	int department_id = 1;
    	String country_name = "new_Argentina";
    	String country_id = "AR";
    	
    	try(
	        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    			
    		PreparedStatement preparedStatement1 = connection.prepareStatement(
    				                               "SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = ?");
    		PreparedStatement preparedStatement2 = connection.prepareStatement(
    				                               "SELECT * FROM DEPARTMENTS WHERE LOCATION_ID = ?");
			PreparedStatement insertStmt = connection.prepareStatement(
					   "INSERT INTO employees "
					 + "(first_name, last_name, email, phone_number, hire_date, job_id, salary, manager_id, department_id)" 
	    			 + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)" );
    			
    		PreparedStatement updateStmt = connection.prepareStatement(
    				   "UPDATE COUNTRIES SET country_name = ? WHERE country_id = ?");
    		){
    		
		    System.out.println("Connected to the H2 database successfully!");
		    
		    preparedStatement1.setLong(1, depId);
		    try (ResultSet resultSet = preparedStatement1.executeQuery();) {
			    while(resultSet.next()) {
			    	
			        String firstName = resultSet.getString("FIRST_NAME");
			        String lastName = resultSet.getString("LAST_NAME");
			        System.out.println("firstName: " + firstName + ", lastName: " + lastName);
                }
		    }
		    
		    preparedStatement2.setInt(1, locId);
		    try (ResultSet resultSet = preparedStatement2.executeQuery();) {
			    while(resultSet.next()) {
			    	
			        String DEPARTMENT_NAME = resultSet.getString("DEPARTMENT_NAME");
			        System.out.println("DEPARTMENT_NAME: " + DEPARTMENT_NAME);
                }
		    }
		    
		    insertStmt.setString(1, first_name);
		    insertStmt.setString(2, last_name);
		    insertStmt.setString(3, email);
		    insertStmt.setString(4, phone_number);
		    insertStmt.setDate(5, hire_date);
		    insertStmt.setLong(6, job_id);
		    insertStmt.setLong(7, salary);
		    //insertStmt.setInt(8, manager_id);
		    insertStmt.setObject(8, manager_id, java.sql.Types.INTEGER);
		    insertStmt.setInt(9, department_id);
		    insertStmt.executeUpdate();
		    
		    updateStmt.setString(1, country_name); 
		    updateStmt.setString(2, country_id); 
		    updateStmt.executeUpdate();
    	}
    }
}
