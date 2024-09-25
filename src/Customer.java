public class Customer {
    int id;
    int x;
    int y;
    int demand;
    int readyTime;
    int dueTime;
    int serviceTime;

    public Customer(int id, int x, int y, int demand, int readyTime, int dueTime, int serviceTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demand = demand;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.serviceTime = serviceTime;
    }

    public double distanceTo(Customer other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", demand=" + demand +
                ", readyTime=" + readyTime +
                ", dueTime=" + dueTime +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
