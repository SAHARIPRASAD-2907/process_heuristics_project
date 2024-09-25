import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClarkeWrightSolver {

    private final List<Customer> customers;
    private final int vehicleCapacity;
    private final List<Saving> savingsList;
    private List<RouteClark> routes;

    public ClarkeWrightSolver(List<Customer> customers, int vehicleCapacity) {
        this.customers = customers;
        this.vehicleCapacity = vehicleCapacity;
        this.savingsList = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    // Main method to solve using Clarke-Wright Savings Algorithm
    public void solve() {
        initializeRoutes(); // Start with individual routes for each customer
        calculateSavings(); // Calculate savings for merging routes
        applySavings();     // Apply savings to merge routes
    }

    // Initialize a separate route for each customer
    private void initializeRoutes() {
        for (Customer customer : customers) {
            if (customer.id != 0) { // Skip the depot
                RouteClark route = new RouteClark(vehicleCapacity);
                route.addCustomer(customers.get(0)); // Add depot
                route.addCustomer(customer); // Add customer
                route.addCustomer(customers.get(0)); // Return to depot
                routes.add(route);
            }
        }
    }

    // Calculate savings for merging customers
    private void calculateSavings() {
        for (int i = 1; i < customers.size(); i++) {
            for (int j = i + 1; j < customers.size(); j++) {
                Customer c1 = customers.get(i);
                Customer c2 = customers.get(j);
                double saving = customers.get(0).distanceTo(c1) + customers.get(0).distanceTo(c2) - c1.distanceTo(c2);
                savingsList.add(new Saving(c1, c2, saving));
            }
        }
        Collections.sort(savingsList); // Sort savings in descending order
    }

    // Apply savings to merge routes
    private void applySavings() {
        for (Saving saving : savingsList) {
            RouteClark r1 = findRouteContaining(saving.c1);
            RouteClark r2 = findRouteContaining(saving.c2);

            if (r1 != null && r2 != null && r1 != r2 && r1.canMergeWith(r2)) {
                r1.mergeWith(r2);
                routes.remove(r2); // Remove the merged route
            }
        }
    }

    // Find the route that contains a given customer
    private RouteClark findRouteContaining(Customer customer) {
        for (RouteClark route : routes) {
            if (route.containsCustomer(customer)) {
                return route;
            }
        }
        return null;
    }

    // Print the results of the Clarke-Wright algorithm
    public void printResults() {
        System.out.println("Number of Vehicles Used: " + routes.size());
        double totalDistance = 0;
        for (int i = 0; i < routes.size(); i++) {
            RouteClark route = routes.get(i);
            totalDistance += route.getTotalDistance();
            System.out.print("Route for Vehicle " + (i + 1) + ": ");
            route.printRoute();
        }
        System.out.println("Total Distance: " + totalDistance);
    }
}