package co.com.fibonacci_api.services;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.fibonacci_api.DTO.MailDTO;
import co.com.fibonacci_api.models.FibonacciSequenceModel;
import co.com.fibonacci_api.models.MailAddressModel;
import co.com.fibonacci_api.models.http.RequestResponse;
import co.com.fibonacci_api.repositories.FibonacciRepository;

@Service
public class FibonacciService {

	@Autowired
	FibonacciRepository fibonacciRepo;
	@Autowired
	MailService mailSvc;
	
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
	
	public RequestResponse saveFibonacciSeqFromHour(String hour, Boolean sendMail) {
		
		Pattern pattern = Pattern.compile("^\\d\\d:\\d\\d:\\d\\d$");
		StringBuilder outputMsg = new StringBuilder();
		
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
			
			Collections.sort(fibonacciSequence, Collections.reverseOrder());
			
			FibonacciSequenceModel newEntity = new FibonacciSequenceModel();
			String finalString = "";
			for (Integer number : fibonacciSequence) {
				finalString += number.toString() + ",";
			}
			
			newEntity.setSequence(finalString.substring(0, finalString.length() - 1));
			newEntity.setGeneratedAt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			newEntity.setMailSent(false);
			
			FibonacciSequenceModel result = fibonacciRepo.save(newEntity); // Guardamos registro en BD
			
			outputMsg.append("Secuencia calculada y guardada en la base de datos");
			
			if (sendMail) {
				var sendMailResp = sendUnsentMail(result.getId().intValue());
				
				if (sendMailResp.getSuccess())
					outputMsg.append(", correo enviado exitosamente!");
			}
			
			return new RequestResponse(true, outputMsg.toString(), "", result);
			
		} catch (Exception e) {
			return new RequestResponse(false, "Ocurrió un error", "", e.toString());
		}
		
	}
	
	public RequestResponse sendUnsentMail(Integer id) {
		try {
			
			FibonacciSequenceModel result = this.fibonacciRepo.findById(id.longValue()).orElseThrow();
			
			if (result.getMailSent())
				return new RequestResponse(false, "El correo ya se encuentra enviado", "", result);
			
			List<String> parametrizedMails = mailSvc.getParametrizedMailAddresses(); // Buscamos los correos destinatarios habilitados
			
			if (parametrizedMails.isEmpty())
				return new RequestResponse(false, "No se encontraron correos parametrizados disponibles", "", result);
			
			StringBuilder content = new StringBuilder();
			content.append("<h3>¡Cordial saludo!</h3>");
			content.append("<p>A continuación, los resultados del cálculo de la secuencia fibonacci:</p>");
			content.append("<b>Hora de Generación</b>: " + result.getGeneratedAt() + "<br>");
			content.append("<b>Resultado de la Secuencia: </b>" + result.getSequence() + "<br><br>");
			content.append("<i>Prueba técnica - Mateo Zapata Pérez </i> ;)");
			
			MailDTO mailDTO = new MailDTO();
			mailDTO.setTo(String.join(";", parametrizedMails));
			mailDTO.setSubject("Resultados Secuencia Fibonacci (prueba-desarrollador)");
			mailDTO.setContent(content.toString());
			
			var response = mailSvc.sendMail(mailDTO);
			
			if (response.getSuccess()) {
				result.setMailSent(true); // Si el correo fue enviado exitosamente, actualizamos estado!
				fibonacciRepo.save(result);
			}
			
			return response;
			
		} catch (NoSuchElementException e) {
			return new RequestResponse(false, "No se encontró el registro por ID " + id, "", e.toString());
		} catch (Exception e) {
			return new RequestResponse(false, "Error al procesar envío de correo", "", e.toString());
		}
	}
}
