package com.driver.repository;
import com.driver.model.Driver;
import com.driver.model.TripBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TripBookingRepository extends JpaRepository<TripBooking, Integer>{

}
