/*************************************************************************
 *
 *  Pace University
 *  Spring 2024
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Aidan Jurevich, Riley Ramcharran
 *  Collaborators: 
 *  References: 
 *
 *  Assignment: 2
 *  Problem: 1-4
 *  Description: Compare radix sort and quicksort on an array of integers with repetitions.
 *
 *  Input: n - the size of the input array, r - the number of repetitions for each integer in the array.
 *  Output: Running times of each sorting algorithm.
 *
 *  Visible data fields:
 *  int[] A1 - the original array with repetitions
 *  int[] A2 - copy of the original array for quicksort
 *  int n - size of the input array 
 *  int r - number of repetitions.
 *
 *  Visible methods:
 *  public static int[] quicksort(int[] A, int p, int r) - sorts the array A using quicksort
 *  public static int partition(int[] A, int p, int r) - partitions the array A for quicksort
 *  public static int[] radixSort(int[] A) - sorts the array A using radix sort
 *  public static int max(int[] A) - finds the maximum value in the array A for radix sort
 *  public static void countingSort(int[] A, int exp) - performs counting sort on the array A for radix sort
 *
 *
 *   Remarks
 *   -------
 *  Quicksort:
 *   |-------------------------------------------------------------------|
 *   |       |  100       | 1000      | 10000    | 100000   | 1000000    |
 *   |  5    |  121657    | 1609831   | 2875647  | 23743511 | 142523552  |
 *   |  25   |  152101    | 2413733   | 8557301  | 49143039 | 283906913  |
 *   |  100  |  256909    | 4771818   | 16508138 | 61779368 | 613567828  |
 *   |  500  |  318159    | 5149413   | 18336779 | 202557071| 3341897289 |
 *   |  1000 |  455354    | 6061733   | 46211419 | 364786171| 7656756886 |
 * 
 *  Radixsort:
 *   |-------------------------------------------------------------------|
 *   |       |  100       | 1000      | 10000    | 100000   | 1000000    |
 *   |  5    |  101106    | 1115867   | 6125453  | 61571071 | 268530247  |
 *   |  25   |  145169    | 799007    | 7992930  | 52061868 | 212272404  |
 *   |  100  |  151859    | 871851    | 5784116  | 51554931 | 227273347  |
 *   |  500  |  193238    | 854024    | 7074152  | 56350214 | 243923576  |
 *   |  1000 |  184606    | 821798    | 5462489  | 78073624 | 213682843  |
 * 
 *  When r is smaller, the performance of both algorithms is similar. However, as r increases, the 
 *  performance of radix sort improves significantly compared to quicksort. The repetitions seem to
 *  have no effect on radixsort. We can see the linear relationship of radixsort by multiplying previous
 *  running times by 10. Each column almost uniformally increases by one digit. Dividing the times by r
 *  shows no meaningful relationship, meaning that r has little effect on radix sort. Quicksort, on the other hand,
 *  is heavily affected by the number of repetitions. For high values of r, the same n is around 10 times slower than
 *  the lower rs. 
 *
 *************************************************************************/
package RadixSort;
import java.util.Arrays;
import java.util.Scanner;
public class HW2 {
    // Quicksort
    public static int[] quicksort(int[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
        return A;
    }

    public static int partition(int[] A, int p, int r) {
        int x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                i++;
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        int temp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp;
        return i + 1;
    }

    // Radix Sort
    public static int[] radixSort(int[] A) {
        int max = max(A);
        for (int i = 1; max / i > 0; i *= 10) {
            countingSort(A, i);
        }
        return A;
    }

    public static int max(int[] A) {
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        return max;
    }

    public static void countingSort(int[] A, int exp) {
        int n = A.length;
        int[] B = new int[n];
        int[] C = new int[10];
        Arrays.fill(C, 0);

        for (int i = 0; i < n; i++) {
            C[(A[i] / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            C[i] += C[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            B[C[(A[i] / exp) % 10] - 1] = A[i];
            C[(A[i] / exp) % 10]--;
        }
        for (int i = 0; i < n; i++) {
            A[i] = B[i];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the input size: ");
        int n = scanner.nextInt();
        System.out.println("Enter the number of repetitions: ");
        int r = scanner.nextInt();

        int[] A1 = new int[n];

        // Generate random array with r average repetitions
        for (int i = 0; i < n;) {
            int x = (int) ((Math.random() * 999999));
            int y = (int) ((Math.random() * (2 * r) + 1));
            for (int j = 0; j < y && i < n; j++) {
                A1[i] = x;
                i++;
            }
        }

        // Make copy of array
        int[] A2 = Arrays.copyOf(A1, A1.length);

        // Quicksort array and measure time

        long startTime = System.nanoTime();
        quicksort(A2, 0, n - 1);
        long quicksortTime = System.nanoTime() - startTime;
        System.out.println("Quicksort time:  " + quicksortTime + " ns");

        // Radix sort array and measure time
        startTime = System.nanoTime();
        radixSort(A1);
        long radixSortTime = System.nanoTime() - startTime;
        System.out.println("Radix sort time: " + radixSortTime + " ns");

        System.out.println();
        scanner.close();
    }
}
