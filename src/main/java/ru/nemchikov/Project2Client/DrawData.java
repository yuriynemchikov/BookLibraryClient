package ru.nemchikov.Project2Client;

import org.springframework.boot.SpringApplication;
import org.springframework.web.client.RestTemplate;
import ru.nemchikov.Project2Client.dto.MeasurementDTO;
import ru.nemchikov.Project2Client.dto.MeasurementResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DrawData {

	public static void main(String[] args) {
		List<Double> measurements = getTemperaturesFromServer();
	}

	public static List<Double> getTemperaturesFromServer(){
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/measurements";

		MeasurementResponse response = restTemplate.getForObject(url, MeasurementResponse.class);

		if(response == null || response.getMeasurements() == null){
			return Collections.emptyList();
		}
		else{
			return response.getMeasurements().stream().map(MeasurementDTO::getValue)
					.collect(Collectors.toList());
		}
	}
}
