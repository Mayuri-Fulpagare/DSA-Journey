class Solution {
    int[][] maxT, minT;
    int n;

    public long maxTotalValue(int[] nums, int k) {
        n = nums.length;
        int LOG = 32 - Integer.numberOfLeadingZeros(n);
        maxT = new int[n][LOG];
        minT = new int[n][LOG];

        for (int i = 0; i < n; i++) {
            maxT[i][0] = nums[i];
            minT[i][0] = nums[i];
        }
        for (int j = 1; j < LOG; j++)
            for (int i = 0; i + (1 << j) <= n; i++) {
                maxT[i][j] = Math.max(maxT[i][j - 1], maxT[i + (1 << (j - 1))][j - 1]);
                minT[i][j] = Math.min(minT[i][j - 1], minT[i + (1 << (j - 1))][j - 1]);
            }

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(b[0], a[0]));
        Set<Long> vis = new HashSet<>();

        pq.offer(new long[]{qMax(0, n - 1) - qMin(0, n - 1), 0, n - 1});
        vis.add((long) n * 0 + (n - 1));

        long ans = 0;
        for (int i = 0; i < k; i++) {
            long[] top = pq.poll();
            ans += top[0];
            int l = (int) top[1], r = (int) top[2];

            if (l + 1 <= r) {
                long key = (long)(l + 1) * n + r;
                if (vis.add(key))
                    pq.offer(new long[]{qMax(l + 1, r) - qMin(l + 1, r), l + 1, r});
            }
            if (r - 1 >= l) {
                long key = (long) l * n + (r - 1);
                if (vis.add(key))
                    pq.offer(new long[]{qMax(l, r - 1) - qMin(l, r - 1), l, r - 1});
            }
        }
        return ans;
    }

    int qMax(int l, int r) {
        int k = 31 - Integer.numberOfLeadingZeros(r - l + 1);
        return Math.max(maxT[l][k], maxT[r - (1 << k) + 1][k]);
    }

    int qMin(int l, int r) {
        int k = 31 - Integer.numberOfLeadingZeros(r - l + 1);
        return Math.min(minT[l][k], minT[r - (1 << k) + 1][k]);
    }
}