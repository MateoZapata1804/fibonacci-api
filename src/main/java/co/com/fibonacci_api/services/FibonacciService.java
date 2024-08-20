package co.com.fibonacci_api.services;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.fibonacci_api.models.FibonacciSequenceModel;
import co.com.fibonacci_api.models.http.RequestResponse;
import co.com.fibonacci_api.repositories.FibonacciRepository;

@Service
public class FibonacciService {

	@Autowired
	FibonacciRepository fibonacciRepo;
	
	public RequestResponse getAllSequences() {
		try {
			ArrayList<FibonacciSequenceModel> result = (ArrayList<FibonacciSequenceModel>) fibonacciRepo.findAll();
			
			return new RequestResponse(true, "Operacion exitosa mi papá", "", result);
		} catch (Exception e) {
			return new RequestResponse(false, "Ocurrió un error al consultar las secuencias", "", e.toString());
		}
	}
	
	public RequestResponse getSequenceById(Integer id) {
		try {
			FibonacciSequenceModel result = fibonacciRepo.findById(id.longValue()).orElseThrow();
			
			return new RequestResponse(true, "Operacion exitosa mi papá", "", result);
		} catch (NoSuchElementException e) {
			return new RequestResponse(false, "No se encontró el registro por ID " + id, "", e.toString());
		} catch (Exception e) {
			return new RequestResponse(false, "Ocurrió un error al consultar las secuencias", "", e.toString());
		}
	}
	
	public RequestResponse saveFibonacciSeqFromHour(String hour) {
		
		Pattern pattern = Pattern.compile("\\d\\d:\\d\\d:\\d\\d");
		
		if (!pattern.matcher(hour).find())
			return new RequestResponse(false, "Formato de hora incorrecta, este debe ser {hh:mm:ss}", "", hour);
		
		try {
			String[] splitTime = hour.split(":");
			Integer minutes = Integer.parseInt(splitTime[1]);
			Integer seconds = Integer.parseInt(splitTime[2]);
			
			Integer actualNumber = 0;
			
			ArrayList<Integer> fibonacciSequence = new ArrayList<Integer>();
			if (minutes < 10) fibonacciSequence.add(0);
			
			for (Character digit : minutes.toString().toCharArray()) {
				fibonacciSequence.add(Integer.parseInt(digit.toString()));
			}
			
			for (int i = 0; i < seconds; i++) {
				
				int arrayLength = fibonacciSequence.toArray().length;
				
				actualNumber = fibonacciSequence.get(arrayLength - 2) + fibonacciSequence.get(arrayLength - 1);
				
				fibonacciSequence.add(actualNumber);
			}
			
			FibonacciSequenceModel newEntity = new FibonacciSequenceModel();
			String finalString = "";
			for (Integer number : fibonacciSequence) {
				finalString += number.toString() + ",";
			}
			
			newEntity.setSequence(finalString.substring(0, finalString.length() - 1));
			newEntity.setGeneratedAt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			newEntity.setMailSent(false);
			
			FibonacciSequenceModel result = fibonacciRepo.save(newEntity);
			
			return new RequestResponse(true, "Secuencia calculada y guardada en la base de datos", "", result);
			
		} catch (Exception e) {
			return new RequestResponse(false, "Ocurrió un error", "", e.toString());
		}
		
	}
}
