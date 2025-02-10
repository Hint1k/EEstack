package com.demo.eestack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private Long id;
    private String countryName;
    private String cityName;
    private String streetName;
    private String houseNumber;
    private Courier courier;

    // no address id
    public Address(String countryName, String cityName, String streetName, String houseNumber, Courier courier) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.courier = courier;
    }

    // no courier
    public Address(Long id, String countryName, String cityName, String streetName, String houseNumber) {
        this.id = id;
        this.countryName = countryName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    // no address id and no courier
    public Address(String countryName, String cityName, String streetName, String houseNumber) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }
}