package com.example.PlanMyTrip;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class TrippController {

    @Autowired
    TripRepo tripRepo;

    @RequestMapping("/")
    public String homepage() {
        return "index"; // Return the index page
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ModelAndView bookPage() {
        ModelAndView modelAndView = new ModelAndView("book"); // Return the booking page
        modelAndView.addObject("tripEntity", new TripEntity()); // Add an empty TripEntity object for the form
        return modelAndView;
    }

    
    
    
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute TripEntity tripEntity) 
    {
        tripRepo.save(tripEntity); // Save the trip entity to the database

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bookconfirm"); // Redirect to the confirmation page
        modelAndView.addObject("tripEntity", tripEntity); // Add the trip entity to the model
        return modelAndView;
    }
    
    
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView findById(@RequestParam("tripId") Long tripId) {
        Optional<TripEntity> tripEntityOptional = tripRepo.findById(tripId); // Find the trip entity by ID
        ModelAndView modelAndView = new ModelAndView();

        if (tripEntityOptional.isPresent()) 
        {
            TripEntity tripEntity = tripEntityOptional.get();
            modelAndView.setViewName("search"); // Redirect to the details page
            modelAndView.addObject("tripEntity", tripEntity); // Add the trip entity to the model
        } 
        else 
        {
            modelAndView.setViewName("error"); // Redirect to an error page
        }        
        return modelAndView;
    }
    
  
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getData() {
        List<TripEntity> tripEntity = tripRepo.findAll(); // Fetch all trip entities
        ModelAndView modelAndView = new ModelAndView();
        
        if(!tripEntity.isEmpty())
        {
            modelAndView.setViewName("profile"); // Set the view to profile
            modelAndView.addObject("tripEntity", tripEntity); // Add the list of trip entities to the model
        }       
        else
        {
        	modelAndView.setViewName("error");
        }       
        return modelAndView;
    }

    @RequestMapping(value = "/update/{tripId}", method = RequestMethod.GET)
    public ModelAndView getTripForUpdate(@PathVariable("tripId") Long tripId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<TripEntity> tripEntityOptional = tripRepo.findById(tripId);

        if (tripEntityOptional.isPresent()) {
            TripEntity tripEntity = tripEntityOptional.get();
            modelAndView.setViewName("update"); // Show update form
            modelAndView.addObject("tripEntity", tripEntity); // Send the existing data to the view
        } else {
            modelAndView.setViewName("error"); // Handle case where trip is not found
        }
        
        return modelAndView;
    }

    // Post the updated trip details to save in the database
    @RequestMapping(value = "/update/{tripId}", method = RequestMethod.POST)
    public ModelAndView updateTrip(@PathVariable("tripId") Long tripId, @ModelAttribute TripEntity tripEntity) {
        ModelAndView modelAndView = new ModelAndView();

        // Check if the trip exists
        if (tripRepo.existsById(tripId)) {
            tripEntity.setTripId(tripId); // Ensure the correct ID is being updated
            tripRepo.save(tripEntity); // Save the updated trip entity
            modelAndView.setViewName("success"); // Redirect to success page
        } else {
            modelAndView.setViewName("error"); // If not found, show error page
        }
        
        return modelAndView;
    }
    
  
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ModelAndView deleteTrip(@RequestParam("tripId") Long tripId) {
	    tripRepo.deleteById(tripId);  // Delete the trip entity from the database using its ID
	    
	    ModelAndView modelAndView = new ModelAndView();    
	    modelAndView.setViewName("delete");  // Set a view to show a success message
	    modelAndView.addObject("message", "Trip deleted successfully");
	    
	    return modelAndView;    
	}


    
}
