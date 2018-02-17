package pl.coderslab.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import pl.coderslab.model.Vehicle;

public class VehicleDbUtil {
	private DataSource dataSource;

	public VehicleDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Vehicle> getVehicles() throws Exception {

		List<Vehicle> vehicles = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "SELECT `brand`, `model`, `yearOfProduction`, `registration`, `nextService` FROM `Vehicle`"; // tu do poprawy

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
//				int id = myRs.getInt("id"); // wywalało że nie może znaleźć kolumny z id
				String brand = myRs.getString("brand");
				String model = myRs.getString("model");
				Date yearOfProduction = myRs.getDate("yearOfProduction");
				Date registration = myRs.getDate("registration");
				Date nextService = myRs.getDate("nextService");

				// create new student object
				Vehicle tempVehicle = new Vehicle(brand, model, yearOfProduction, registration, nextService);

				// add it to the list of students
				vehicles.add(tempVehicle);
			}

			return vehicles;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addVehicle(Vehicle theVehicle) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "INSERT INTO `Vehicle`(`brand`, `model`, `yearOfProduction`, `registration`, `nextService`, `customerId`, `employeeId`) VALUES (?, ?, ?, ?, ?, ?)";
			


			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, theVehicle.getBrand());
			myStmt.setString(2, theVehicle.getModel());
			myStmt.setDate(3, (java.sql.Date) theVehicle.getYearOfProduction()); // cholera wie czy dobrze
			myStmt.setDate(4, (java.sql.Date) theVehicle.getRegistration());
			myStmt.setDate(5, (java.sql.Date) theVehicle.getNextService());
			myStmt.setInt(6, theVehicle.getCustomerId()); // prawdopodbnie do usunięcia
			myStmt.setInt(7, theVehicle.getEmployeeId());

			

			myStmt.execute();

		} finally {

			close(myConn, myStmt, null);

		}

	}

	public Vehicle getVehicles(String theCustomerId) throws Exception {

		Vehicle theVehicle = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int customerId;

		try {
			customerId = Integer.parseInt(theCustomerId);

			myConn = dataSource.getConnection();

			String sql = "SELECT `brand`, `model`, `yearOfProduction`, `registration`, `nextService` FROM `Vehicle` WHERE customerId = ?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, customerId);

			myRs = myStmt.executeQuery();

			if (myRs.next()) {
//				int id = myRs.getInt("id");
				String brand = myRs.getString("brand");
				String model = myRs.getString("model");
				Date yearOfProduction = myRs.getDate("yearOfProduction");
				Date registration = myRs.getDate("registration");
				Date nextService = myRs.getDate("nextService");

				theVehicle = new Vehicle(brand, model, yearOfProduction, registration, nextService);
			} else {
				throw new Exception("Pojazd o podanym ID nie istnieje: ID - " + customerId);
			}

			return theVehicle;
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	public void updateVehicle(Vehicle theVehicle) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update Vehicle set brand=?, model=?, yearOfProduction=?, registration=?, nextService=? where customerId=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theVehicle.getBrand());
			myStmt.setString(2, theVehicle.getModel());
			myStmt.setDate(3, (java.sql.Date) theVehicle.getYearOfProduction());
			myStmt.setDate(4, (java.sql.Date) theVehicle.getRegistration());
			myStmt.setDate(5, (java.sql.Date) theVehicle.getNextService());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteVehicle(String theCustomerId) throws Exception { //tutaj leci customer id

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {

			int customerID = Integer.parseInt(theCustomerId);

			myConn = dataSource.getConnection();

			String sql = "DELETE FROM Vehicle WHERE customerId=?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, customerID);

			myStmt.execute();

		} finally {
			close(myConn, myStmt, null);
		}

	}

//	public List<Customer> searchCustomers(String theSearchName) throws Exception {
//		
//		List<Customer> customers = new ArrayList<>();
//
//		Connection myConn = null;
//		PreparedStatement myStmt = null;
//		ResultSet myRs = null;
//		int studentId;
//
//		try {
//
//			// get connection to database
//			myConn = dataSource.getConnection();
//
//			//
//			// only search by name if theSearchName is not empty
//			//
//			if (theSearchName != null && theSearchName.trim().length() > 0) {
//				// create sql to search for students by name
//				String sql = "select * from Client where lower(first_name) like ? or lower(last_name) like ?";
//				// create prepared statement
//				myStmt = myConn.prepareStatement(sql);
//				// set params
//				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
//				myStmt.setString(1, theSearchNameLike);
//				myStmt.setString(2, theSearchNameLike);
//
//			} else {
//				// create sql to get all students
//				String sql = "select * from Client order by last_name";
//				// create prepared statement
//				myStmt = myConn.prepareStatement(sql);
//			}
//
//			// execute statement
//			myRs = myStmt.executeQuery();
//
//			// retrieve data from result set row
//			while (myRs.next()) {
//
//				// retrieve data from result set row
//				int id = myRs.getInt("id");
//				String firstName = myRs.getString("first_name");
//				String lastName = myRs.getString("last_name");
//				String email = myRs.getString("email");
//
//				// create new student object
//				Customer tempStudent = new Customer(id, firstName, lastName, email);
//
//				// add it to the list of students
//				customers.add(tempStudent);
//			}
//
//			return customers;
//		} finally {
//			// clean up JDBC objects
//			close(myConn, myStmt, myRs);
//		}
//	}
}
