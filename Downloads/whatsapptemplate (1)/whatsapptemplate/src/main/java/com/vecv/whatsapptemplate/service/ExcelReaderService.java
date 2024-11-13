package com.vecv.whatsapptemplate.service;

import com.vecv.whatsapptemplate.request.CustomerDetails;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vecv.whatsapptemplate.request.CustomerDetails;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vecv.whatsapptemplate.request.CustomerDetails;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelReaderService {

    static final DecimalFormat decimalFormat = new DecimalFormat("#");

    public List<CustomerDetails> readExcel() throws IOException {
        List<CustomerDetails> customerDetailsList = new ArrayList<>();
        String filePath = "C:\\Users\\rmadan\\Desktop\\whatsapptemp\\whatsapptemp.xlsx";

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            // Skip the header row if it exists
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                System.out.println("Ritik");
                Row row = rowIterator.next();
                CustomerDetails cdetails = new CustomerDetails();

                Cell tempIdCell = row.getCell(0); // Temp ID is in the first column
                Cell phoneNoCell = row.getCell(1); // Phone number is in the third column

                // Handle TempId
                if (tempIdCell != null) {
                    switch (tempIdCell.getCellType()) {
                        case STRING:
                            cdetails.setTempId(Double.valueOf(tempIdCell.getStringCellValue()));
                            break;
                        case NUMERIC:
                            cdetails.setTempId(tempIdCell.getNumericCellValue());
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected cell type for TempId");
                    }
                }

                // Handle PhoneNo
                if (phoneNoCell != null) {
                    switch (phoneNoCell.getCellType()) {
                        case STRING:
                            try {
                                cdetails.setPhoneNo(Double.valueOf(phoneNoCell.getStringCellValue()));
                            } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Invalid phone number format in cell with STRING type", e);
                            }
                            break;
                        case NUMERIC:
                            cdetails.setPhoneNo(phoneNoCell.getNumericCellValue());
                            break;
                        case BLANK:
                            // Optionally handle a blank cell, for example, by setting a default or skipping it
                            cdetails.setPhoneNo(null); // Or handle as per your business logic
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected cell type for PhoneNo: " + phoneNoCell.getCellType());
                    }
                }


                customerDetailsList.add(cdetails);
            }
        }

        return customerDetailsList;
    }

    public static void main(String[] args) {
        ExcelReaderService excelReaderService = new ExcelReaderService();
        try {
            List<CustomerDetails> customerDetailsList = excelReaderService.readExcel();
            System.out.println(customerDetailsList.size());
            customerDetailsList.forEach(customerDetails -> {
                // Format the numeric values as whole numbers
                if (customerDetails.getTempId() != null) {
                    System.out.println("TempId: " + decimalFormat.format(customerDetails.getTempId()));
                } else {
                    System.out.println("TempId: null");
                }

                if (customerDetails.getPhoneNo() != null) {
                    System.out.println("PhoneNo: " + decimalFormat.format(customerDetails.getPhoneNo()));
                } else {
                    System.out.println("PhoneNo: null");
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


