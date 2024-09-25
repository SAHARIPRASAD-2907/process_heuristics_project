import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "src/data/C101.txt";  // Specify your file path here

        // Define parameters for the Genetic Algorithm
        int populationSize = 100;
        int generations = 1500;
        double mutationRate = 0.05;

        try {
            // Read the dataset
            DatasetReader reader = new DatasetReader(fileName);
            List<Customer> customers = reader.getCustomers();
            int vehicleCapacity = reader.getVehicleCapacity();

            // Solve and print results using a static function
            System.out.println("Using Dynamic Programming (Exact Algorithm)");
            solveAndPrintResults(customers, vehicleCapacity);
            System.out.println("Using Genetic Algorithm (Metaheuristic)");
            solveAndPrintResultsWithGA(customers, vehicleCapacity, populationSize, generations, mutationRate);
            System.out.println("Using Clarke Wright Solver (Constructive Heuristics)");
            solveAndPrintResultsWithClarkeWright(customers, vehicleCapacity);

        } catch (IOException e) {
            System.err.println("Error reading dataset: " + e.getMessage());
        }
    }

    // Static function to solve the problem and print the results
    public static void solveAndPrintResults(List<Customer> customers, int vehicleCapacity) {
        // Solve the problem using Dynamic Programming
        DynamicProgrammingSolver solver = new DynamicProgrammingSolver(customers, vehicleCapacity);
        solver.solve();
        solver.printResults();  // Print routes, vehicles used, and total distance
    }

    // Static function to solve and print results using Genetic Algorithm
    public static void solveAndPrintResultsWithGA(List<Customer> customers, int vehicleCapacity, int populationSize, int generations, double mutationRate) {
        GeneticAlgorithmSolver solver = new GeneticAlgorithmSolver(customers, vehicleCapacity, populationSize, generations, mutationRate);
        solver.solve();  // Solve using Genetic Algorithm
        solver.printResults();  // Print best route, distance, and vehicles used
    }

    // Static function to solve and print results using Clarke-Wright Savings Algorithm
    public static void solveAndPrintResultsWithClarkeWright(List<Customer> customers, int vehicleCapacity) {
        ClarkeWrightSolver solver = new ClarkeWrightSolver(customers, vehicleCapacity);
        solver.solve();  // Solve using Clarke-Wright Savings Algorithm
        solver.printResults();  // Print number of vehicles used, routes, and total distance
    }
}