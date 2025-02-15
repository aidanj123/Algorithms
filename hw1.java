/*************************************************************************
 *
 *  Pace University
 *  Spring 2024
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Aidan Jurevich, Riley Ramcharran
 *  Collaborators: 
 *  References: PUT THE LINKS TO YOUR SOURCES HERE
 *
 *  Assignment: 1
 *  Problem: 1-3
 *  Description: Test running times of maximum subarray algorithms.
 *
 *  Input: Number of elements in the array.
 *  Output: The maximum subarray sum.
 *
 *  Visible data fields:
 *  
 *
 *  Visible methods:
 *  algorithm1(int[] A) - Returns the maximum subarray sum using brute force.
 *  algorithm2(int[] A, int low, int high) - Returns the maximum subarray sum using divide and conquer.
 *  MaxCrossingSubAay(int[] A, int low, int mid, int high) - Returns the maximum subarray sum crossing the midpoint, used in algorithm 2.
 *
 *   Remarks
 *   -------
 *
 *   PUT ALL NON-CODING ANSWERS HERE
 *
 *
 *************************************************************************/
import java.util.Scanner;

public class hw1 {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of elements in the array: ");
        int n = scanner.nextInt();

        int A[] = new int[n];

        for (int i = 0; i < n; i++) {
            A[i] = (int) ((Math.random() * 200) - 100);
        }

        System.out.println("The array is: " + java.util.Arrays.toString(A));
        System.out.println(algorithm1(A));
        System.out.println(algorithm2(A, 0, n - 1));


        scanner.close();
    }
}