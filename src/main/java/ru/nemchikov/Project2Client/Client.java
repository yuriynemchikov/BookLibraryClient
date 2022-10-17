package ru.nemchikov.Project2Client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {

    public static void main(String[] args) {
        final String sensorName = "Sensor12";

        // registerSensor(sensorName);

        Random random = new Random();

        double minTemperature = 0.00;
        double maxTemperature = 45.00;

        for(int i = 0; i < 100; i++){
            System.out.println(i);
            sendMeasurement(random.nextDouble()*maxTemperature,
                    random.nextBoolean(), sensorName);
        }
    }

    public static void registerSensor(String sensorName){
        final String url = "http://localhost:8080/sensors/registration";

        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("name", sensorName);

        makePostRequestWithJsonData(url, jsonData);
    }

    public static void sendMeasurement(double value, boolean raining, String sensorName){
        final String url = "http://localhost:8080/measurements/add";

        Map<String, Object> jsonData = new HashMap<>();

        jsonData.put("value", value);
        jsonData.put("raining", raining);
        jsonData.put("sensor", Map.of("name", sensorName));

        makePostRequestWithJsonData(url, jsonData);
    }

    public static void makePostRequestWithJsonData(String url, Map<String, Object> jsonData){
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(jsonData, headers);

        try{
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Отправлено");
        }
        catch (HttpClientErrorException e){
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
