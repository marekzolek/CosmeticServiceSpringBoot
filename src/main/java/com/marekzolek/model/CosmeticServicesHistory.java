package com.marekzolek.model;

import javax.persistence.*;

@Entity
public class CosmeticServicesHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CosmeticService cosmeticService;

    @ManyToOne
    private Customer customer;

    private String date;


    public CosmeticServicesHistory(CosmeticService cosmeticService, Customer customer, String date) {
        this.cosmeticService = cosmeticService;
        this.customer = customer;
        this.date = date;
    }

    public CosmeticServicesHistory() {

    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }


    public CosmeticService getCosmeticService() {
        return cosmeticService;
    }

    public void setCosmeticService(CosmeticService cosmeticService) {
        this.cosmeticService = cosmeticService;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
