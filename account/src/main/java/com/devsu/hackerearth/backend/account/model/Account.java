package com.devsu.hackerearth.backend.account.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account extends Base {
    private String number;
    private String type;
    private double initialAmount;
    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions = new ArrayList<>();
}
