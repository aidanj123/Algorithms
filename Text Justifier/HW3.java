public class HW3 {
    static int w = 80;
    static String[] W = {  "And" , "we" , "are" , "increasingly" , "relying" , "on" , "videoconferencing" , "app" , "like" , "Zoom" , "and" ,
                           "FaceTime" , "to" , "correspond" , "with" , "our" , "peers." , "But" , "inevitably", "with" , "our" , "homes" , "and" ,
                    "workplaces" , "merging" , "into" , "one," , "the" , "boundaries" , "between" , "our" , "personal" , "and" , "professional" ,
                    "lives" , "are" , "beginning" , "to" , "erode" , "and" , "awkward" , "situations" , "have" , "ensued." }; 
    static int n = W.length;
    static int i = 0;
    static int j = 5;


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
        memorizedMinimumBadness(W, 0, memo, linebreaksMemo, w);
        return linebreaksMemo;
    }
    public static int memorizedMinimumBadness(String[] W, int i, int[] memo, int[] linebreaksMemo, int w){
        if (memo[i] >= 0){
            return memo[i];
        }
        if (i == n){
            memo[i] = 0;
            linebreaksMemo[i] = n;
        }
        else {
            double min = Integer.MAX_VALUE;
            double indexMin = 0;
            for (int j = i+1; j < n; j++){
                double temp = badness(W, i, j, w);
                temp ++;
                memorizedMinimumBadness(W, j, memo, linebreaksMemo, w);
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
        int total = 0;
        for (int i = 0; i < W.length; i++){
            if (total > W.length){
                break;
            }
            total += next;
            L[i] = next;
            next = minBadness[next];
        }    
        return L;
    }

    public static void main(String[] args) {
        int[] split = split(w, W);
        for (int i : split) {
            System.out.println(i);
        } 
    }
}