public class TimeComplexityExamples {

    // O(1) - Constant Time
    public static void printFirstElement(int[] arr) {
        System.out.println(arr[0]);
    }

    // O(n) - Linear Time
    public static void printAllElements(int[] arr) {
        for (int num : arr) {
            System.out.println(num);
        }
    }

    // O(n^2) - Quadratic Time
    public static void printAllPairs(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println("(" + arr[i] + ", " + arr[j] + ")");
            }
        }
    }

    public static void main(String[] args) {
        int[] sample = {1, 2, 3, 4};

        printFirstElement(sample);      // O(1)
        printAllElements(sample);       // O(n)
        printAllPairs(sample);          // O(n^2)
    }
}
