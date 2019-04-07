package com.marekzolek.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@NamedQueries(value = {@NamedQuery(name = "CosmeticServiceCategory.countNumberOfCosmeticServicesCostGivenPrice",
        query = "SELECT count(s.price) from CosmeticServiceCategory c join c.cosmeticServices s where s.price=:price")})
@Entity
public class CosmeticServiceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "category")
    private List<CosmeticService> cosmeticServices;

    public static class CosmeticServiceCategoryBuilder{

        private Long id;
        private String name;
        private List<CosmeticService> cosmeticServices;

        public CosmeticServiceCategoryBuilder id(final Long id){
            this.id = id;
            return this;
        }

        public CosmeticServiceCategoryBuilder name(final String name){
            this.name = name;
            return this;
        }

        public CosmeticServiceCategoryBuilder cosmeticServices(final List<CosmeticService> cosmeticServices){
            this.cosmeticServices = cosmeticServices;
            return this;
        }

        public CosmeticServiceCategory build(){
            CosmeticServiceCategory cosmeticServiceCategory = new CosmeticServiceCategory();
            cosmeticServiceCategory.id = this.id;
            cosmeticServiceCategory.name = this.name;
            cosmeticServiceCategory.cosmeticServices = this.cosmeticServices;
            return cosmeticServiceCategory;
        }
    }

    public CosmeticServiceCategory() {
    }

    public CosmeticServiceCategory(final Long id, final String name, final List<CosmeticService> cosmeticServices) {
        this.id = id;
        this.name = name;
        this.cosmeticServices = cosmeticServices;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<CosmeticService> getCosmeticServices() {
        return cosmeticServices;
    }

    public void setCosmeticServices(final List<CosmeticService> cosmeticServices) {
        this.cosmeticServices = cosmeticServices;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CosmeticServiceCategory that = (CosmeticServiceCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
