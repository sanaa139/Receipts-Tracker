package dev.sana.receipts_tracker.Controller;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import dev.sana.receipts_tracker.Repository.TransactionRepository;
import dev.sana.receipts_tracker.Transaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/files/upload")
@Controller
public class FileUploadController {
    private final TransactionRepository transactionRepository;

    public FileUploadController(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @PostMapping
    public String addTransactionsToDatabase(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        List<List<String>> records = new ArrayList<>();
        Resource resource = new ClassPathResource("tempFile.csv");
        File newFile = resource.getFile();
        file.transferTo(newFile);

        char delimiter = ';';

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(newFile))
                .withCSVParser(new CSVParserBuilder().withSeparator(delimiter).build())
                .build()) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                records.add(Arrays.asList(nextLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i = 1; i < records.size(); i++){
            createTransaction(records.get(i));
        }

        return "redirect:/transactions";
    }

    private void createTransaction(List<String> line){
        String[] amountSplitted = line.get(7).split(",");
        String newAmount = amountSplitted[0] + "." + amountSplitted[1];

        int day = Integer.parseInt(line.get(0).substring(0,2));
        int month = Integer.parseInt(line.get(0).substring(3,5));
        int year = Integer.parseInt(line.get(0).substring(6,10));

        Transaction newTransaction = new Transaction(null, LocalDate.of(year, month, day), line.get(2), new BigDecimal(newAmount));
        transactionRepository.save(newTransaction);
    }
}
