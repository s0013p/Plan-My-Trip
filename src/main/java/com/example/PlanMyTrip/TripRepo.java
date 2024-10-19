package com.example.PlanMyTrip;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepo extends JpaRepository<TripEntity, Long>{

}
