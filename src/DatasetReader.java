import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatasetReader {
    private int numVehicles;
    private int vehicleCapacity;
    private List<Customer> customers;

    public DatasetReader(String fileName) throws IOException {
        customers = new ArrayList<>();
        readData(fileName);
    }

    private void readData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        // Skip the first 4 lines
        for (int i = 0; i < 4; i++) {
            reader.readLine();
        }

        // Read the 5th line (Vehicle number and capacity)
        line = reader.readLine().trim();
        String[] vehicleData = line.split("\\s+");
        numVehicles = Integer.parseInt(vehicleData[0]);
        vehicleCapacity = Integer.parseInt(vehicleData[1]);

        // Skip the next line which is just headers for customer data
        reader.readLine();
        reader.readLine();
        reader.readLine();
        reader.readLine();


        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (!line.isEmpty()) {
                String[] parts = line.split("\\s+");
                int id = Integer.parseInt(parts[0]);
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int demand = Integer.parseInt(parts[3]);
                int readyTime = Integer.parseInt(parts[4]);
                int dueTime = Integer.parseInt(parts[5]);
                int serviceTime = Integer.parseInt(parts[6]);

                customers.add(new Customer(id, x, y, demand, readyTime, dueTime, serviceTime));
            }
        }

        reader.close();
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public int getVehicleCapacity() {
        return vehicleCapacity;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

}
