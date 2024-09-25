import java.util.ArrayList;
import java.util.List;

public class RouteClark {

    private final List<Customer> customers; // List of customers in the route
    private final int vehicleCapacity;      // Maximum capacity of the vehicle
    private int currentLoad;                // Current load of the vehicle
    private double totalDistance;           // Total distance of the route

    // Constructor
    public RouteClark(int vehicleCapacity) {
        this.customers = new ArrayList<>();
        this.vehicleCapacity = vehicleCapacity;
        this.currentLoad = 0;
        this.totalDistance = 0;
    }

    // Add a customer to the route
    public void addCustomer(Customer customer) {
        if (!customers.isEmpty()) {
            Customer lastCustomer = customers.get(customers.size() - 1);
            totalDistance += lastCustomer.distanceTo(customer); // Add distance between last and current customer
        }
        customers.add(customer);
        currentLoad += customer.demand;
    }

    // Merge this route with another route (concatenate the routes)
    public void mergeWith(RouteClark other) {
        this.customers.remove(customers.size() - 1); // Remove depot from this route
        for (int i = 1; i < other.customers.size(); i++) { // Skip depot from other route
            addCustomer(other.customers.get(i));
        }
    }

    // Check if this route can merge with another route without exceeding vehicle capacity
    public boolean canMergeWith(RouteClark other) {
        return this.currentLoad + other.currentLoad <= vehicleCapacity;
    }

    // Check if this route contains a specific customer
    public boolean containsCustomer(Customer customer) {
        return customers.contains(customer);
    }

    // Get the total distance of the route
    public double getTotalDistance() {
        return totalDistance;
    }

    // Print the route details
    public void printRoute() {
        for (Customer customer : customers) {
            System.out.print(customer.id + " -> ");
        }
        System.out.println();
    }
}
