package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL queryr
		List<Driver> availableDrivers = driverRepository2.findByAvailableIsTrueOrderByDriverId();
		if (availableDrivers.isEmpty()) {
			throw new Exception("No cab available!");
		}
		Driver driver=availableDrivers.get(0);
		Optional<TripBooking>optionalTripBooking=tripBookingRepository2.findByDriverAndStatus(driver);
		if(optionalTripBooking.isPresent()){
			throw new Exception("Driver is Already Book");
		}
		TripBooking tripBooking=new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		//tripBooking.setStatus(TripStatus.COMPLETED);

		return tripBookingRepository2.save(tripBooking);


	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBookingOptional = tripBookingRepository2.findById(tripId);
		if (tripBookingOptional.isPresent()) {
			TripBooking tripBooking = tripBookingOptional.get();
			tripBooking.setStatus(TripStatus.CANCELED);

			tripBookingRepository2.save(tripBooking);
		}
	}

	@Override
	public void completeTrip(Integer tripId) {
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBookingOptional = tripBookingRepository2.findById(tripId);
		if (tripBookingOptional.isPresent()) {
			TripBooking tripBooking = tripBookingOptional.get();
			tripBookingRepository2.deleteById(tripId);
			tripBooking.setStatus(TripStatus.COMPLETED);
			tripBookingRepository2.save(tripBooking);
		}
	}
}
