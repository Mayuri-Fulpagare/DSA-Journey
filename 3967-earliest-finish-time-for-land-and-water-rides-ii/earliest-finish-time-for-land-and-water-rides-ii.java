// class Solution {
//     public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        
//     }
// }

import java.util.Arrays;

class Solution {
    int minFinish = Integer.MAX_VALUE;

    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int n = landStartTime.length;
        int m = waterStartTime.length;
        
        // Group start times and durations
        int[][] land = new int[n][2];
        for (int i = 0; i < n; i++) {
            land[i][0] = landStartTime[i];
            land[i][1] = landDuration[i];
        }
        
        int[][] water = new int[m][2];
        for (int i = 0; i < m; i++) {
            water[i][0] = waterStartTime[i];
            water[i][1] = waterDuration[i];
        }
        
        // Sort both by start times
        Arrays.sort(land, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(water, (a, b) -> Integer.compare(a[0], b[0]));
        
        minFinish = Integer.MAX_VALUE;
        
        // Try both permutations: land then water, and water then land
        checkOrder(land, water);
        checkOrder(water, land);
        
        return minFinish;
    }
    
    private void checkOrder(int[][] first, int[][] second) {
        int m = second.length;
        int[] preMinDuration = new int[m];
        int[] sufMinFinish = new int[m];
        
        // Prefix min for durations (if ride is already open)
        preMinDuration[0] = second[0][1];
        for (int i = 1; i < m; i++) {
            preMinDuration[i] = Math.min(preMinDuration[i-1], second[i][1]);
        }
        
        // Suffix min for finish times (if we have to wait for the ride to open)
        sufMinFinish[m-1] = second[m-1][0] + second[m-1][1];
        for (int i = m - 2; i >= 0; i--) {
            sufMinFinish[i] = Math.min(sufMinFinish[i+1], second[i][0] + second[i][1]);
        }
        
        for (int i = 0; i < first.length; i++) {
            int finishFirst = first[i][0] + first[i][1];
            
            // Custom binary search (upper bound) to find the split point
            int left = 0, right = m;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (second[mid][0] <= finishFirst) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            int pos = left - 1;
            
            // Option 1: We take a second ride that is already open when we finish the first
            if (pos >= 0) {
                minFinish = Math.min(minFinish, finishFirst + preMinDuration[pos]);
            }
            
            // Option 2: We wait for a second ride that opens after we finish the first
            if (pos + 1 < m) {
                minFinish = Math.min(minFinish, sufMinFinish[pos + 1]);
            }
        }
    }
}
