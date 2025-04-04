package rentalProject;

import java.io.*;
import java.util.*;

public abstract class Vehicle{
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status; 
    public enum VehicleStatus { AVAILABLE, RESERVED, RENTED, MAINTENANCE, OUTOFSERVICE }

    public Vehicle(String make, String model, int year) {
    	if (make == null || make.isEmpty())
    		this.make = null;
    	else
    		this.make = capitalize(make);
    	
    	if (model == null || model.isEmpty())
    		this.model = null;
    	else
    		this.model = capitalize(model);
    	
        this.year = year;
        this.status = VehicleStatus.AVAILABLE;
        this.licensePlate = null;
    }
    
    public Vehicle() {
        this(null, null, 0);
    }

    public boolean setLicensePlate(String plate) {
    	if (isValidPlate(plate)) {
    		plate = plate.toUpperCase();
    		this.licensePlate = plate;
        	return true;
        }
        	
        throw new IllegalArgumentException("Illegal Plate Entered!");
    }

    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status;
    }

    private String capitalize(String input) {
    	return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    
    private boolean isValidPlate(String plate) {
    	try {
    		if (plate.equals("") || plate == null) {
        		return false;
        	}
    	} catch (NullPointerException e) {
    		return false;
    	}
    	
    	
    	int letterCount = 0;
    	int numCount = 0;
    	
    	for (char c: plate.toCharArray()) {
    		//Check character
    		if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
    			letterCount++;
    		}
    		//Check for number
    		if ((c >= 48 && c <= 57)) {
    			numCount++;
    		}
    	}
    	
    	
    	if (letterCount != 3 || numCount != 3) {
    		System.out.println("Invalid plate!");
    		return false;
    	}
    	
    	return true;
    }
}