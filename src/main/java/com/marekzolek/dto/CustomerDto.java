package com.marekzolek.dto;

public class CustomerDto {

    private Long id;

    private Long pesel;

    private String name;

    private String surname;

    private Integer sumPrice;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getPesel() {
        return pesel;
    }

    public void setPesel(final Long pesel) {
        this.pesel = pesel;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(final Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}
