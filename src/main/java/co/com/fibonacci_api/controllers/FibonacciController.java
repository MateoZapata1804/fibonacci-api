package co.com.fibonacci_api.controllers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.fibonacci_api.models.FibonacciSequenceModel;
import co.com.fibonacci_api.models.http.FibonacciFromHourRequest;
import co.com.fibonacci_api.models.http.RequestResponse;
import co.com.fibonacci_api.services.FibonacciService;

@RestController
@RequestMapping("/fibonacci")
public class FibonacciController {

	@Autowired
	FibonacciService fiboSvc;
	
	@PostMapping("/saveFibonacciSeqFromHour")
	public RequestResponse getFibonacciSeqFromHour(@RequestBody FibonacciFromHourRequest hour) {
		return fiboSvc.saveFibonacciSeqFromHour(hour.getHour());
	}
	
	@GetMapping("/getAllSequences")
	public RequestResponse getAllSequences() {
		return fiboSvc.getAllSequences();
	}
	
	@GetMapping("/getSequenceById")
	public RequestResponse getSequenceById(Integer id) {
		return fiboSvc.getSequenceById(id);
	}
	
}
