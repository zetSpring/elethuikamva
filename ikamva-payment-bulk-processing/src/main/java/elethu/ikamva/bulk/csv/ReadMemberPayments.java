package elethu.ikamva.bulk.csv;

import elethu.ikamva.bulk.commons.TransactionTypes;
import elethu.ikamva.bulk.util.services.PaymentUtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class ReadMemberPayments {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadMemberPayments.class);
    private final String file = "/Statement_1178891232_14.csv"; //to remove, only for testing purposes.
    PaymentUtilService paymentUtilService;

    public void ReadCSVFile(String fileName) throws IOException {
        LOGGER.info("ServiceInvocation::ReadCSVFile");
        try {
            Reader reader =  Files.newBufferedReader(Paths.get(file));
            CSVParser csvRecords = new CSVParser(reader, CSVFormat.DEFAULT);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

            for (CSVRecord csvRecord : csvRecords) {
                Date date = formatter.parse(csvRecord.get(0));
                String reference = csvRecord.get(1);
                double amount = Double.parseDouble(csvRecord.get(2));
                double balance = Double.parseDouble(csvRecord.get(3));
                if(paymentUtilService.GetTransactionType(amount).value.equals("Credit")){
                    System.out.println(paymentUtilService.GetTransactionType(amount).value);
                    //reference = paymentUtilService.ExtractInvestID(reference);
                }

                System.out.println("Date: " + date + " Reference: " + reference + " Amount: " + amount + " Balance: " + balance);
            }
        } catch (IOException | ParseException e) {
            String msg = "There was a problem processing the payment csv file: " + fileName + " because " + e.getMessage();
            System.out.println(msg);
        }
    }
}
