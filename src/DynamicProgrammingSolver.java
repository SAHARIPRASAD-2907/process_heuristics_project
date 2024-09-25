import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgrammingSolver {

    private final List<Customer> customers;
    private final int vehicleCapacity;
    private int[][] dp;
    private int[][] prev;
    private double[][] distanceMatrix;
    private List<List<Integer>> routes; // Store routes for each vehicle
    private int numVehiclesUsed;
    private double totalDistance;

    public DynamicProgrammingSolver(List<Customer> customers, int vehicleCapacity) {
        this.customers = customers;
        this.vehicleCapacity = vehicleCapacity;
        int n = customers.size();
        this.dp = new int[n][n];
        this.prev = new int[n][n];
        this.distanceMatrix = new double[n][n];
        this.routes = new ArrayList<>();
        computeDistanceMatrix();
    }

    // Compute the distance between customers and store it in a matrix
    private void computeDistanceMatrix() {
        int n = customers.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distanceMatrix[i][j] = customers.get(i).distanceTo(customers.get(j));
            }
        }
    }

    // Solve the VRPTW problem
    public void solve() {
        int n = customers.size();
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // Infinite-like value
        }

        dp[0][0] = 0; // Start at the depot (customer 0)

        // Iterate over customers to update dp table
        for (int u = 0; u < n; u++) {
            for (int v = 0; v < n; v++) {
                if (u == v) continue; // Skip the same customer

                // Calculate the time to reach customer v from customer u
                int arrivalTime = dp[u][u] + (int) distanceMatrix[u][v];

                if (arrivalTime <= customers.get(v).dueTime) {
                    // Update dp with earliest time and store previous customer
                    dp[u][v] = Math.max(arrivalTime, customers.get(v).readyTime) + customers.get(v).serviceTime;
                    prev[u][v] = u;
                }
            }
        }

        // Track the routes and minimize vehicles used
        findRoutes();
    }

    // Backtrack and find the routes for the vehicles
    private void findRoutes() {
        int n = customers.size();
        boolean[] visited = new boolean[n];
        numVehiclesUsed = 0;
        totalDistance = 0;

        while (!allCustomersVisited(visited)) {
            List<Integer> route = new ArrayList<>();
            int currentCapacity = 0;
            int currentCustomer = 0; // Start from depot

            // Build the route for a single vehicle
            while (true) {
                route.add(currentCustomer);
                visited[currentCustomer] = true;

                int nextCustomer = findNextCustomer(currentCustomer, visited, currentCapacity);
                if (nextCustomer == -1) {
                    break; // No valid next customer, route ends
                }

                double distance = distanceMatrix[currentCustomer][nextCustomer];
                currentCapacity += customers.get(nextCustomer).demand;
                totalDistance += distance;
                currentCustomer = nextCustomer;
            }

            route.add(0); // Return to depot
            totalDistance += distanceMatrix[currentCustomer][0];
            routes.add(route);
            numVehiclesUsed++;
        }
    }

    // Find the next customer to visit for the current route
    private int findNextCustomer(int currentCustomer, boolean[] visited, int currentCapacity) {
        int n = customers.size();
        for (int nextCustomer = 1; nextCustomer < n; nextCustomer++) {
            if (!visited[nextCustomer] && currentCapacity + customers.get(nextCustomer).demand <= vehicleCapacity) {
                return nextCustomer;
            }
        }
        return -1;
    }

    // Check if all customers have been visited
    private boolean allCustomersVisited(boolean[] visited) {
        for (int i = 1; i < visited.length; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }

    // Print results
    public void printResults() {
        System.out.println("Number of Vehicles Used: " + numVehiclesUsed);
        System.out.println("Total Distance: " + totalDistance);
        for (int i = 0; i < routes.size(); i++) {
            System.out.println("Route for Vehicle " + (i + 1) + ": " + routes.get(i));
        }
    }
}