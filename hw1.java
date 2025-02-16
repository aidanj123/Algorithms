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
 *  Assignment: 1
 *  Problem: 1-4
 *  Description: Test running times of maximum subarray algorithms.
 *
 *  Input: Number of elements in the array.
 *  Output: The maximum subarray sum using each algorithm and the running time of each algorithm.
 *
 *  Visible data fields:
 *  
 *
 *  Visible methods:
 *  algorithm1(int[] A) - Returns the maximum subarray sum using brute force.
 *  algorithm2(int[] A, int low, int high) - Returns the maximum subarray sum using divide and conquer.
 *  MaxCrossingSubAay(int[] A, int low, int mid, int high) - Returns the maximum subarray sum crossing the midpoint, used in algorithm 2.
 *  maxSubarrayK(int[] A) - Returns the maximum subarray sum using Kadane's algorithm
 *
 *   Remarks
 *  |------------------------------------------------------------------------------|
 *  |                    |   n=10^3 | n=10^4   |  n=10^5  |   n=10^6  |   n=10^7   | 
 *  |--------------------|----------|----------|----------|-----------|------------|
 *  | Brute Force        | 376788   | 4576893  | 55778272 | 4702963760|  Crashed   |
 *  | Divide & Conquer   | 135288   | 505803   | 1672421  | 13812499  | 35428240   |
 *  | Kadane's Algorithm | 40527    | 117734   | 654659   | 5112664   | 6843197    |
 *  |------------------------------------------------------------------------------|
 * 
 *  Conclusions:
 *  As we can see from the measured running times, brute force's running time is growing exponentially with
 *  the size of the input and matches the running time of O(n^2).  
 * 
 *  Divide and conquer's running time is growing much slower and is growing at a pace consistent with O(nlogn) 
 *  when compared with the running time of the brute force.
 * 
 *  Kadane's algorithm's running time is growing linearly with the size of the input as seen by the running times
 *  only growing slightly compared to the other two methods, which matches the given running time of O(n).
 *
 *
 *************************************************************************/
import java.util.Scanner;

public class hw1 {
    //Brute Force Algorithm
    public static int algorithm1(int[] A) {
        int max = -999999999;

        for (int i = 0; i < A.length; i++) {
            int sum = 0;
            for (int j = i; j < A.length; j++) {
                sum += A[j];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    //Divide and Conquer Algorithm
    public static int algorithm2(int[] A, int low, int high) {
        if(low == high) {
            return A[low];
        }
        else {
            int mid = (low + high) / 2;
            int leftSum = algorithm2(A, low, mid);
            int rightSum = algorithm2(A, mid + 1, high);
            int crossSum = MaxCrossingSubAay(A, low, mid, high);
            return Math.max(Math.max(leftSum, rightSum), crossSum);
        }
    }

    //Helper function for algorithm 2
    public static int MaxCrossingSubAay(int[] A, int low, int mid, int high) {
        int leftSum = -999999999;
        int sum = 0;
        for (int i = mid; i >= low; i--) {
            sum += A[i];
            if(sum > leftSum) {
                leftSum = sum;
            }
        }
        int rightSum = -999999999;
        sum = 0;
        for (int j = mid + 1; j <= high; j++) {
            sum += A[j];
            if(sum > rightSum) {
                rightSum = sum;
            }
        }
        return leftSum + rightSum;
    }

    //Kadane's Algorithm
    public static int maxSubarrayK(int[] A) {
        int maxSF = A[0], maxEH = A[0]; //Max So Far -> Max End Here
        for (int i = 1; i < A.length; i++) {
            maxEH = Math.max(A[i], maxEH + A[i]);
            maxSF = Math.max(maxSF, maxEH);
        }
        return maxSF;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements in the array: ");
        int n = scanner.nextInt();

        int A[] = new int[n];

        for (int i = 0; i < n; i++) {
            A[i] = (int) ((Math.random() * 200) - 100);
        }

        long startTime;

        startTime = System.nanoTime();
        System.out.println(algorithm1(A));
        System.out.println("t1= "+(System.nanoTime() - startTime)+" nanosecs.");

        startTime = System.nanoTime();
        System.out.println(algorithm2(A, 0, n - 1));
        System.out.println("t2= "+(System.nanoTime() - startTime)+" nanosecs.");

        startTime = System.nanoTime();
        System.out.println(maxSubarrayK(A));
        System.out.println("t3= "+(System.nanoTime() - startTime)+" nanosecs.");


        scanner.close();
    }
}