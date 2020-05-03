package elethu.ikamva.util.excel;


import elethu.ikamva.services.PaymentService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Component
public class ReadMemberPayments {

    private final PaymentService payments;
    FileInputStream excelFile;
    Workbook workbook;
    Sheet dataTypeSheet;
    Iterator<Row> iterator;

    public ReadMemberPayments(PaymentService payments) {
        this.payments = payments;
    }

    void ReadPaymentFile(String fileName){
        try{
            excelFile = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(excelFile);
            dataTypeSheet = workbook.getSheetAt(0);
            iterator = dataTypeSheet.iterator();

            while(iterator.hasNext()){

            }

        } catch (FileNotFoundException e)
        {

        } catch (IOException io){

        }

    }
}
