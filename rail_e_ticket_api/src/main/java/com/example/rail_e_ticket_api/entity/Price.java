package com.example.rail_e_ticket_api.entity;

import com.example.rail_e_ticket_api.entity.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price")
public class Price extends BaseEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_station_id", referencedColumnName = "id")
    private Station fromStation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_station_id", referencedColumnName = "id")
    private Station toStation;

    @Column(name = "amount", nullable = false)
    private double amount;
}