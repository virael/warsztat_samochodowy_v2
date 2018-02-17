package pl.coderslab.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import pl.coderslab.model.Customer;

public class CustomerDbUtil {

	private DataSource dataSource;

	public CustomerDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Customer> getCustomers() throws Exception {

		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "select * from Client order by last_name"; // tu do poprawy

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");

				// create new student object
				Customer tempStudent = new Customer(id, firstName, lastName, email);

				// add it to the list of students
				customers.add(tempStudent);
			}

			return customers;
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

	public void addCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			myConn = dataSource.getConnection();

			String sql = "INSERT INTO `Client`(`first_name`, `last_name`, `email`) VALUES (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, theCustomer.getFirstName());
			myStmt.setString(2, theCustomer.getLastName());
			myStmt.setString(3, theCustomer.getEmail());

			myStmt.execute();

		} finally {

			close(myConn, myStmt, null);

		}

	}

	public Customer getCustomers(String theCustomerId) throws Exception {

		Customer theCustomer = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int customerId;

		try {
			customerId = Integer.parseInt(theCustomerId);

			myConn = dataSource.getConnection();

			String sql = "SELECT * FROM `Client` WHERE `id` = ?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, customerId);

			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");

				theCustomer = new Customer(customerId, firstName, lastName, email);
			} else {
				throw new Exception("Klient o podanym ID nie istnieje: ID - " + customerId);
			}

			return theCustomer;
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	public void updateCustomer(Customer theCustomer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create SQL update statement
			String sql = "update Client set first_name=?, last_name=?, email=? where id=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theCustomer.getFirstName());
			myStmt.setString(2, theCustomer.getLastName());
			myStmt.setString(3, theCustomer.getEmail());
			myStmt.setInt(4, theCustomer.getId());

			// execute SQL statement
			myStmt.execute();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteCustomer(String theCustomerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {

			int customerID = Integer.parseInt(theCustomerId);

			myConn = dataSource.getConnection();

			String sql = "DELETE FROM Client WHERE id=?";

			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, customerID);

			myStmt.execute();

		} finally {
			close(myConn, myStmt, null);
		}

	}

	public List<Customer> searchCustomers(String theSearchName) throws Exception {
		
		List<Customer> customers = new ArrayList<>();

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;

		try {

			// get connection to database
			myConn = dataSource.getConnection();

			//
			// only search by name if theSearchName is not empty
			//
			if (theSearchName != null && theSearchName.trim().length() > 0) {
				// create sql to search for students by name
				String sql = "select * from Client where lower(first_name) like ? or lower(last_name) like ?";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				// set params
				String theSearchNameLike = "%" + theSearchName.toLowerCase() + "%";
				myStmt.setString(1, theSearchNameLike);
				myStmt.setString(2, theSearchNameLike);

			} else {
				// create sql to get all students
				String sql = "select * from Client order by last_name";
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
			}

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");

				// create new student object
				Customer tempStudent = new Customer(id, firstName, lastName, email);

				// add it to the list of students
				customers.add(tempStudent);
			}

			return customers;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

}
