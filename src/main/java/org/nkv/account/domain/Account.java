package org.nkv.account.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @JsonIgnore
    private Long id;

    private String iban;
    private String currency;
    private BigDecimal balance;
    private LocalDate update;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    @Getter(value = AccessLevel.NONE)
    private User user;
    
    @PrePersist
    void update() {
        update = LocalDate.now();
    }
}