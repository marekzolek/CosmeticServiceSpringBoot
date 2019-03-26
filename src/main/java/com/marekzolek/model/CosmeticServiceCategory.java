package com.marekzolek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class CosmeticServiceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<CosmeticService> cosmeticServices;

    public CosmeticServiceCategory() {
    }

    public CosmeticServiceCategory(Long id,String name, List<CosmeticService> cosmeticServices) {
        this.id = id;
        this.name = name;
        this.cosmeticServices = cosmeticServices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CosmeticService> getCosmeticServices() {
        return cosmeticServices;
    }

    public void setCosmeticServices(List<CosmeticService> cosmeticServices) {
        this.cosmeticServices = cosmeticServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CosmeticServiceCategory that = (CosmeticServiceCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
