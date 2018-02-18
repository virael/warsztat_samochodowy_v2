package pl.coderslab.model;

import java.util.Date;

public class Vehicle {
	private int id;
	private String brand;
	private String model;
	private Date yearOfProduction;
	private String registration;
	private Date nextService;
	private int customerId;
	private int employeeId;
	
	public Vehicle(String brand, String model, Date yearOfProduction, String registration, Date nextService) {
		this.brand = brand;
		this.model = model;
		this.yearOfProduction = yearOfProduction;
		this.registration = registration;
		this.nextService = nextService;
	}

	public Vehicle(int id, String brand, String model, Date yearOfProduction, String registration, Date nextService) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.yearOfProduction = yearOfProduction;
		this.registration = registration;
		this.nextService = nextService;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getYearOfProduction() {
		return yearOfProduction;
	}

	public void setYearOfProduction(Date yearOfProduction) {
		this.yearOfProduction = yearOfProduction;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Date getNextService() {
		return nextService;
	}

	public void setNextService(Date nextService) {
		this.nextService = nextService;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", brand=" + brand + ", model=" + model + ", yearOfProduction=" + yearOfProduction
				+ ", registration=" + registration + ", nextService=" + nextService + ", customerId=" + customerId
				+ ", employeeId=" + employeeId + "]";
	}
	
	
}
