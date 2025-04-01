package testing;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import rentalProject.*;

import java.time.LocalDate;

import java.lang.reflect.*;


class VehicleRentalTest {
	//Vehicle License Plate Validation
	@Test
	void testLicensePlateValidation() {
		Vehicle v = new Car("Tristan", "Tristan", 1, 1);
		
		
		//Valid plates
		Assertions.assertTrue(v.setLicensePlate("AAA100"));
		
		Assertions.assertTrue(v.setLicensePlate("ABC567"));
		
		Assertions.assertTrue(v.setLicensePlate("ZZZ999"));
		
		
		//Invalid plates
		Assertions.assertThrows(IllegalArgumentException.class, () -> { v.setLicensePlate(""); });

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			v.setLicensePlate(null);
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> { v.setLicensePlate("AAA1000"); });
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> { v.setLicensePlate("ZZZ99"); });
	}

	@Test
	void testRentAndReturnVehicle() {
		//Instantiate Objects
		Vehicle v = new Car("Tristan", "Tristan", 1, 1);
		Customer c = new Customer(1, "Tristan");
		
		//Verify the vehicle is available
		Assertions.assertTrue(v.getStatus().equals(Vehicle.VehicleStatus.AVAILABLE));
		
		//Retrieve RentalSystem instance
		RentalSystem r = RentalSystem.getInstance();
		
		//rentVehicle() method
			//Rent Vehicle, verify success
			Assertions.assertTrue(r.rentVehicle(v, c, LocalDate.now(), 1));
			
			//Verify the vehicle is rented
			Assertions.assertTrue(v.getStatus().equals(Vehicle.VehicleStatus.RENTED));
			
			//Rent Vehicle, verify failure
			Assertions.assertFalse(r.rentVehicle(v, c, LocalDate.now(), 1));
		
		//returnVehicle() method		
			//Return Vehicle, verify success
			Assertions.assertTrue(r.returnVehicle(v, c, LocalDate.now(), 1));
			
			//Verify the vehicle is available
			Assertions.assertTrue(v.getStatus().equals(Vehicle.VehicleStatus.AVAILABLE));
			
			//Return Vehicle, verify failure
			Assertions.assertFalse(r.returnVehicle(v, c, LocalDate.now(), 1));
	}	
	
	@Test
	void testSingletonRentalSystem() throws Exception {
		Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
				
		//Verify the constructor is private
		Assertions.assertTrue(constructor.getModifiers() == Modifier.PRIVATE);
		
		//Assert singleton instance is not null
		Assertions.assertNotNull(RentalSystem.getInstance());
	}

}