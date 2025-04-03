package HW2;
import java.util.Scanner;

public class HW2 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the input size: ");
        int n = scanner.nextInt();
        System.out.println("Enter the number of repetitions: ");
        int r = scanner.nextInt();

        int[] A1 = new int[n];

        for (int i = 0; i < n; i++) {
            int x = (int) ((Math.random() * 999999));
            int y = (int) ((Math.random() * (2 * r) + 1));

            for (int j = 0; j < y && i < n; j++) {
                A1[i] = x;
                if (j < y - 1) {
                    i++;
                }
            }
            i++;
        }

        int[] A2 = A1;

        System.out.println(A1.toString());
        System.out.println(A2.toString());

        scanner.close();
    }
}
