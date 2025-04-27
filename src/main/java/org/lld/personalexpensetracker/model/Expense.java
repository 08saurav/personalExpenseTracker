package org.lld.personalexpensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//@EqualsAndHashCode(callSuper = true)
//@Data
@Entity
@Table(name = "expenses")
@Getter
@Setter
public class Expense extends BaseModel {
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}