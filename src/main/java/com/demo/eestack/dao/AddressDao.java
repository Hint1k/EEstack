package com.demo.eestack.dao;

import com.demo.eestack.entity.Address;

import java.util.List;

public interface AddressDao {

    List<Address> getAddresses();

    Address getAddress(Long id);

    void saveAddress(Address address);

    void updateAddress(Address address);

    void deleteAddress(Long id);

    boolean isCourierAssigned(Long courierId);
}