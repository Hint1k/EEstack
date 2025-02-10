package com.demo.eestack.dao;

import com.demo.eestack.entity.Address;
import com.demo.eestack.entity.Courier;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Slf4j
public class AddressDaoImpl implements AddressDao {

    private static final String URL = "jdbc:postgresql://postgres_db:5432/eestack?currentSchema=eestack";
    private static final String USER = "user";
    private static final String PASSWORD = "123";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("PostgresQL JDBC Driver not found: {}", e.getMessage());
            throw new RuntimeException("Failed to load PostgreSQL JDBC Driver", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Address> getAddresses() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT a.id, a.country_name, a.city_name, a.street_name, a.house_number, " +
                "c.id AS courier_id, c.first_name, c.last_name, c.phone " +
                "FROM address a LEFT JOIN courier c ON a.courier_id = c.id";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Courier courier = new Courier(
                        resultSet.getLong("courier_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone")
                );
                Address address = new Address(
                        resultSet.getLong("id"),
                        resultSet.getString("country_name"),
                        resultSet.getString("city_name"),
                        resultSet.getString("street_name"),
                        resultSet.getString("house_number"),
                        courier
                );
                addresses.add(address);
            }
        } catch (SQLException e) {
            log.error("Error fetching addresses: {}", e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        return addresses;
    }

    @Override
    public Address getAddress(Long id) {
        String sql = "SELECT a.id, a.country_name, a.city_name, a.street_name, a.house_number, " +
                "c.id AS courier_id, c.first_name, c.last_name, c.phone " +
                "FROM address a JOIN courier c ON a.courier_id = c.id WHERE a.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Courier courier = new Courier(
                        resultSet.getLong("courier_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone")
                );

                return new Address(
                        resultSet.getLong("id"),
                        resultSet.getString("country_name"),
                        resultSet.getString("city_name"),
                        resultSet.getString("street_name"),
                        resultSet.getString("house_number"),
                        courier
                );
            }
        } catch (SQLException e) {
            log.error("Error fetching address: {}", e.getMessage());
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public boolean isCourierAssigned(Long courierId) {
        String sql = "SELECT 1 FROM address WHERE courier_id = ? LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, courierId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a record is found, false otherwise
        } catch (SQLException e) {
            log.error("Error checking if courier is assigned: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAddress(Address address) {
        String sql = "INSERT INTO address (country_name, city_name, street_name, " +
                "house_number, courier_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, address.getCountryName());
            statement.setString(2, address.getCityName());
            statement.setString(3, address.getStreetName());
            statement.setString(4, address.getHouseNumber());
            statement.setLong(5, address.getCourier().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error saving address: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void updateAddress(Address address) {
        String sql = "UPDATE address SET country_name = ?, city_name = ?, street_name = ?, " +
                "house_number = ?, courier_id = ? WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, address.getCountryName());
            statement.setString(2, address.getCityName());
            statement.setString(3, address.getStreetName());
            statement.setString(4, address.getHouseNumber());
            statement.setLong(5, address.getCourier().getId());
            statement.setLong(6, address.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error updating address: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteAddress(Long id) {
        String sql = "DELETE FROM address WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error deleting address: {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}