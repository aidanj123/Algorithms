public class node {
    String color;
    int number;
    int discoveryTime;
    int finishTime;

    public node(String color, int number, int discoveryTime, int finishTime) {
        this.color = color;
        this.number = number;
        this.discoveryTime = discoveryTime;
        this.finishTime = finishTime;
    }
}
