package com.marekzolek.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long pesel;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @OneToMany(mappedBy = "customer")
    private List<CosmeticServicesHistory> history = new ArrayList<>();

    public static class CustomerBuilder{

        private Long id;
        private Long pesel;
        private String name;
        private String surname;
        private List<CosmeticServicesHistory> history = new ArrayList<>();

        public CustomerBuilder id(final Long id){
            this.id = id;
            return this;
        }

        public CustomerBuilder pesel (final Long pesel){
            this.pesel = pesel;
            return this;
        }

        public CustomerBuilder name(final String name){
            this.name = name;
            return this;
        }

        public CustomerBuilder surname(final String surname){
            this.surname = surname;
            return this;
        }

        public CustomerBuilder history(final List<CosmeticServicesHistory> history){
            this.history = history;
            return this;
        }

        public Customer build(){
            Customer customer = new Customer();
            customer.id = this.id;
            customer.pesel = this.pesel;
            customer.name = this.name;
            customer.surname = this.surname;
            customer.history = this.history;
            return customer;
        }
    }


    public Customer(final Long pesel, final String name, final String surname) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
    }

    public Customer() {
    }

    public Long getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getId() {
        return id;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Customer customer = (Customer) o;

        if (pesel != null ? !pesel.equals(customer.pesel) : customer.pesel != null) {
            return false;
        }
        if (name != null ? !name.equals(customer.name) : customer.name != null) {
            return false;
        }
        return surname != null ? surname.equals(customer.surname) : customer.surname == null;
    }

    @Override
    public int hashCode() {
        int result = pesel != null ? pesel.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
