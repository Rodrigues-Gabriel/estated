package com.properties.estated.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @Column(name = "property_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bedroom_count", nullable = false)
    private Double bedroomCount;

    @Column(name = "bathroom_count", nullable = false)
    private Double bathroomCount;

    @Column(name = "room_count", nullable = false)
    private Double roomCount;

    @Column(name = "square_footage", nullable = false)
    private Double squareFootage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="investor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Investor investor;

    public Property() {

    }

    public Property(Double bedroomCount, Double bathroomCount, Double squareFootage) {
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.squareFootage = squareFootage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCount(Double bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public Double getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(Double bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public Double getRoomCount() {
        roomCount = bedroomCount + bathroomCount;
        return roomCount;
    }

    public void setRoomCount(Double roomCount) {
        this.roomCount = roomCount;
    }

    public Double getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Double squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

}
