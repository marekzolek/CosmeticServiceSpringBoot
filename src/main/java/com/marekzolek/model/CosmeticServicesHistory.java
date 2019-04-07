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

    public static class CosmeticServicesHistoryBuilder{

        private Long id;
        private CosmeticService cosmeticService;
        private Customer customer;
        private String date;

        public CosmeticServicesHistoryBuilder id(final Long id){
            this.id = id;
            return this;
        }

        public CosmeticServicesHistoryBuilder cosmeticService(final CosmeticService cosmeticService){
            this.cosmeticService = cosmeticService;
            return this;
        }

        public CosmeticServicesHistoryBuilder customer(final Customer customer){
            this.customer = customer;
            return this;
        }

        public CosmeticServicesHistoryBuilder date(final String date){
            this.date = date;
            return this;
        }

        public CosmeticServicesHistory build(){
            CosmeticServicesHistory cosmeticServicesHistory = new CosmeticServicesHistory();
            cosmeticServicesHistory.id = this.id;
            cosmeticServicesHistory.cosmeticService = this.cosmeticService;
            cosmeticServicesHistory.customer = this.customer;
            cosmeticServicesHistory.date = this.date;
            return cosmeticServicesHistory;
        }
    }


    public CosmeticServicesHistory(final CosmeticService cosmeticService, final Customer customer, final String date) {
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

    public void setCosmeticService(final CosmeticService cosmeticService) {
        this.cosmeticService = cosmeticService;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public void setDate(final String date) {
        this.date = date;
    }
}
