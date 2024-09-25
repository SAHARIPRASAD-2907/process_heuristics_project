import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmSolver {

    private final List<Customer> customers;
    private final int vehicleCapacity;
    private final int populationSize;
    private final int generations;
    private final double mutationRate;
    private final Random random = new Random();

    private List<Route> population;
    private Route bestRoute;
    private double bestDistance;
    private int bestVehiclesUsed;

    public GeneticAlgorithmSolver(List<Customer> customers, int vehicleCapacity, int populationSize, int generations, double mutationRate) {
        this.customers = customers;
        this.vehicleCapacity = vehicleCapacity;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
        this.population = new ArrayList<>();
        this.bestDistance = Double.MAX_VALUE;
    }

    // Main method to solve using Genetic Algorithm
    public void solve() {
        initializePopulation(); // Initialize with random routes
        for (int generation = 0; generation < generations; generation++) {
            List<Route> newPopulation = new ArrayList<>();

            // Generate a new population through selection, crossover, and mutation
            for (int i = 0; i < populationSize; i++) {
                Route parent1 = selectParent();
                Route parent2 = selectParent();
                Route child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }

            // Replace the old population with the new one
            population = newPopulation;

            // Check for the best route in this generation
            for (Route route : population) {
                route.evaluateRoute(vehicleCapacity);
                double distance = route.getTotalDistance();
                int vehiclesUsed = route.getVehiclesUsed();

                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestVehiclesUsed = vehiclesUsed;
                    bestRoute = route;
                }
            }
        }
    }

    // Initialize population with random routes
    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Route route = new Route(customers, vehicleCapacity);
            route.generateRandomRoute();
            population.add(route);
        }
    }

    // Select a parent route using tournament selection
    private Route selectParent() {
        int tournamentSize = 5;
        List<Route> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            Route randomRoute = population.get(random.nextInt(populationSize));
            tournament.add(randomRoute);
        }
        return Collections.min(tournament);
    }

    // Crossover two parent routes to create a child route
    private Route crossover(Route parent1, Route parent2) {
        Route child = new Route(customers, vehicleCapacity);

        // Perform ordered crossover
        int start = random.nextInt(parent1.getCustomerOrder().size());
        int end = random.nextInt(parent1.getCustomerOrder().size());

        for (int i = Math.min(start, end); i < Math.max(start, end); i++) {
            child.addCustomer(parent1.getCustomerOrder().get(i));
        }

        for (Customer c : parent2.getCustomerOrder()) {
            if (!child.containsCustomer(c)) {
                child.addCustomer(c);
            }
        }

        return child;
    }

    // Mutate a route by swapping two customers
    private void mutate(Route route) {
        if (random.nextDouble() < mutationRate) {
            int index1 = random.nextInt(route.getCustomerOrder().size());
            int index2 = random.nextInt(route.getCustomerOrder().size());

            // Swap two customers
            Collections.swap(route.getCustomerOrder(), index1, index2);
        }
    }

    // Print the results of the genetic algorithm
    public void printResults() {
        System.out.println("Best Total Distance: " + bestDistance);
        System.out.println("Number of Vehicles Used: " + bestVehiclesUsed);
        bestRoute.printRouteDetails();
    }
}
