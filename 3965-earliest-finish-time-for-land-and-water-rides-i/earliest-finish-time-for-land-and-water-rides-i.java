import java.util.*;

class Solution {

    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        int ans = Integer.MAX_VALUE;

        WaterHelper water = new WaterHelper(waterStartTime, waterDuration);
        LandHelper land = new LandHelper(landStartTime, landDuration);

        // Land -> Water
        for (int i = 0; i < landStartTime.length; i++) {
            int landFinish = landStartTime[i] + landDuration[i];
            ans = Math.min(ans, water.bestFinishAfter(landFinish));
        }

        // Water -> Land
        for (int i = 0; i < waterStartTime.length; i++) {
            int waterFinish = waterStartTime[i] + waterDuration[i];
            ans = Math.min(ans, land.bestFinishAfter(waterFinish));
        }

        return ans;
    }

    static class WaterHelper {
        int[] start;
        int[] prefixMinDur;
        int[] suffixMinStartPlusDur;
        int n;

        WaterHelper(int[] s, int[] d) {
            n = s.length;

            int[][] rides = new int[n][2];
            for (int i = 0; i < n; i++) {
                rides[i] = new int[]{s[i], d[i]};
            }

            Arrays.sort(rides, Comparator.comparingInt(a -> a[0]));

            start = new int[n];
            prefixMinDur = new int[n];
            suffixMinStartPlusDur = new int[n];

            for (int i = 0; i < n; i++) {
                start[i] = rides[i][0];
            }

            prefixMinDur[0] = rides[0][1];
            for (int i = 1; i < n; i++) {
                prefixMinDur[i] = Math.min(prefixMinDur[i - 1], rides[i][1]);
            }

            suffixMinStartPlusDur[n - 1] = rides[n - 1][0] + rides[n - 1][1];
            for (int i = n - 2; i >= 0; i--) {
                suffixMinStartPlusDur[i] = Math.min(
                    suffixMinStartPlusDur[i + 1],
                    rides[i][0] + rides[i][1]
                );
            }
        }

        int bestFinishAfter(int finishTime) {
            int idx = upperBound(start, finishTime);

            int ans = Integer.MAX_VALUE;

            if (idx > 0) {
                ans = Math.min(ans, finishTime + prefixMinDur[idx - 1]);
            }

            if (idx < n) {
                ans = Math.min(ans, suffixMinStartPlusDur[idx]);
            }

            return ans;
        }
    }

    static class LandHelper extends WaterHelper {
        LandHelper(int[] s, int[] d) {
            super(s, d);
        }
    }

    private static int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}