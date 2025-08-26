package org.unibl.etf.clientapp.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.unibl.etf.clientapp.bean.UserBean;
import org.unibl.etf.clientapp.bean.VehicleBean;
import org.unibl.etf.clientapp.dao.*;
import org.unibl.etf.clientapp.dto.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@MultipartConfig
@WebServlet(name = "controller", value = "/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null) {
            action = "login";
        }
        switch (action) {
            case "register":
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
                break;
            case "login":
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                break;
            case "home":
                if (session.getAttribute("userBean") != null) {
                    request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Controller?action=login");
                }
                break;
            case "logout":
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("notification", "You have been successfully logged out.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                break;
            case "profile":
                UserBean userBean = (UserBean) session.getAttribute("userBean");
                long clientId = userBean.getUser().getId();
                List<Rental> rentals = RentalDAO.getRentalsFromClient(clientId);
                request.setAttribute("rentals", rentals);
                request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
                break;
            case "car-rental":
                showCarRentalPage(request, response);
                break;
            case "bike-rental":
                showBikeRentalPage(request, response);
                break;
            case "scooter-rental":
                showScooterRentalPage(request, response);
                break;
            case "show-rental-form":
                String vehicleId = request.getParameter("vehicleId");
                String vehicleType = request.getParameter("vehicleType");
                Object vehicle = null;
                double price = 0.0;
                switch (vehicleType) {
                    case "car":
                        vehicle = CarDAO.getCarById(vehicleId);
                        price = RentalPriceConfigDAO.getPriceForCar();
                        break;
                    case "ebike":
                        vehicle = EBikeDAO.getEBikeById(vehicleId);
                        price = RentalPriceConfigDAO.getPriceForEBike();
                        break;
                    case "escooter":
                        vehicle = EScooterDAO.getEScooterById(vehicleId);
                        price = RentalPriceConfigDAO.getPriceForEScooter();
                        break;
                }
                if (vehicle != null) {
                    request.setAttribute("vehicle", vehicle);
                    request.setAttribute("price", price);
                    request.setAttribute("vehicleType", vehicleType);
                    request.getRequestDispatcher("/WEB-INF/pages/rental-form.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Controller?action=vehicle-list");
                }
                break;
            case "show-active-ride":
                if (session.getAttribute("activeRental") != null) {
                    request.getRequestDispatcher("/WEB-INF/pages/active-ride.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Controller?action=home");
                }
                break;
            case "download-invoice":
                try {
                    long rentalId = Long.parseLong(request.getParameter("rentalId"));
                    InvoiceData data = RentalDAO.getInvoiceData(rentalId);

                    if (data != null) {
                        generatePdfInvoice(data, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        System.out.println("Action here in post is " + action);

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserBean user = new UserBean();
            if (user.login(username, password)) {
                session.setAttribute("userBean", user);
                response.sendRedirect("Controller?action=home");

            } else {
                session.setAttribute("notification", "Invalid credentials");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            }
        }
        else if (action.equals("register")) {
            String errorMessage = null;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String id_document = request.getParameter("id_document");
            String drivers_licence = request.getParameter("drivers_licence");
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()
                    || email == null || email.trim().isEmpty() || firstname == null || firstname.trim().isEmpty() || drivers_licence==null || drivers_licence.trim().isEmpty()) {
                errorMessage = "All fields are required.";
            } else if (UserDAO.existsByUsername(username)) {
                errorMessage = "Username taken. Change and try again.";
            }
            if (errorMessage != null) {
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect(request.getContextPath() + "/Controller?action=register");
            } else {
                User newUser = new User(firstname, lastname, username, password, "CLIENT", false, email, phone, id_document,drivers_licence);
                UserBean registrationBean = new UserBean();
                boolean registrationSuccess = registrationBean.register(newUser);
                if (registrationSuccess) {
                    session.removeAttribute("errorMessage");
                    session.setAttribute("registrationSuccess", "Account created successfully! Please login.");
                    response.sendRedirect(request.getContextPath() + "/Controller?action=login");
                } else {
                    session.setAttribute("errorMessage", "Registration failed. Please try again.");
                    response.sendRedirect(request.getContextPath() + "/Controller?action=register");

                }
            }
        }
        else if ("logout".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/Controller?action=logout");
        }
        else if ("changePassword".equals(action)) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            UserBean userBean = (UserBean) session.getAttribute("userBean");

            String statusMessage = null;
            boolean success = false;

            if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
                statusMessage = "Error: Both password fields are required.";
            }
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("profileMessage", "Error. Password does not match.");
            } else {
                success = userBean.changePassword(oldPassword, newPassword);
                if (success) {
                    statusMessage = "Success: Password changed successfully!";
                    System.out.println("Password changed for user ID: " + userBean.getId());
                } else {
                    statusMessage = "Error: Could not change password due to a server error.";
                    System.err.println("Failed to change password for user ID: " + userBean.getId());
                }
            }
            session.setAttribute("passwordChangeMessage", statusMessage);
            response.sendRedirect(request.getContextPath() + "/Controller?action=profile");
        }
        else if ("deactivateAccount".equals(action)) {
            UserBean userBean = (UserBean) session.getAttribute("userBean");
            boolean success = userBean.deactivateAccount();
            if (success) {
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("notification", "Your account has been successfully deactivated.");
                response.sendRedirect("Controller?action=login");
            } else {
                session.setAttribute("profileMessage", "Error: Account deactivation failed. Please try again.");
                response.sendRedirect("Controller?action=profile");
            }
        }
        else if ("start-ride".equals(action)) {
            UserBean userBean = (UserBean) session.getAttribute("userBean");
            String vehicleIdStr = request.getParameter("vehicleId");
            String vehicleType = request.getParameter("vehicleType");

            if (vehicleIdStr == null || vehicleType == null) {
                response.sendRedirect("Controller?action=home");
                return;
            }
            Object vehicle = null;
            switch (vehicleType) {
                case "car":
                    vehicle = CarDAO.getCarById(vehicleIdStr);
                    break;
                case "ebike":
                    vehicle = EBikeDAO.getEBikeById(vehicleIdStr);
                    break;
                case "escooter":
                    vehicle = EScooterDAO.getEScooterById(vehicleIdStr);
                    break;
            }
            if (vehicle == null) {
                session.setAttribute("notification", "Sorry, that vehicle was just rented by another user.");
                response.sendRedirect("Controller?action=home");
                return;
            }
            double startX = 0, startY = 0;
            if (vehicle instanceof Car) {
                startX = ((Car) vehicle).getMapX();
                startY = ((Car) vehicle).getMapY();
            } else if (vehicle instanceof EBike) {
                startX = ((EBike) vehicle).getMapX();
                startY = ((EBike) vehicle).getMapY();
            } else {
                startX = ((EScooter) vehicle).getMapX();
                startY = ((EScooter) vehicle).getMapY();
            }

            double[] newCoords = generateRandomCoordinates();
            VehicleDAO.setVehicleAvailabilityToRented(vehicleIdStr);
            VehicleDAO.updateVehicleCoordinates(vehicleIdStr, newCoords[0], newCoords[1]);
            Rental activeRental = new Rental();
            activeRental.setClientId(userBean.getUser().getId());
            activeRental.setVehicleId(vehicleIdStr);
            activeRental.setDateTime(java.time.LocalDateTime.now());
            activeRental.setStartX(startX);
            activeRental.setStartY(startY);
            activeRental.setEndX(newCoords[0]);
            activeRental.setEndY(newCoords[1]);
            activeRental.setVehicleType(vehicleType);
            activeRental.setActive(true);

            long rentalId = RentalDAO.startRental(activeRental);
            if (rentalId > 0) {
                activeRental.setId(rentalId);
                session.setAttribute("activeRental", activeRental);
                response.sendRedirect("Controller?action=show-active-ride");
            } else {
                VehicleDAO.setVehicleAvailabilityToAvailable(vehicleIdStr);
                session.setAttribute("notification", "Error starting ride. Please try again.");
                response.sendRedirect("Controller?action=home");
            }

        }
        else if ("end-ride".equals(action)) {
            UserBean userBean = (UserBean) session.getAttribute("userBean");
            Rental activeRental = (Rental) session.getAttribute("activeRental");

            if (userBean == null || activeRental == null) {
                response.sendRedirect("Controller?action=login");
                return;
            }
            String vehicleType = activeRental.getVehicleType();
            long rentalId = activeRental.getId();
            String vehicleId = activeRental.getVehicleId();
            int durationInSeconds = Integer.parseInt(request.getParameter("durationSeconds"));
            double pricePerMinute = RentalPriceConfigDAO.getPriceByVehicleType(vehicleType);
            double pricePerSecond = pricePerMinute / 60.0;
            double finalPrice = durationInSeconds * pricePerSecond;

            double endX = activeRental.getEndX();
            double endY = activeRental.getEndY();

            RentalDAO.endRental(rentalId, durationInSeconds, finalPrice, endX, endY);

            VehicleDAO.setVehicleAvailabilityToAvailable(vehicleId);
            session.removeAttribute("activeRental");
            session.setAttribute("notification", "Ride finished! Total price: " + String.format("%.2f", finalPrice) + " $.");
            session.setAttribute("lastRentalId", rentalId);
            response.sendRedirect("Controller?action=home");
        }
        else if ("uploadAvatar".equals(action)) {
            UserBean userBean = (UserBean) session.getAttribute("userBean");

            String uploadPath = "C:\\Users\\PC\\Desktop\\4 GODINA\\IP\\urban-motion\\avatars";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            try {
                Part filePart = request.getPart("avatar");
                long userId = userBean.getUser().getId();

                String fileName = userId + ".png";
                String filePath = uploadPath + File.separator + fileName;

                filePart.write(filePath);

                UserDAO.updateAvatarUrl(userId, fileName);

                userBean.getUser().setAvatarPicture(fileName);
                session.setAttribute("userBean", userBean);

                session.setAttribute("profileMessage", "Avatar successfully updated!");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("profileMessage", "Error uploading avatar.");
            }
            response.sendRedirect("Controller?action=profile");
        }
    }

    private void showScooterRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<EScooter> eScooters = vehicleBean.getAvailableEScooters();
            request.setAttribute("vehicles", eScooters);
            request.setAttribute("pageTitle", "Available E-Scooters");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }

    private void showCarRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<Car> cars = vehicleBean.getAvailableCars();
            request.setAttribute("vehicles", cars);
            request.setAttribute("pageTitle", "Available Cars");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }

    private void showBikeRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<EBike> eBikes = vehicleBean.getAvailableEBikes();
            request.setAttribute("vehicles", eBikes);
            request.setAttribute("pageTitle", "Available E-Bikes");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }

    private double[] generateRandomCoordinates() {
        double centerLat = 44.7725;
        double centerLon = 17.186;
        double radius = 0.02;

        double randomLat = centerLat + (Math.random() * 2 - 1) * radius;
        double randomLon = centerLon + (Math.random() * 2 - 1) * radius;

        return new double[]{randomLat, randomLon};
    }

    private void generatePdfInvoice(InvoiceData data, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"invoice_" + data.getRentalId() + ".pdf\"");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Urban Motion - Invoice").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Invoice ID: " + data.getRentalId()).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Date: " + data.getStartDateTime().toLocalDate().toString()).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        Table table = new Table(2);
        table.addCell(new Cell().add(new Paragraph("Client:").setBold()));
        table.addCell(data.getClientFirstname() + " " + data.getClientLastname());

        table.addCell(new Cell().add(new Paragraph("ID Document:").setBold()));
        table.addCell(data.getClientIdDocument());

        if ("car".equalsIgnoreCase(data.getVehicleType())) {
            table.addCell(new Cell().add(new Paragraph("Driver's license:").setBold()));
            table.addCell(data.getClientDriversLicence());
        }

        table.addCell(new Cell().add(new Paragraph("\nVehicle:").setBold()));
        table.addCell(data.getVehicleModel());

        table.addCell(new Cell().add(new Paragraph("Start time:").setBold()));
        table.addCell(data.getStartDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        table.addCell(new Cell().add(new Paragraph("Duration:").setBold()));
        table.addCell(data.getDurationSeconds() + " seconds");

        table.addCell(new Cell().add(new Paragraph("Start location (Lat, Lon):").setBold()));
        table.addCell(String.format("%.4f, %.4f", data.getStartX(), data.getStartY()));

        table.addCell(new Cell().add(new Paragraph("End location (Lat, Lon):").setBold()));
        table.addCell(String.format("%.4f, %.4f", data.getEndX(), data.getEndY()));

        Table priceTable = new Table(2).setMarginTop(20);
        priceTable.addCell(new Cell(1, 2).add(new Paragraph("Total price").setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER)));
        priceTable.addCell(new Cell().add(new Paragraph(String.format("%.2f $", data.getPrice())).setBold().setFontSize(16)));
        priceTable.addCell(new Cell().add(new Paragraph("Thank you for using our services!").setTextAlignment(TextAlignment.RIGHT)));

        document.add(table);
        document.add(priceTable);

        document.close();

        response.setContentLength(baos.size());
        OutputStream os = response.getOutputStream();
        baos.writeTo(os);
        os.flush();
        os.close();
    }
}
