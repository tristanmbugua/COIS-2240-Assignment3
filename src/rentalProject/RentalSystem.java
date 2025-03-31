package rentalProject;

import java.util.*;
import java.io.*;
import java.time.*;

import rentalProject.Vehicle.VehicleStatus;

public class RentalSystem {
	private static RentalSystem instance;
	
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private RentalHistory rentalHistory;

  //Data Processing Objects
    private File data;
    private Scanner reader;
    private String obj;
	
    //Generic objects
    private Vehicle v;
    private Customer c;
    private RentalRecord r;

    //Generic Vehicle Information
    private String licensePlate;
    private String make;
    private String model;
    private String year;
    private String statusStr;
    private VehicleStatus status;
    
    //Specific Vehicle Details
    private String sideCar;
    private String horsePower;
    private String seats;
    private String turbo;
    private String cargoCapacity;
    
    //Generic Customer Information
    private String c_ID;
    private String c_Name;
    
    //Generic Rental Record
    private String recordDate;
    private String totalAmount;
    private String recordType;
    
    private RentalSystem() {
		this.vehicles = new ArrayList<>();
		this.customers = new ArrayList<>();
		this.rentalHistory = new RentalHistory();
	}
	
	public static RentalSystem getInstance() {
		if (instance == null) {
			return new RentalSystem();
		} else {
			return instance;
		}
	}
	
    public boolean addVehicle(Vehicle vehicle) {
        return saveVehicle(vehicle);
    }

