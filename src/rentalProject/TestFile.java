package rentalProject;

import java.time.LocalDate;

public class TestFile {
	public static void main(String[] args) {
		Vehicle v;
		Customer c;
		RentalRecord r;
		
		c = new Customer(1, "Tristan");
		RentalSystem.getInstance().saveCustomer(c);
		
		v = new Car("Tristan", "Tristan", 1, 1);
		r = new RentalRecord(v, c, LocalDate.now(), 1, "Tristan");
		RentalSystem.getInstance().saveVehicle(v);
		RentalSystem.getInstance().saveRecord(r);
		
		v = new Motorcycle("Tristan", "Tristan", 1, false);
		r = new RentalRecord(v, c, LocalDate.now(), 1, "Tristan");
		RentalSystem.getInstance().saveVehicle(v);
		RentalSystem.getInstance().saveRecord(r);
		
		v = new SportCar("Tristan", "Tristan", 1, 1, 1, false);
		r = new RentalRecord(v, c, LocalDate.now(), 1, "Tristan");
		RentalSystem.getInstance().saveVehicle(v);
		RentalSystem.getInstance().saveRecord(r);
		
		v = new Truck("Tristan", "Tristan", 1, 1);
		r = new RentalRecord(v, c, LocalDate.now(), 1, "Tristan");
		RentalSystem.getInstance().saveVehicle(v);
		RentalSystem.getInstance().saveRecord(r);
		
		RentalSystem.getInstance().loadData();
	}
}
