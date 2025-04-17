/*************************************************************************
 *
 *  Pace University
 *  Spring 2024
 *  Algorithms and Computing Theory
 *
 *  Course: CS 242
 *  Team members: Aidan Jurevich, Riley Ramcharran
 *  Collaborators: Eric Couperman
 *  References: https://www.w3schools.com/java/java_files_create.asp
 *
 *  Assignment: 3
 *  Problem: 1-6
 *  Description: Read in a file and justify the text based on a given width. The program will add spaces based on the
 *               width to make the text fill the entire line.
 *
 *  Input: unjust.txt , a text file with words to be justified.
 *  Output: just.txt, a text file with the justified words.
 *
 *  Visible methods:
 *  int badness(String[] W, int i, int j, int w) - calculates the badness of a collection of words.
 *  int l(String[] W, int i, int j) - calculates the length of a line of text.
 *  int[] minBadness(String[] W, int w) - returns an array of the minimum badnesses of a collection of words.
 *  int memoizedMinimumBadness(String[] W, int i, int[] memo, int[] linebreaksMemo, int w) - calculates the minimum badness of a collection of words using memoization.
 *  int[] split(int w, String[] W) - returns an array of integers where each element represents the index of the next line break.
 *  int[] justify(String[] W, int w) - justifies the text based on a given width.
 *  String spacedLine(String[] W, int[] L, int w, int i) - creates a single justified line of text with spacing.
 *  String[] parseWords(String filePath) - parses the words from a file and returns them as an array.
 *
 *************************************************************************/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
 public class HW3 {

    public static int badness(String[] W, int i, int j, int w){
        if (w - l(W, i, j) >= 0){
            return (w - l(W, i, j))^3;
        } else {
            return Integer.MAX_VALUE;
        }
    }
    public static int l(String[] W, int i, int j){
        int l = 0;
        for (int k = i; k <= j; k++){
            l += W[k].length();
            l++;
        }
        return l - 1;
    }
    public static int[] minBadness(String[] W, int w){
        int[] memo = new int[W.length];
        int[] linebreaksMemo = new int[W.length];
        for (int i = 0; i < W.length; i++){
            memo[i] = -1;
        }
        memoizedMinimumBadness(W, 0, memo, linebreaksMemo, w);
        return linebreaksMemo;
    }
    public static int memoizedMinimumBadness(String[] W, int i, int[] memo, int[] linebreaksMemo, int w){
        if (memo[i] >= 0){
            return memo[i];
        }
        if (i == W.length){
            memo[i] = 0;
            linebreaksMemo[i] = W.length;
        }
        else {
            double min = Integer.MAX_VALUE;
            double indexMin = 0;
            for (int j = i+1; j < W.length; j++){
                double temp = badness(W, i, j, w);
                temp ++;
                memoizedMinimumBadness(W, j, memo, linebreaksMemo, w);
                if (temp < min){
                    min = temp;
                    indexMin = j;
                }
            }
            memo[i] = (int) min;
            linebreaksMemo[i] = (int) indexMin;
        }
        return memo[i];
    }

    //  This method returns an array of integers where each element represents the index of the next line break.
    //  The first element is how man words are in the first line, and the second element is how many words are in the second line, and so on.
    //  Once all words are in the array, the array will be filled with -1 which signal to further methods that all words are accounted for.
    public static int[] split(int w, String[] W){
        int[] L = new int[W.length];
        for (int i = 0; i < W.length; i++){
            L[i] = -1;
        }
        int[] minBadness = minBadness(W, w);
        int next = minBadness[0];
        L[0] = 0;
        for (int i = 1; i < L.length; i++){
            if (next == 0){
                break;
            }
            if (next != W.length-1) {
                L[i] = next;
            }
            next = minBadness[next];
            
            
        }    

        return L;
    }
    public static void justify(String[] W, int w){
        int[] L = split(w, W);
        try{
            FileWriter myWriter = new FileWriter("just.txt");
            for (int i = 0; i < L.length; i++){
                if (L[i] == -1){
                    break;
                }
                String line = spacedLine(W, L, w, i);
                myWriter.write(line);
            }

            myWriter.close();
            }
            
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static String spacedLine(String[] W, int[] L, int w, int i){
        int x = 0;
        if (i > 0){
            x = L[i-1];
        }
        int next = L[i];
        String tempWord = "";
        for (int j = x; j < next; j++){
            tempWord += W[j] + " ";
        }
        int wLength = tempWord.length();
        int extraSpaces = w - wLength;
        int spacesSeperation = w / extraSpaces;
        int nextSpace = spacesSeperation;
        String finWord = "";
        for (int j = x; j < next; j++){
            finWord += W[j] + " ";
            while (extraSpaces > 0 && finWord.length() > nextSpace){
                finWord += " ";
                extraSpaces--;
                nextSpace += spacesSeperation;
            }
        }
        finWord = finWord.trim() + "\n";
        if (finWord.length() > w){
            for (int j = 0; j < finWord.length()-1; j++){
                if (finWord.charAt(j) == ' ' && finWord.charAt(j+1) == ' '){
                    finWord = finWord.substring(0, j) + finWord.substring(j+1);
                    break;
                }
            }
        }
        while (finWord.length() < w) {
            boolean spaceAdded = false; 
            for (int j = 0; j < finWord.length() - 1; j++) {
                if (finWord.charAt(j) == ' ' && finWord.charAt(j + 1) != ' ') {
                    finWord = finWord.substring(0, j) + " " + finWord.substring(j);
                    spaceAdded = true; 
                    break;
                }
            }
            if (!spaceAdded) {
                break;
            }
        }
        return finWord;
    }
    public static String[] parseWords(String filePath) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                words.addAll(Arrays.asList(lineWords));
            }
        }
        return words.toArray(new String[0]);
    }
    

    public static void main(String[] args) throws IOException {
        String[] W = parseWords("unjust.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the width of the page: ");
        int w = scanner.nextInt();        
        
        justify(W, w);
        scanner.close();
    }
}

