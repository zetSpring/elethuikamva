package elethu.ikamva.bulk.csv;

import elethu.ikamva.bulk.util.services.PaymentUtilService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
//import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class ReadMemberPayments {

    //private static String fileLocation = "classpath:statements/Statement_1178891232_14.csv";
    PaymentUtilService paymentUtilService;

    public void ReadCSVFile(String file) throws IOException {
        try {
            Reader reader =  Files.newBufferedReader(Paths.get(file));
            CSVParser csvRecords = new CSVParser(reader, CSVFormat.DEFAULT);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

            for (CSVRecord csvRecord : csvRecords) {
                Date date = formatter.parse(csvRecord.get(0));
                String reference = csvRecord.get(1);
                double amount = Double.parseDouble(csvRecord.get(2));
                double balance = Double.parseDouble(csvRecord.get(3));
                String investmentID = paymentUtilService.ExtractInvestID(reference);
                System.out.println("Date: " + date + " Reference: " + reference + " Amount: " + amount + " Balance: " + balance);
            }

        } catch (IOException | ParseException e) {
            System.out.println("could not find a file"+ e.getLocalizedMessage());
        }
    }
}
