package dev.sana.receipts_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)

@SpringBootApplication
public class TransactionsTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsTrackerApplication.class, args);
    }

}
