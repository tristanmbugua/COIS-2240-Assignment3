package testing;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import rentalProject.*;


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
}