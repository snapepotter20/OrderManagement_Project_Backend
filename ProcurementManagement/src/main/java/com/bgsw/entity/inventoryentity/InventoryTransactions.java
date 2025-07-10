package com.bgsw.entity.inventoryentity;

import java.time.LocalDate;
import java.util.List;

import com.bgsw.entity.Product;

import jakarta.persistence.*;

@Entity
@Table(name = "INVENTORY_TRANSACTIONS")
public class InventoryTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    private int userId;

    @ManyToMany
    @JoinTable(
        name = "inventory_transaction_products",
        joinColumns = @JoinColumn(name = "transaction_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    private LocalDate transactionDate;
    private String transactionType;
    private double quantity;
    private String reference;

    // Constructors
    public InventoryTransactions() {}

    public InventoryTransactions(int transactionId, int userId, LocalDate transactionDate,
                                 String transactionType, double quantity, String reference) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.reference = reference;
    }

    // Getters and Setters

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "InventoryTransactions{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", transactionDate=" + transactionDate +
                ", transactionType='" + transactionType + '\'' +
                ", quantity=" + quantity +
                ", reference='" + reference + '\'' +
                '}';
    }
}
