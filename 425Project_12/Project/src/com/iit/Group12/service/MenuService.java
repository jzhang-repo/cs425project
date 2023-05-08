package com.iit.Group12.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.iit.Group12.dto.Result;
import com.iit.Group12.entity.AllUser;
import com.iit.Group12.entity.Apartment;
import com.iit.Group12.entity.Booking;
import com.iit.Group12.entity.CommercialBuilding;
import com.iit.Group12.entity.House;
import com.iit.Group12.entity.Payment;
import com.iit.Group12.entity.Property;
//import com.iit.Group12.entity.VacationHome;

public class MenuService {

    private static MenuService _instance = new MenuService();

    private Scanner scanner = new Scanner(System.in);
    private AgentService renterService = AgentService.getInstance();
    private AgentService agentService = AgentService.getInstance();
    private PropertyService propertyService = PropertyService.getInstance();
    private BookingService bookingService = BookingService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance();
   

    private MenuService() {

    }

    public static MenuService getInstance() {
        return _instance;
    }

    public void init() throws SQLException {
        // main menu
        System.out.println("1. Log in");
        System.out.println("2. Register an account ");
        System.out.println("3. quit ");
        int menuIndex = scanner.nextInt();
        try {
            switch (menuIndex) {
            case 1: {// login
                System.out.println("Enter your email address:");
                String email = scanner.next();
                System.out.println("Enter your password:");
                String password = scanner.next();
                System.out.println("login email: " + email + ", password:" + password);
                UserService userService = new UserService();
                Result<AllUser> loginResult = userService.login(email, password);
                if (!loginResult.isSuccess()) {
                    System.out.println("login fail.");
                    return;
                }
                System.out.println("login successful");
                AllUser currentUser = loginResult.getObj();
                switch (Integer.valueOf(currentUser.getUser_type())) {
                case AgentService.user_type_agent:
                    String currentAgentId = agentService.getAgentId(currentUser.getUser_id());
                    agentMenu(currentUser.getUser_id(), currentAgentId);
                    break;
                case AgentService.user_type_renter:
                    String currentRenterId = renterService.getRenterId(currentUser.getUser_id());
                    renterMenu(currentUser.getUser_id(), currentRenterId);
                    break;
                default:
                    break;
                }
                break;
            }
            case 2: {// register
                System.out.println("Are you (1.Agent 2.Renter)");
                int userType = scanner.nextInt();
                switch (userType) {
                case AgentService.user_type_agent:// Agent
                case AgentService.user_type_renter:// Renter
                    UserService userService = new UserService();
                    System.out.println("Enter your user_id:");
                    String user_id = scanner.next();
                    System.out.println("Enter your password:");
                    String password = scanner.next();
                    System.out.println("Enter your first_name:");
                    String first_name = scanner.next();
                    System.out.println("Enter your last_name:");
                    String last_name = scanner.next();
                    System.out.println("Enter your email:");
                    String email = scanner.next();
                    userService.insertAllUser(user_id, password, String.valueOf(userType), first_name, last_name, email);
                    if (userType == AgentService.user_type_agent) {
                        System.out.println("Enter your agent_id:");
                        String agent_id = scanner.next();
                        System.out.println("Enter your job_title:");
                        String job_title = scanner.next();
                        System.out.println("Enter your estate_agency:");
                        String estate_agency = scanner.next();
                        System.out.println("Enter your contact_information:");
                        String contact_information = scanner.next();
                        // logic
                        userService.registerAgent(agent_id, job_title, estate_agency, contact_information, user_id);
                    } else if (userType == AgentService.user_type_renter) {
                        System.out.println("Enter your renter_id:");
                        String renter_id = scanner.next();
                        System.out.println("Enter your desired_move_in_date:");
                        int desired_move_in_date = scanner.nextInt();
                        System.out.println("Enter your preferred_location:");
                        String preferred_location = scanner.next();
                        System.out.println("Enter your budget:");
                        double budget = scanner.nextDouble();
           
                        // logic
                        userService.registerRenter(renter_id, desired_move_in_date, preferred_location, budget, user_id);
                    }
                    System.out.println("register successfully");
                    break;
                default:
                    break;
                }
                break;
            }
            case 3: // quit
                System.out.println("quit successful.");
                return;
            }
        } finally {
            scanner.close();
        }
    }

