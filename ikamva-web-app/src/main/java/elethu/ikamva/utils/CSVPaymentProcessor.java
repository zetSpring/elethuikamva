package elethu.ikamva.utils;

import elethu.ikamva.commons.DateFormatter;
import elethu.ikamva.domain.Payment;
import elethu.ikamva.exception.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CSVPaymentProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVPaymentProcessor.class);
    public static String FILETYPE = "text/csv";

    public static boolean isCSVFormat(MultipartFile file) {
        return FILETYPE.equals(file.getContentType());
    }

    public static List<Payment> BulkCSVFileProcessing(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader("Transaction Date", "Reference No", "Amount", "Balance"));) {

            return csvParser.getRecords().stream()
                    .filter(d -> Double.parseDouble(d.get(2)) > 15.0)
                    .map(d -> new Payment(Double.parseDouble(d.get(2)), InvestmentIdExtractor.ExtractInvestID(d.get(1)), DateFormatter.csvDateExtract(d.get(0)), d.get(1)))
                    .sorted(Comparator.comparing(Payment::getInvestmentId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("There was a problem creating the data ");
            throw new PaymentException("There was a problem ");
        }
    }
}
