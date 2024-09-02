import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Driver {
    private String name;
    private String licenseNumber;

    public Driver(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return "Driver Name: " + name + ", License Number: " + licenseNumber;
    }
}

class Passenger {
    private String name;
    private int seatNumber;

    public Passenger(String name, int seatNumber) {
        this.name = name;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "Passenger Name: " + name + ", Seat Number: " + seatNumber;
    }
}

class Bus {
    private String id;
    private String route;
    private int capacity;
    private Driver driver;
    private boolean[] seats;
    private Map<Integer, Passenger> passengerList;

    public Bus(String id, String route, int capacity, Driver driver) {
        this.id = id;
        this.route = route;
        this.capacity = capacity;
        this.driver = driver;
        this.seats = new boolean[capacity];
        this.passengerList = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getRoute() {
        return route;
    }

    public int getCapacity() {
        return capacity;
    }

    public Driver getDriver() {
        return driver;
    }

    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber < 1 || seatNumber > capacity) {
            throw new IllegalArgumentException("Invalid seat number.");
        }
        return !seats[seatNumber - 1];
    }

    public boolean bookSeat(int seatNumber, Passenger passenger) {
        if (seatNumber < 1 || seatNumber > capacity) {
            throw new IllegalArgumentException("Invalid seat number.");
        }
        if (isSeatAvailable(seatNumber)) {
            seats[seatNumber - 1] = true;
            passengerList.put(seatNumber, passenger);
            return true;
        }
        return false;
    }

    public boolean cancelSeat(int seatNumber) {
        if (seatNumber < 1 || seatNumber > capacity) {
            throw new IllegalArgumentException("Invalid seat number.");
        }
        if (!isSeatAvailable(seatNumber)) {
            seats[seatNumber - 1] = false;
            passengerList.remove(seatNumber);
            return true;
        }
        return false;
    }

    public int getAvailableSeats() {
        int availableSeats = 0;
        for (boolean seat : seats) {
            if (!seat) {
                availableSeats++;
            }
        }
        return availableSeats;
    }

    public void displayPassengers() {
        if (passengerList.isEmpty()) {
            System.out.println("No passengers booked.");
        } else {
            for (Passenger passenger : passengerList.values()) {
                System.out.println(passenger);
            }
        }
    }

    @Override
    public String toString() {
        return "Bus ID: " + id + ", Route: " + route + ", Capacity: " + capacity +
               ", Available Seats: " + getAvailableSeats() + "\nDriver Info: " + driver;
    }
}

class TicketBookingSystem {
    private Bus bus;

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public void bookTicket(String passengerName, int seatNumber) {
        if (bus != null) {
            Passenger passenger = new Passenger(passengerName, seatNumber);
            if (bus.bookSeat(seatNumber, passenger)) {
                System.out.println("Ticket booked successfully for seat " + seatNumber);
            } else {
                System.out.println("Seat " + seatNumber +  " is already booked.");
            }
        } else {
            System.out.println("No bus selected.");
        }
    }

    public void cancelTicket(int seatNumber) {
        if (bus != null) {
            if (bus.cancelSeat(seatNumber)) {
                System.out.println("Booking canceled for seat " + seatNumber);
            } else {
                System.out.println("Seat " + seatNumber + " was not booked.");
            }
        } else {
            System.out.println("No bus selected.");
        }
    }

    public void displayAvailableSeats() {
        if (bus != null) {
            System.out.println("Available seats: " + bus.getAvailableSeats());
        } else {
            System.out.println("No bus selected.");
        }
    }

    public void displayPassengers() {
        if (bus != null) {
            bus.displayPassengers();
        } else {
            System.out.println("No bus selected.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Driver driver = new Driver("John Doe", "DL123456");
        Bus bus = new Bus("101", "hydrabad", 30, driver);
        TicketBookingSystem ticketSystem = new TicketBookingSystem();
        ticketSystem.setBus(bus);

        while (true) {
            System.out.println("\nBus Ticket Management System");
            System.out.println("1. Display Bus Info");
            System.out.println("2. Book Ticket,passengerName,mobile number,Email");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. Display Available Seats");
            System.out.println("5. Display Passengers");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println(bus);
                    break;
                case 2:
                    System.out.print("Enter passenger name: ");
                    String name = scanner.nextLine();
                     System.out.print("Enter mobile number: ");
                    String mobilenumber = scanner.nextLine();
                     System.out.print("Enter Email: ");
                    String Email = scanner.nextLine();
                    System.out.print("Enter seat number to book: ");
                    int bookSeat = scanner.nextInt();
                    scanner.nextLine();
                    ticketSystem.bookTicket(name, bookSeat);
                    break;
                case 3:
                    System.out.print("Enter seat number to cancel: ");
                    int cancelSeat = scanner.nextInt();
                    scanner.nextLine();
                    ticketSystem.cancelTicket(cancelSeat);
                    break;
                case 4:
                    ticketSystem.displayAvailableSeats();
                    break;
                case 5:
                    ticketSystem.displayPassengers();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
