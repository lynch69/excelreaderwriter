package com.vecv.whatsapptemplate.service;

import com.vecv.whatsapptemplate.request.Request;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import com.vecv.whatsapptemplate.task.WhatsAppClient;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import static com.vecv.whatsapptemplate.service.ExcelReaderService.decimalFormat;

@Service
public class ExcuteAPI {
     @Autowired
    ExcelReaderService ExcelReaderService;
     @Autowired
    WhatsAppClient WhatsAppClient;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#");

     public void test(){

         try {
             ExcelReaderService.readExcel().stream().forEach(customerDetails -> {
                 Request request = new Request();
                 System.out.println(customerDetails.getPhoneNo());
                 request.setTemplateID(String.valueOf(decimalFormat.format(customerDetails.getTempId())));
                 request.setMobileNumber(String.valueOf(decimalFormat.format(customerDetails.getPhoneNo())));
                 WhatsAppClient.sendRequest(request);
             });
         }catch (Exception e){
             System.out.println(e.getMessage());
         }
     }

}
