package pl.coderslab.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import pl.coderslab.model.Customer;
import pl.coderslab.services.CustomerDbUtil;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/CustomerControllerServlet")
public class CustomerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CustomerDbUtil customerDbUtil;

	@Resource(name = "jdbc/warsztat_samochodwy")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our student db util ... and pass in the conn pool / datasource
		try {
			customerDbUtil = new CustomerDbUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// list the customers ... in mvc fashion
		try {

			String theCommand = request.getParameter("command");

			if (theCommand == null) {
				theCommand = "LIST";
			}

			switch (theCommand) {

			case "LIST":
				listCustomers(request, response);
				break;

			case "ADD":
				addCustomers(request, response);
				break;

			case "LOAD":
				loadCustomers(request, response);
				break;

			case "UPDATE":
				updateCustomers(request, response);
				break;
				
			case "DELETE":
				deleteCustomers(request, response);
				break;
				
			case "SEARCH":
                searchCustomers(request, response);
                break;

			default:
				listCustomers(request, response);
				break;
			}

		} catch (Exception exc) {
			// TODO Auto-generated catch block
			throw new ServletException(exc);
		}

	}

	private void searchCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read search name from form data
        String theSearchName = request.getParameter("theSearchName");
        
        // search students from db util
        List<Customer> customers = customerDbUtil.searchCustomers(theSearchName);
        
        // add students to the request
        request.setAttribute("CUSTOMER_LIST", customers);
                
        // send to JSP page (view)
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/list-customers.jsp");
        dispatcher.forward(request, response);
		
	}

	private void deleteCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("customerId");
		
		customerDbUtil.deleteCustomer(theCustomerId);
		
		listCustomers(request, response);
		
	}

	private void updateCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		int id = Integer.parseInt(request.getParameter("customerId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		// create a new student object
		Customer theCustomer = new Customer(id, firstName, lastName, email);

		// perform update on database
		customerDbUtil.updateCustomer(theCustomer);

		// send them back to the "list students" page
		listCustomers(request, response);

	}

	private void loadCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("customerId");

		Customer theCustomer = customerDbUtil.getCustomers(theCustomerId);

		request.setAttribute("THE_CUSTOMER", theCustomer);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/update-customer.jsp");
		dispatcher.forward(request, response);
	}

	private void addCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		Customer theCustomer = new Customer(firstName, lastName, email);

		customerDbUtil.addCustomer(theCustomer);

		listCustomers(request, response);
	}

	private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// get students from db util
		List<Customer> customers = customerDbUtil.getCustomers();

		// add students to the request
		request.setAttribute("CUSTOMER_LIST", customers);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/list-customers.jsp");
		dispatcher.forward(request, response);
	}

}
