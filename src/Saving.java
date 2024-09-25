public class Saving implements Comparable<Saving> {
    Customer c1, c2;
    double saving;

    public Saving(Customer c1, Customer c2, double saving) {
        this.c1 = c1;
        this.c2 = c2;
        this.saving = saving;
    }

    @Override
    public int compareTo(Saving other) {
        return Double.compare(other.saving, this.saving); // Sort in descending order
    }
}