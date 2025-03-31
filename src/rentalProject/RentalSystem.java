package rentalProject;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentalSystem {
	private static RentalSystem instance;
	
	private RentalSystem() {
		instance = new RentalSystem();
		instance.vehicles = new ArrayList<>();
		instance.customers = new ArrayList<>();
		instance.rentalHistory = new RentalHistory();
	}
	
	public static RentalSystem getInstance() {
		if (instance == null) {
			return new RentalSystem();
		} else {
			return instance;
		}
	}
	
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private RentalHistory rentalHistory;

    public void addVehicle(Vehicle vehicle) {
        saveVehicle(vehicle);
    }

    public void addCustomer(Customer customer) {
        saveCustomer(customer);
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
    
    public void saveVehicle(Vehicle vehicle) {
    	try {
    		File vehicles = new File("./vehicles.txt");
            FileWriter vehicleWriter = new FileWriter(vehicles, true);
            vehicleWriter.append(vehicle.getInfo());
            vehicleWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    }
    
    public void saveCustomer(Customer customer) {
    	try {
    		File customers = new File("./customers.txt");
            FileWriter customerWriter = new FileWriter(customers, true);
            customerWriter.append(customer.toString());
            customerWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    }
    
    public void saveRecord(RentalRecord rentalRecord) {
    	try {
    		File rentalRecords = new File("./rental_records.txt");
            FileWriter rentalRecordWriter = new FileWriter(rentalRecords, true);
            rentalRecordWriter.append(rentalRecord.toString());
            rentalRecordWriter.close();
    	} catch (Exception e) {
    		System.out.println("Error writing to file!");
    	}
    }
    
}