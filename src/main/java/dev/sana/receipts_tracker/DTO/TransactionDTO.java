package dev.sana.receipts_tracker.DTO;

import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {
    private Integer id;
    private LocalDate date;
    private String recipient;
    private BigDecimal price;
    private Boolean hasBill;

    public TransactionDTO(Integer id, LocalDate date, String recipient, BigDecimal price, Boolean hasBill) {
        this.id = id;
        this.date = date;
        this.recipient = recipient;
        this.price = price;
        this.hasBill = hasBill;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date + '\'' +
                ", recipient=" + recipient + '\'' +
                ", amount=" + price + '\'' +
                ", hasBill=" + hasBill + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRecipient() {
        return recipient;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public Boolean getHasBill(){
        return hasBill;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setHasBill(Boolean hasBill) {
        this.hasBill = hasBill;
    }
}
