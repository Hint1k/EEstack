package com.demo.eestack.dao;

import com.demo.eestack.entity.Courier;

import java.util.List;

public interface CourierDao {

    List<Courier> getCouriers();

    Courier getCourier(Long id);

    void saveCourier(Courier courier);

    void updateCourier(Courier courier);

    void deleteCourier(Long id);
}