    public boolean addCustomer(Customer customer) {
    	return saveCustomer(customer);
    }

    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            saveRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            saveRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(boolean onlyAvailable) {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(String id) {
        for (Customer c : customers)
            if (c.getCustomerId() == Integer.parseInt(id))
                return c;
        return null;
    }
    
    public boolean saveVehicle(Vehicle vehicle) {
    	//Reset existing data
    	loadData();
    	
    	//Check for duplicate using license plate
    	for(Vehicle v_: vehicles) {
    		if (v_.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate())) {
    			System.out.println("Duplicate Vehicle!");
    			return false;
    		}
    	}
    	
    	try {
    		File vehicles = new File("./src/rentalProject/vehicles.txt");
            FileWriter vehicleWriter = new FileWriter(vehicles, true);
            vehicleWriter.append(vehicle.getInfo() + "\n");
            vehicleWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    	return true;
    }
    
    public boolean saveCustomer(Customer customer) {
    	//Reset existing data
    	loadData();
    	
    	//Check for duplicate using Customer ID
    	for(Customer c_: customers) {
    		if (c_.getCustomerName().equals(customer.getCustomerName())) {
    			System.out.println("Duplicate Customer!");
    			return false;
    		}
    	}
    	
    	try {
    		File customers = new File("./src/rentalProject/customers.txt");
            FileWriter customerWriter = new FileWriter(customers, true);
            customerWriter.append(customer.toString() + "\n");
            customerWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    	
    	return true;
    }
    
    public void saveRecord(RentalRecord rentalRecord) {
    	try {
    		File rentalRecords = new File("./src/rentalProject/rental_records.txt");
            FileWriter rentalRecordWriter = new FileWriter(rentalRecords, true);
            rentalRecordWriter.append(rentalRecord.toString() + "\n");
            rentalRecordWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    }
    
    public void loadData() {
    	//Reset data storage
    	vehicles = new ArrayList<Vehicle>();
    	customers = new ArrayList<Customer>();
    	rentalHistory = new RentalHistory();
    	
    	
    	try {
        	//Load vehicles
        	data = new File("./src/rentalProject/vehicles.txt");
        	reader = new Scanner(data);
        	while(reader.hasNextLine()) {
        		obj = reader.nextLine();
        		
        		//Parse the get info output.
	        		//Trim basic info
	        			//Trim License Plate
			    		obj = obj.substring(2);
			    		licensePlate = obj.substring(0, obj.indexOf(" |"));
			    		obj = obj.substring(obj.indexOf(" |"));
			    		
			    		//Trim Make
			    		obj = obj.substring(3);
			    		make = obj.substring(0, obj.indexOf(" |"));
			    		obj = obj.substring(obj.indexOf(" |"));
			    		
			    		//Trim Model
			    		obj = obj.substring(3);
			    		model = obj.substring(0, obj.indexOf(" |"));
			    		obj = obj.substring(obj.indexOf(" |"));
			    		
			    		//Trim Year
			    		obj = obj.substring(3);
			    		year = obj.substring(0, obj.indexOf(" |"));
			    		obj = obj.substring(obj.indexOf(" |"));
			    		
			    		//Trim Status
			    		obj = obj.substring(3);
			    		statusStr = obj.substring(0, obj.indexOf(" |"));
			    		switch(statusStr) {
			    			case "AVAILABLE":
			    				status = VehicleStatus.AVAILABLE;
			    				break;
			    			case "RESERVED":
			    				status = VehicleStatus.RESERVED;
			    				break;
			    			case "RENTED":
			    				status = VehicleStatus.RENTED;
			    				break;
			    			case "MAINTENANCE":
			    				status = VehicleStatus.MAINTENANCE;
			    				break;
			    			case "OUTOFSERVICE":
			    				status = VehicleStatus.OUTOFSERVICE;
			    				break;
			    		}
			    		obj = obj.substring(obj.indexOf(" |"));
        			
		    		//Differentiate between vehicle type
			    		//SportCar
	        			if(obj.contains("Horsepower")) {
	        				//Trim Horsepower
	        				obj = obj.substring(obj.indexOf(":") + 2);
		        	    	horsePower = obj.substring(0, obj.indexOf(" |"));
		        	    	obj = obj.substring(obj.indexOf(" |"));
		        	    	
	        				//Trim Turbo
		        	    	obj = obj.substring(obj.indexOf(":") + 2);
		        	    	seats = obj.substring(0, obj.indexOf(" |"));
		        	    	obj = obj.substring(obj.indexOf(" |"));
		        	    	
		        	    	//Trim Number of Seats
	        				obj = obj.substring(obj.indexOf(":") + 2);
		        	    	turbo = obj;
		        	    	
		        	    	v = new SportCar(
		        	    			make, model, Integer.parseInt(year), Integer.parseInt(seats),
		        	    			Integer.parseInt(horsePower), Boolean.getBoolean(turbo)
		        	    		);
		        	    	v.setLicensePlate(licensePlate);
		        	    	v.setStatus(status);
	        			}
	        			//Motorcycle
	        			else if (obj.contains("Sidecar")) {
		        	    	//Trim Sidecar
	        				obj = obj.substring(obj.indexOf(":") + 2);
		        	    	sideCar = obj;
		        	    	
		        	    	v = new Motorcycle(make, model, Integer.parseInt(year), Boolean.getBoolean(sideCar));
		        	    	v.setLicensePlate(licensePlate);
		        	    	v.setStatus(status);
	        			}
	        			//Car
	        			else if (obj.contains("Seats:")) {
	        				//Trim Number of Seats
	        				obj = obj.substring(obj.indexOf(":") + 2);
		        	    	seats = obj;
		        	    	
		        	    	v = new Car(make, model, Integer.parseInt(year), Integer.parseInt(seats));
		        	    	v.setLicensePlate(licensePlate);
		        	    	v.setStatus(status);
	        			}
	        			//Truck
	        			else {
	        				//Trim Cargo Capacity
		        	    	obj = obj.substring(obj.indexOf(":") + 2);
		        	    	cargoCapacity = obj;
		        	    	v = new Truck(make, model, Integer.parseInt(year), Double.parseDouble(cargoCapacity));
		        	    	v.setLicensePlate(licensePlate);
		        	    	v.setStatus(status);
	        			}
	        		
	        	vehicles.add(v);
	        }
        	
        	//Load customers
        	data = new File("./src/rentalProject/customers.txt");
        	reader = new Scanner(data);
        	while(reader.hasNextLine()) {
        		obj = reader.nextLine();
        		
        		//Parse the get info output.
        		
        		//Trim Customer ID
        		obj = obj.substring(obj.indexOf(":") + 2);
        		c_ID = obj.substring(0, obj.indexOf(" |"));
    	    	obj = obj.substring(obj.indexOf(" |")); 
        		
    	    	//Trim Customer Name
    	    	obj = obj.substring(obj.indexOf(":") + 2);
        		c_Name = obj;
    	    	
        		customers.add(new Customer(Integer.parseInt(c_ID), c_Name));
        	}
        	
        	//Load rental records
        	data = new File("./src/rentalProject/rental_records.txt");
        	reader = new Scanner(data);
        	while(reader.hasNextLine()) {
        		//Parse the get info output.
        		obj = reader.nextLine();
        		
        		//Trim Record Type
        		recordType = obj.substring(0, obj.indexOf(" |"));
    	    	obj = obj.substring(obj.indexOf(" |"));
        		
    	    	//Trim Vehicle
    	    	obj = obj.substring(obj.indexOf(":") + 2);
        		licensePlate = obj.substring(0, obj.indexOf(" |"));
        		for (Vehicle v_: vehicles) {
        			if (v_.getLicensePlate().equalsIgnoreCase(licensePlate)) {
        				v = v_;
        				break;
        			}
        		}
    	    	obj = obj.substring(obj.indexOf(" |"));
        		
    	    	//Trim Customer
    	    	obj = obj.substring(obj.indexOf(":") + 2);
        		c_Name = obj.substring(0, obj.indexOf(" |"));
        		for (Customer c_: customers) {
        			if (c_.getCustomerName().equals(c_Name)) {
        				c = c_;
        				break;
        			}
        		}
    	    	obj = obj.substring(obj.indexOf(" |"));
    	    	
    	    	//Trim Record Date
    	    	obj = obj.substring(obj.indexOf(":") + 2);
        		recordDate = obj.substring(0, obj.indexOf(" |"));
    	    	obj = obj.substring(obj.indexOf(" |"));
    	    	
        		//Trim Total Amount
    	    	obj = obj.substring(obj.indexOf("$") + 1);
        		totalAmount = obj;
        		
        		rentalHistory.addRecord(new RentalRecord(v, c, LocalDate.parse(recordDate), Double.parseDouble(totalAmount), recordType));
        	}
        	
        	reader.close();
    	} catch (Exception e) {
    		System.out.println("Error reading from file!");
    	}
    }
}