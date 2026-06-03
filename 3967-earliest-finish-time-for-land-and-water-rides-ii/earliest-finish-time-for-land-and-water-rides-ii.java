import java.util.Arrays;

class Solution {
    int minFinish;

    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int n = landStartTime.length;
        int m = waterStartTime.length;
        
        // Pack start time and duration into a single 64-bit primitive long
        long[] land = new long[n];
        for (int i = 0; i < n; i++) {
            land[i] = ((long) landStartTime[i] << 32) | landDuration[i];
        }
        
        long[] water = new long[m];
        for (int i = 0; i < m; i++) {
            water[i] = ((long) waterStartTime[i] << 32) | waterDuration[i];
        }
        
        // Sorting primitive arrays is incredibly fast compared to object arrays
        Arrays.sort(land);
        Arrays.sort(water);
        
        minFinish = Integer.MAX_VALUE;
        
        // Pre-allocate to prevent multiple memory allocations in our helper method
        int maxLen = Math.max(n, m);
        int[] preMinDuration = new int[maxLen];
        int[] sufMinFinish = new int[maxLen];
        
        checkOrder(land, water, preMinDuration, sufMinFinish);
        checkOrder(water, land, preMinDuration, sufMinFinish);
        
        return minFinish;
    }
    
    private void checkOrder(long[] first, long[] second, int[] preMinDuration, int[] sufMinFinish) {
        int m = second.length;
        
        // (int) second[i] extracts the bottom 32 bits (duration)
        // (int) (second[i] >>> 32) extracts the top 32 bits (startTime)
        
        preMinDuration[0] = (int) second[0];
        for (int i = 1; i < m; i++) {
            int dur = (int) second[i];
            preMinDuration[i] = preMinDuration[i-1] < dur ? preMinDuration[i-1] : dur;
        }
        
        int lastStart = (int) (second[m-1] >>> 32);
        int lastDur = (int) second[m-1];
        sufMinFinish[m-1] = lastStart + lastDur;
        
        for (int i = m - 2; i >= 0; i--) {
            int start = (int) (second[i] >>> 32);
            int dur = (int) second[i];
            int finish = start + dur;
            sufMinFinish[i] = sufMinFinish[i+1] < finish ? sufMinFinish[i+1] : finish;
        }
        
        for (int i = 0; i < first.length; i++) {
            int finishFirst = (int) (first[i] >>> 32) + (int) first[i];
            
            // Binary search 
            int left = 0, right = m;
            while (left < right) {
                int mid = (left + right) >>> 1;
                if ((int) (second[mid] >>> 32) <= finishFirst) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            
            int pos = left - 1;
            
            if (pos >= 0) {
                int cand1 = finishFirst + preMinDuration[pos];
                if (cand1 < minFinish) minFinish = cand1;
            }
            if (pos + 1 < m) {
                int cand2 = sufMinFinish[pos + 1];
                if (cand2 < minFinish) minFinish = cand2;
            }
        }
    }
}
