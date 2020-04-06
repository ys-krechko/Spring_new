package com.it.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Table Insurance representation
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "insurance_type", unique = true, nullable = false)
    @NotNull(message = "{insurance.type.notNull}")
    @NotEmpty(message = "{insurance.type.notEmpty}")
    @Size(min = 3, max = 50, message = "{insurance.type.size}")
    private String insuranceType;

    @Column(name = "insurance_price", nullable = false)
    @NotNull(message = "{insurance.price.notNull}")
    private Double insurancePrice;

    @OneToMany(mappedBy = "insurance")
    private Set<OrderList> orderList;

    public Insurance(Long id, String insuranceType, Double insurancePrice) {
        this.id = id;
        this.insuranceType = insuranceType;
        this.insurancePrice = insurancePrice;
    }

    public Insurance(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", insuranceType='" + insuranceType + '\'' +
                ", insurancePrice=" + insurancePrice +
                '}';
    }
}
