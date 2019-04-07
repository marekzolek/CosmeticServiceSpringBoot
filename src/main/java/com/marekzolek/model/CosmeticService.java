package com.marekzolek.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CosmeticService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private String type;

    @ManyToOne
    private CosmeticServiceCategory category;

    @OneToMany(mappedBy = "cosmeticService")
    private List<CosmeticServicesHistory> history = new ArrayList<>();

    public static class CosmeticServiceBuilder {

        private Long id;
        private String name;
        private Integer price;
        private String type;
        private CosmeticServiceCategory category;
        private List<CosmeticServicesHistory> history = new ArrayList<>();

        public CosmeticServiceBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public CosmeticServiceBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public CosmeticServiceBuilder price(final Integer price) {
            this.price = price;
            return this;
        }

        public CosmeticServiceBuilder type(final String type) {
            this.type = type;
            return this;
        }

        public CosmeticServiceBuilder category(final CosmeticServiceCategory category) {
            this.category = category;
            return this;
        }

        public CosmeticServiceBuilder history(final List<CosmeticServicesHistory> history) {
            this.history = history;
            return this;
        }

        public CosmeticService build() {
            CosmeticService cosmeticService = new CosmeticService();
            cosmeticService.id = this.id;
            cosmeticService.name = this.name;
            cosmeticService.price = this.price;
            cosmeticService.type = this.type;
            cosmeticService.category = this.category;
            cosmeticService.history = this.history;
            return cosmeticService;
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public CosmeticServiceCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CosmeticService that = (CosmeticService) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (price != null ? !price.equals(that.price) : that.price != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
