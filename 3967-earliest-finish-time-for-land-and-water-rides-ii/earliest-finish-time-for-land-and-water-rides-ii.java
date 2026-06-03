class Solution {
    private static final int INF = 1000000000; // Safe infinity
    
    // Static arrays are allocated ONCE per JVM, meaning zero Garbage Collection delays!
    private static final int[] minLandDur = new int[100005];
    private static final int[] minWaterDur = new int[100005];
    private static final int[] preMinSecondDur = new int[100005];
    private static final int[] sufMinSecondFinish = new int[100005];

    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int maxLandStart = 0;
        int maxWaterStart = 0;
        
        // Find maximum boundaries so we only iterate as far as we need to
        for (int t : landStartTime) {
            if (t > maxLandStart) maxLandStart = t;
        }
        for (int t : waterStartTime) {
            if (t > maxWaterStart) maxWaterStart = t;
        }
        
        // Only clear up to the max starts for this specific test case
        for (int i = 0; i <= maxLandStart; i++) minLandDur[i] = INF;
        for (int i = 0; i <= maxWaterStart; i++) minWaterDur[i] = INF;
        
        // Bucket map the durations (storing only the fastest duration per start time)
        for (int i = 0; i < landStartTime.length; i++) {
            int st = landStartTime[i];
            int dur = landDuration[i];
            if (dur < minLandDur[st]) {
                minLandDur[st] = dur;
            }
        }
        
        for (int i = 0; i < waterStartTime.length; i++) {
            int st = waterStartTime[i];
            int dur = waterDuration[i];
            if (dur < minWaterDur[st]) {
                minWaterDur[st] = dur;
            }
        }
        
        int ans1 = solve(minLandDur, minWaterDur, maxLandStart, maxWaterStart);
        int ans2 = solve(minWaterDur, minLandDur, maxWaterStart, maxLandStart);
        
        return ans1 < ans2 ? ans1 : ans2;
    }
    
    // Extremely fast linear scan logic
    private int solve(int[] firstDur, int[] secondDur, int maxFirstStart, int maxSecondStart) {
        preMinSecondDur[0] = secondDur[0];
        for (int i = 1; i <= maxSecondStart; i++) {
            preMinSecondDur[i] = preMinSecondDur[i-1] < secondDur[i] ? preMinSecondDur[i-1] : secondDur[i];
        }
        
        sufMinSecondFinish[maxSecondStart] = secondDur[maxSecondStart] != INF ? maxSecondStart + secondDur[maxSecondStart] : INF;
        for (int i = maxSecondStart - 1; i >= 0; i--) {
            int finish = secondDur[i] != INF ? i + secondDur[i] : INF;
            sufMinSecondFinish[i] = sufMinSecondFinish[i+1] < finish ? sufMinSecondFinish[i+1] : finish;
        }
        
        int minFinish = INF;
        
        // Check all start times sequentially
        for (int i = 0; i <= maxFirstStart; i++) {
            if (firstDur[i] == INF) continue;
            
            int finishFirst = i + firstDur[i];
            
            // "pos" acts as an instant O(1) binary search alternative
            int pos = finishFirst < maxSecondStart ? finishFirst : maxSecondStart;
            
            // Option 1: Second ride is already open 
            if (preMinSecondDur[pos] != INF) {
                int cand = finishFirst + preMinSecondDur[pos];
                if (cand < minFinish) minFinish = cand;
            }
            
            // Option 2: Second ride hasn't opened yet
            if (pos + 1 <= maxSecondStart) {
                if (sufMinSecondFinish[pos + 1] < minFinish) {
                    minFinish = sufMinSecondFinish[pos + 1];
                }
            }
        }
        return minFinish;
    }
}