    public void agentMenu(String userId, String agentId) throws SQLException {
        final int menu_property_add = 1;
        final int menu_property_search = 2;
        final int menu_property_del = 3;
        final int menu_booking_search = 4;
        final int menu_booking_update = 5;
        final int menu_booking_del = 6;
        final int menu_quit = 999;
        System.out.println("Menu");
        System.out.println("-------------");
        System.out.println("1. Add property");
        System.out.println("2. Search property");
        System.out.println("3. Delete property");

        System.out.println("4. Search booking");
        System.out.println("5. Update booking state");
        System.out.println("6. Delete booking");
        System.out.println("999. quit");
        int agentMenuIndex = scanner.nextInt();
        switch (agentMenuIndex) {
        case menu_property_add: {
            System.out.println("Enter your property_type: 1: House; 2: Apartment; 3: Commercial Building");
            int property_type = scanner.nextInt();
            System.out.println("Enter your property_id:");
            String property_id = scanner.next();
            System.out.println("Enter your price:");
            double price = scanner.nextDouble();
            System.out.println("Enter your estate_agency:");
            String estate_agency = scanner.next();
            System.out.println("Enter your description:");
            String description = scanner.next();
            System.out.println("Enter your availability:");
            String availability = scanner.next();
            
            switch (property_type) {
            case PropertyService.property_type_house:
                House house = new House();
                house.setProperty_type(String.valueOf(property_type));
                house.setProperty_id(property_id);
                house.setAgent_id(agentId);
                house.setPrice(price);
                house.setEstate_agency(estate_agency);
                house.setDescription(description);
                house.setAvailability(availability);
        
                System.out.println("Enter your house id:");
                house.setHouse_id(scanner.next());
                System.out.println("Enter your num of rooms:");
                house.setNum_of_rooms(scanner.nextInt());
                System.out.println("Enter your square footage:");
                house.setSquare_footage(scanner.nextDouble());
                propertyService.insertHouse(house);
                System.out.println("Add house successfully");
                break;
            case PropertyService.property_type_commercial_building:
                CommercialBuilding commercialBuilding = new CommercialBuilding();
                commercialBuilding.setProperty_type(String.valueOf(property_type));
                commercialBuilding.setProperty_id(property_id);
                commercialBuilding.setAgent_id(agentId);
                commercialBuilding.setPrice(price);
                commercialBuilding.setEstate_agency(estate_agency);
                commercialBuilding.setDescription(description);
                commercialBuilding.setAvailability(availability);
               
                System.out.println("Enter your commercial_building_id:");
                commercialBuilding.setCommercial_building_id(scanner.next());
                System.out.println("Enter your square_footage:");
                commercialBuilding.setSquare_footage(scanner.nextDouble());
                System.out.println("Enter your type_of_business:");
                commercialBuilding.setType_of_business(scanner.next());
                propertyService.insertCommercialBuilding(commercialBuilding);
                System.out.println("Add commercial building successfully");
                break;
            case PropertyService.property_type_apartment:
                Apartment apartment = new Apartment();
                apartment.setProperty_type(String.valueOf(property_type));
                apartment.setProperty_id(property_id);
                apartment.setAgent_id(agentId);
                apartment.setPrice(price);
                apartment.setEstate_agency(estate_agency);
                apartment.setDescription(description);
                apartment.setAvailability(availability);
 

                System.out.println("Enter your apartment_id:");
                apartment.setApartment_id(scanner.next());
                System.out.println("Enter your num_of_rooms:");
                apartment.setNum_of_rooms(scanner.nextInt());
                System.out.println("Enter your square_footage:");
                apartment.setSquare_footage(scanner.nextDouble());
                System.out.println("Enter your building_type:");
                apartment.setBuilding_type(scanner.next());
                propertyService.insertApartment(apartment);
                System.out.println("Add apartment successfully");
                break;
            
            default:
                break;
            }
            break;
        }
        case menu_property_search:
            System.out.println("Enter your property_id:");
            String property_id = scanner.next();
            propertyService.findPropertyBySelf(property_id, userId);
            break;
        case menu_property_del:
            System.out.println("Enter your property_id:");
            property_id = scanner.next();
            propertyService.delProperty(property_id, agentId);
            System.out.println("Delete property successful.");
            break;
        case menu_booking_search:
            List<Booking> bookings = bookingService.findBooking(agentId, null);
            System.out.println("find bookings size: " + bookings.size());
            for (Booking booking : bookings) {
                System.out.println("booking id: " + booking.getBooking_id() + ", state: " + bookingService.getBookingStateText(booking.getState()));
            }
            break;
        case menu_booking_update: {
            System.out.println("Enter booking id:");
            String bookingId = scanner.next();
            System.out.println("Enter state: 1: Approve 2: Reject");
            int state = scanner.nextInt();
            bookingService.updateState(bookingId, agentId, state);
            System.out.println("Booking state has been updated");
            break;
        }
        case menu_booking_del:
            System.out.println("Enter booking id:");
            String bookingId = scanner.next();
            bookingService.delBooking(bookingId, agentId);
            System.out.println("Booking state is deleted");
            break;
        case menu_quit:
            System.out.println("exit successful.");
        }
    }

