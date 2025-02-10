package com.demo.eestack.controller;

import com.demo.eestack.dao.AddressDao;
import com.demo.eestack.dao.CourierDao;
import com.demo.eestack.entity.Address;
import com.demo.eestack.entity.Courier;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/addresses/*")
@Slf4j
public class AddressController extends HttpServlet {

    private final AddressDao addressDao;
    private final CourierDao courierDao;

    @Inject
    public AddressController(AddressDao addressDao, CourierDao courierDao) {
        this.addressDao = addressDao;
        this.courierDao = courierDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter("action");
            String pathInfo = request.getPathInfo();
            Long id = extractIdFromPath(pathInfo);
            if ("new".equals(action)) {
                showNewAddressForm(request, response);
            } else if (id != null) {
                showUpdateAddressForm(request, response, id);
            } else {
                showAddressList(request, response);
            }
        } catch (Exception e) {
            log.error("AddressController doGet(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    // Extracts ID from the path for URL-based routing
    private Long extractIdFromPath(String pathInfo) {
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                log.info("Extracted ID: {}", id);
                return id;
            } catch (NumberFormatException ignored) {
                // it means a page with no address id is requested
            }
        }
        return null;
    }

    // Show all addresses
    private void showAddressList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Address> addresses = addressDao.getAddresses();
        request.setAttribute("addresses", addresses);
        request.getRequestDispatcher("/WEB-INF/views/address-list.jsp").forward(request, response);
    }

    // Show new address form
    private void showNewAddressForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all couriers for the dropdown in the address form
        List<Courier> couriers = courierDao.getCouriers();
        request.setAttribute("couriers", couriers);
        request.setAttribute("address", new Address());
        request.getRequestDispatcher("/WEB-INF/views/new-address.jsp").forward(request, response);
    }

    // Show update address form
    private void showUpdateAddressForm(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        log.info("Looking for address with ID: {} to update", id);
        Address address = addressDao.getAddress(id);
        List<Courier> couriers = courierDao.getCouriers();
        if (address == null) {
            log.error("Address with ID {} not found", id);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Address not found");
        } else if (couriers == null || couriers.isEmpty()) {
            log.error("Couriers not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Couriers not found");
        } else {
            log.info("Address to update found: {}", address);
            // Fetch all couriers for the dropdown in the update form
            request.setAttribute("couriers", couriers);
            request.setAttribute("address", address);
            request.getRequestDispatcher("/WEB-INF/views/update-address.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String method = request.getParameter("_method");
            if (method != null && method.equalsIgnoreCase("PUT")) {
                doPut(request, response); // Forward to doPut() manually
            } else if (method != null && method.equalsIgnoreCase("DELETE")) {
                doDelete(request, response); // forward to doDelete() manually
            } else {
                String country = request.getParameter("countryName");
                String city = request.getParameter("cityName");
                String street = request.getParameter("streetName");
                String houseNumber = request.getParameter("houseNumber");
                String courierId = request.getParameter("courierId");
                Courier courier = courierDao.getCourier(Long.parseLong(courierId));
                Address address = new Address(country, city, street, houseNumber, courier);
                addressDao.saveAddress(address);
                response.sendRedirect(request.getContextPath() + "/api/addresses");
            }
        } catch (Exception e) {
            log.error("AddressController doPost(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.parseLong(request.getPathInfo().substring(1)); // Extract ID
            String country = request.getParameter("countryName");
            String city = request.getParameter("cityName");
            String street = request.getParameter("streetName");
            String houseNumber = request.getParameter("houseNumber");
            String courierId = request.getParameter("courierId");
            Courier courier = courierDao.getCourier(Long.parseLong(courierId));
            Address address = new Address(id, country, city, street, houseNumber, courier);
            log.info("Address to update: {}", address);
            addressDao.updateAddress(address);
            response.sendRedirect(request.getContextPath() + "/api/addresses");
        } catch (Exception e) {
            log.error("CourierController doPut(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.parseLong(request.getPathInfo().substring(1)); // Extract ID
            addressDao.deleteAddress(id);
            response.sendRedirect(request.getContextPath() + "/api/addresses");
        } catch (Exception e) {
            log.error("AddressController doDelete(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage) {
        try {
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            log.error("Error forwarding to error page: {}", ex.getMessage());
        }
    }
}