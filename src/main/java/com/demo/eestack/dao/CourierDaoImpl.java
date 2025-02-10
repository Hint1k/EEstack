package com.demo.eestack.dao;

import com.demo.eestack.entity.Courier;
import jakarta.ejb.Stateless;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Slf4j
public class CourierDaoImpl implements CourierDao {

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
    public List<Courier> getCouriers() {
        List<Courier> couriers = new ArrayList<>();
        String sql = "SELECT * FROM courier";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                couriers.add(new Courier(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone")
                ));
            }
        } catch (SQLException e) {
            log.error("Error fetching couriers: {}", e.getMessage());
            throw new RuntimeException();
        }
        return couriers;
    }

    @Override
    public Courier getCourier(Long id) {
        String sql = "SELECT * FROM courier WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Courier(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("phone")
                );
            }
        } catch (SQLException e) {
            log.error("Error fetching courier: {}", e.getMessage());
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public void saveCourier(Courier courier) {
        String sql = "INSERT INTO courier (first_name, last_name, phone) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setString(3, courier.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error saving courier: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void updateCourier(Courier courier) {
        if (courier == null) {
            log.error("Courier object is null, cannot update.");
            throw new IllegalArgumentException("Courier cannot be null");
        }
        log.info("Updating courier with ID: {}, firstName: {}, lastName: {}, phone: {}",
                courier.getId(), courier.getFirstName(), courier.getLastName(), courier.getPhone());

        String sql = "UPDATE courier SET first_name = ?, last_name = ?, phone = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courier.getFirstName());
            statement.setString(2, courier.getLastName());
            statement.setString(3, courier.getPhone());
            statement.setLong(4, courier.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                log.error("No courier found with ID: {}", courier.getId());
                throw new RuntimeException("Courier not found for update");
            }
        } catch (SQLException e) {
            log.error("Error updating courier: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteCourier(Long id) {
        String sql = "DELETE FROM courier WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error deleting courier: {}", e.getMessage());
            throw new RuntimeException();
        }
    }
}