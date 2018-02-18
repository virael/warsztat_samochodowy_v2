package pl.coderslab.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import pl.coderslab.model.Vehicle;
import pl.coderslab.services.VehicleDbUtil;


@WebServlet("/VehicleControllerServlet")
public class VehicleControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VehicleDbUtil vehicleDbUtil;

	@Resource(name = "jdbc/warsztat_samochodwy")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our student db util ... and pass in the conn pool / datasource
		try {
			vehicleDbUtil = new VehicleDbUtil(dataSource);
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
				listVehicles(request, response); // lista pojazdów
				break;

			case "ADD":
				addVehicles(request, response); // dodaj pojazd
				break;

			case "LOAD":
				loadVehicles(request, response); // wczytaj pojazd do update'a
				break;

			case "UPDATE":
				updateVehicles(request, response); // update pojazdu
				break;
				
			case "DELETE":
				deleteVehicles(request, response); // usuń pojazd
				break;
				
//			case "SEARCH":
//                searchCustomers(request, response);
//                break;

			default:
				listVehicles(request, response); // domyślan lista pojazdów
				break;
			}

		} catch (Exception exc) {
			// TODO Auto-generated catch block
			throw new ServletException(exc);
		}

	}

//	private void searchCustomers(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// read search name from form data
//        String theSearchName = request.getParameter("theSearchName");
//        
//        // search students from db util
//        List<Customer> customers = customerDbUtil.searchCustomers(theSearchName);
//        
//        // add students to the request
//        request.setAttribute("CUSTOMER_LIST", customers);
//                
//        // send to JSP page (view)
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/list-customers.jsp");
//        dispatcher.forward(request, response);
//		
//	}

	private void deleteVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("vehicleId");
		
		vehicleDbUtil.deleteVehicle(theCustomerId); // tu może być potrzeba baza danych customera i/lub deleteCustomer
		
		listVehicles(request, response);
		
	}

	private void updateVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		int id = Integer.parseInt(request.getParameter("customerId")); // tu też customer id zamiast vehicle id
		String brand = request.getParameter("brand");
		String model = request.getParameter("model");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // przerabia string na datę
		
		String yearOfProduction = request.getParameter("yearOfProduction");
		Date yearOfProductionToDate = sdf.parse(yearOfProduction);
		String registration = request.getParameter("registration");
		String nextService = request.getParameter("nextService");
		Date nextServiceToDate = sdf.parse(nextService);

		
//		brand=?, model=?, yearOfProduction=?, registration=?, nextService=?
		// create a new student object
		Vehicle theVehicle = new Vehicle (id, brand, model, yearOfProductionToDate, registration, nextServiceToDate);

		// perform update on database
		vehicleDbUtil.updateVehicle(theVehicle);

		// send them back to the "list students" page
		listVehicles(request, response);

	}

	private void loadVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("vehicleId"); //tu niby też ma szukać po id customera

		Vehicle theVehicle = vehicleDbUtil.getVehicles(theCustomerId);

		request.setAttribute("THE_VEHICLE", theVehicle);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/update-vehicle.jsp");
		dispatcher.forward(request, response);
	}

	private void addVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("customerId"); // to dodane
		
		String brand = request.getParameter("brand");
		String model = request.getParameter("model");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // przerabia string na datę
		
		String yearOfProduction = request.getParameter("yearOfProduction");
		Date yearOfProductionToDate = sdf.parse(yearOfProduction);
		String registration = request.getParameter("registration");
		String nextService = request.getParameter("nextService");
		Date nextServiceToDate = sdf.parse(nextService);
		
		// tu muszę jeszcze jakoś dodać id customera

		Vehicle theVehicle = new Vehicle(brand, model, yearOfProductionToDate, registration, nextServiceToDate);

		vehicleDbUtil.addVehicle(theVehicle);

		listVehicles(request, response);
	}

	private void listVehicles(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String theCustomerId = request.getParameter("customerId"); // to dodane
		
//		Vehicle theVehicle = vehicleDbUtil.getVehicle(theCustomerId);
		// get students from db util
		List<Vehicle> vehicles = vehicleDbUtil.getVehicle(theCustomerId); // getVehicles -> getVehicle

		// add vehicles to the request
		request.setAttribute("VEHICLES_LIST", vehicles);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/list-vehicles.jsp");
		dispatcher.forward(request, response);
	}

}
