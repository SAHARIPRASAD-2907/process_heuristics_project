import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route implements Comparable<Route> {
    private final List<Customer> customers;
    private final int vehicleCapacity;
    private List<Customer> customerOrder;
    private double totalDistance;
    private int vehiclesUsed;

    public Route(List<Customer> customers, int vehicleCapacity) {
        this.customers = customers;
        this.vehicleCapacity = vehicleCapacity;
        this.customerOrder = new ArrayList<>();
        this.totalDistance = 0;
        this.vehiclesUsed = 0;
    }

    // Generate a random route (shuffle the customers)
    public void generateRandomRoute() {
        customerOrder = new ArrayList<>(customers);
        Collections.shuffle(customerOrder);
    }

    // Evaluate the route based on the vehicle capacity
    public void evaluateRoute(int vehicleCapacity) {
        int currentLoad = 0;
        double distance = 0;
        vehiclesUsed = 1;
        Customer lastCustomer = customerOrder.get(0); // Start from the depot

        for (Customer customer : customerOrder) {
            if (currentLoad + customer.demand > vehicleCapacity) {
                // If the capacity is exceeded, a new vehicle is needed
                distance += lastCustomer.distanceTo(customers.get(0)); // Return to depot
                vehiclesUsed++;
                currentLoad = 0;
                lastCustomer = customers.get(0); // Start from depot again
            }

            // Add the customer to the route
            distance += lastCustomer.distanceTo(customer);
            currentLoad += customer.demand;
            lastCustomer = customer;
        }

        // Return to depot after the last customer
        distance += lastCustomer.distanceTo(customers.get(0));
        totalDistance = distance;
    }

    // Getters and helper methods
    public List<Customer> getCustomerOrder() {
        return customerOrder;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getVehiclesUsed() {
        return vehiclesUsed;
    }

    public void addCustomer(Customer customer) {
        customerOrder.add(customer);
    }

    public boolean containsCustomer(Customer customer) {
        return customerOrder.contains(customer);
    }

    // Print route details
    public void printRouteDetails() {
        System.out.println("Route taken by vehicles:");
        int currentVehicle = 1;
        int currentLoad = 0;
        System.out.print("Vehicle " + currentVehicle + ": 0 -> "); // Start from depot
        for (Customer customer : customerOrder) {
            if (currentLoad + customer.demand > vehicleCapacity) {
                System.out.print("0\n");
                currentVehicle++;
                System.out.print("Vehicle " + currentVehicle + ": 0 -> ");
                currentLoad = 0;
            }
            System.out.print(customer.id + " -> ");
            currentLoad += customer.demand;
        }
        System.out.println("0"); // Return to depot
    }

    // Compare routes based on their total distance
    @Override
    public int compareTo(Route other) {
        return Double.compare(this.getTotalDistance(), other.getTotalDistance());
    }
}
