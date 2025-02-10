package com.demo.eestack.controller;

import com.demo.eestack.dao.AddressDao;
import com.demo.eestack.dao.CourierDao;
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

@WebServlet("/api/couriers/*")
@Slf4j
public class CourierController extends HttpServlet {

    private final CourierDao courierDao;
    private final AddressDao addressDao;

    @Inject
    public CourierController(CourierDao courierDao, AddressDao addressDao) {
        this.courierDao = courierDao;
        this.addressDao = addressDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter("action");
            String pathInfo = request.getPathInfo();
            Long id = extractIdFromPath(pathInfo);
            if ("new".equals(action)) {
                showNewCourierForm(request, response);
            } else if (id != null) {
                showUpdateCourierForm(request, response, id);
            } else {
                showCourierList(request, response);
            }
        } catch (Exception e) {
            log.error("CourierController doGet(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    // Extracts ID from the path
    private Long extractIdFromPath(String pathInfo) {
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                log.info("Extracted ID: {}", id);
                return id;
            } catch (NumberFormatException ignored) {
                // it means a page with no courier id is requested
            }
        }
        return null;
    }

    // Show all couriers
    private void showCourierList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Courier> couriers = courierDao.getCouriers();
        request.setAttribute("couriers", couriers);
        request.getRequestDispatcher("/WEB-INF/views/courier-list.jsp").forward(request, response);
    }

    // Show new courier form
    private void showNewCourierForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("courier", new Courier());
        request.getRequestDispatcher("/WEB-INF/views/new-courier.jsp").forward(request, response);
    }

    // Show update courier form
    private void showUpdateCourierForm(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        log.info("Looking for courier with ID: {} to update", id);
        Courier courier = courierDao.getCourier(id);
        log.info("Courier to update with id: {}, First name: {}, Last name: {}, Phone number: {}",
                courier.getId(), courier.getFirstName(), courier.getLastName(), courier.getPhone());
        if (courier != null) {
            log.info("Courier to update found: {}", courier);
            request.setAttribute("courier", courier);
            request.getRequestDispatcher("/WEB-INF/views/update-courier.jsp").forward(request, response);
        } else {
            log.error("Courier with ID {} not found", id);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Courier not found");
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
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phone = request.getParameter("phone");
                log.info("Courier to create: first Name: {}, Last name: {}, phone: {}", firstName, lastName, phone);
                Courier courier = new Courier(null, firstName, lastName, phone);
                courierDao.saveCourier(courier);
                response.sendRedirect(request.getContextPath() + "/api/couriers");
            }
        } catch (Exception e) {
            log.error("CourierController doPost(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.parseLong(request.getPathInfo().substring(1)); // Extract ID
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String phone = request.getParameter("phone");
            Courier courier = new Courier(id, firstName, lastName, phone);
            log.info("Id: {} , First Name: {}, Last Name: {}, Phone: {}", id, firstName, lastName, phone);
            courierDao.updateCourier(courier);
            response.sendRedirect(request.getContextPath() + "/api/couriers");
        } catch (Exception e) {
            log.error("CourierController doPut(): {}", e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long id = Long.parseLong(request.getPathInfo().substring(1)); // Extract ID
            if (addressDao.isCourierAssigned(id)) {
                throw new RuntimeException("Cannot delete courier because it is assigned to one or more addresses.");
            }
            courierDao.deleteCourier(id);
            response.sendRedirect(request.getContextPath() + "/api/couriers");
        } catch (Exception e) {
            log.error("CourierController doDelete(): {}", e.getMessage());
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