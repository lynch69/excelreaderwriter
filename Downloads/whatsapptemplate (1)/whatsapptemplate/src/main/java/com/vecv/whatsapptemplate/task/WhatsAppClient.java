package com.vecv.whatsapptemplate.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vecv.whatsapptemplate.request.CustomerDetails;
import com.vecv.whatsapptemplate.request.Request;
import com.vecv.whatsapptemplate.response.ResponseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
@Service
public class WhatsAppClient {

    static int counter = 1;


    private  WebClient webClient;
    public WhatsAppClient() {
        System.out.println("yogesh");
        this.webClient = WebClient.builder()
                .baseUrl("https://whatsapp.vecv.net") // Base URL
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public void sendRequest(Request requestBody) {
        ObjectMapper obj = new ObjectMapper();
        try{
            System.out.println(obj.writeValueAsString(requestBody));
        }catch(Exception e){

        }
        System.out.println(requestBody.toString());
        List<ResponseDetails> response = webClient.post()
                .uri("/bot/api/other/feedbackSAPforRetailLost")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(ResponseDetails.class)
                .collectList().log().block();

            System.out.println(response.get(0).getMessage());
            List<ResponseDetails> failedResponses = response.stream()
                    .filter(res -> "False".equalsIgnoreCase(res.getStatus())) // Collect the failed responses
                    .toList();

            List<ResponseDetails> successfulResponses = response.stream()
                    .filter(res -> "True".equalsIgnoreCase(res.getStatus())) // Collect the successful responses
                    .toList();
            System.out.println(successfulResponses.size());

            saveResponsesToExcel(successfulResponses, failedResponses, "responses.xlsx",requestBody);

//            saveResponsesToExcelChad(successfulResponses, failedResponses, "responses.xlsx",requestBody, successSheet, failedSheet, workbook);
            String excelFilePath = Paths.get("responses.xlsx").toAbsolutePath().toString();
            System.out.println(excelFilePath);
    }

    private void saveResponsesToExcel(List<ResponseDetails> successfulResponses, List<ResponseDetails> failedResponses, String fileName,Request request) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet successSheet = workbook.createSheet("Success");
            createResponseSheet(successfulResponses, successSheet,request);
            Sheet failedSheet = workbook.createSheet("Failed");
            createResponseSheet(failedResponses, failedSheet,request);
            try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
                workbook.write(fileOut);
            }
            System.out.println("Saved to " + fileName);

        } catch (IOException e) {
            System.err.println("Error saving to Excel: " + e.getMessage());
        }
    }

    private void createResponseSheet(List<ResponseDetails> responses, Sheet sheet,Request request) {

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("TempleteId");
        headerRow.createCell(1).setCellValue("PhoneNo");
        headerRow.createCell(2).setCellValue("Status");
        headerRow.createCell(3).setCellValue("CreatedOn");


        // Add responses to the sheet
        for (int i = 0; i < responses.size(); i++) {
            System.out.println(responses.size());
            ResponseDetails response = responses.get(i);
            Row row = sheet.createRow(counter++);
            row.createCell(0).setCellValue(request.getTemplateID());
            row.createCell(1).setCellValue(request.getMobileNumber());
            row.createCell(2).setCellValue(response.getStatus());
            row.createCell(3).setCellValue(getCurrentTimeStamp());

        }
    }
    public static String getCurrentTimeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:dd:MM:HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

}