    public void renterMenu(String userId, String renter_id) throws SQLException {
        System.out.println("Menu");
        System.out.println("-------------");
        System.out.println("1. Search property");
        System.out.println("2. Book property");
        System.out.println("3. quit");
        int renterMenuIndex = scanner.nextInt();
        switch (renterMenuIndex) {
        case 1:
            System.out.println("Enter your property type: 1: House; 2: Apartment; 3: Commercial Building");
            int propertyType = scanner.nextInt();

            List<Property> properties = propertyService.getProperties(propertyType);
            for (Property property : properties) {
                System.out.println("property id: " + property.getProperty_id() + ", price: " + property.getPrice());
            }
            System.out.println("please input property id: ");
            String property_id = scanner.next();
            Property property = propertyService.findPropertyById(property_id);
            if (property == null) {
                System.out.println("The property does not exist");
            } else {
                System.out.println("Do you want to book:  Y/n");
                if ("y".equalsIgnoreCase(scanner.next())) {
                    booiking(renter_id, property_id);
                }
            }
            break;
        case 2:
            System.out.println("Enter your property_id:");
            property_id = scanner.next();
            booiking(renter_id, property_id);
            break;
        default:
            break;
        }
    }

    private void booiking(String renter_id, String property_id) throws SQLException {
        Property property = propertyService.findPropertyById(property_id);
        if (property == null) {
            System.out.println("THe property does not exist");
        } else {
            // search payment
            List<Payment> payments = paymentService.getMyPayments(renter_id);
            if (payments.size() == 0) {
                System.out.println("Do you want to add payment? Y/n");
                if (!"y".equalsIgnoreCase(scanner.next())) {
                    return;
                }
                Payment payment = new Payment();
                payment.setRenter_id(renter_id);
                System.out.println("Please enter your card_number:");
                payment.setCard_number(scanner.next());
                System.out.println("Please enter your expiration_date:");
                payment.setExpiration_date(scanner.nextInt());
                System.out.println("Please enter your cvv:");
                payment.setCvv(scanner.next());
                System.out.println("Please enter your street:");
                payment.setStreet(scanner.next());
                System.out.println("Please enter your city:");
                payment.setCity(scanner.next());
                System.out.println("Please enter your state:");
                payment.setState(scanner.next());
                System.out.println("Please enter your zip:");
                payment.setZip(scanner.nextInt());
                paymentService.addPayment(payment);
                System.out.println("Payment is added");
                payments = paymentService.getMyPayments(renter_id);
            }
            System.out.println("payment list info:");
            for (Payment payment : payments) {
                System.out.println("payment id: " + payment.getId() + ", last 4 card number: " + payment.getCard_number().substring(payment.getCard_number().length() - 4));
            }
            System.out.println("Please input your payment id: ");
            int paymentId = scanner.nextInt();
            Payment choosePayment = null;
            for (Payment payment : payments) {
                if (payment.getId() == paymentId) {
                    choosePayment = payment;
                }
            }
            if (choosePayment == null) {
                System.out.println("Payment information is not found");
                return;
            }
            // booking
            System.out.println("Enter your start_time:");
            int start_time = scanner.nextInt();
            System.out.println("Enter your end_time:");
            int end_time = scanner.nextInt();
            System.out.println("Enter your booking_id:");
            String booking_id = scanner.next();
            Booking booking = new Booking();
            booking.setProperty_id(property_id);
            booking.setBooking_id(booking_id);
            booking.setRenter_id(renter_id);
            booking.setCard_number(choosePayment.getCard_number());
            booking.setStart_time(start_time);
            booking.setEnd_time(end_time);
            booking.setAgent_id(property.getAgent_id());
            booking.setBooking_id(property.getProperty_id());
            agentService.insertBooking(booking);
            System.out.println("You have booked the property");
        }
    }

}
