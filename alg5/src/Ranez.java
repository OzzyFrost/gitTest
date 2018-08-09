public class Ranez {
    static int n = 5;
    static int w = 13;
    static int[][] arr = new int[][]{{5, 3}, {3, 4}, {6, 4}, {7, 1}, {3, 6}};
    static int[][] arr1 = new int[][]{{3, 1}, {4, 6}, {5, 4}, {8, 7}, {9, 6}};

    public static void main(String[] args) {
        System.out.println(maxW(arr1, n - 1, w));
    }

    public static int maxW(int[][] arr, int n, int w) {
        if (n < 0) {
            return 0;
        }
        if (arr[n][0] <= w) {
            return Math.max(maxW(arr, n - 1, w - arr[n][0]) + arr[n][1], maxW(arr, n - 1, w));
        } else {
            return maxW(arr, n - 1, w);
        }

    }
}
