class Solution {
    static final long MOD = 1_000_000_007L;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;

        // CSR (Compressed Sparse Row) offset array setup
        int[] head = new int[n + 2];
        for (int[] e : edges) {
            head[e[0] + 1]++;
            head[e[1] + 1]++;
        }
        for (int i = 1; i <= n + 1; i++) {
            head[i] += head[i - 1];
        }

        // Flat adjacency array construction
        int[] adj = new int[2 * n - 2];
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            adj[head[u]++] = v;
            adj[head[v]++] = u;
        }

        // BFS traversal variables
        int[] depth = new int[n + 1];
        int[] q = new int[n];
        int l = 0, r = 0;
        
        // Start BFS from root 1 (depth initialized to 1 to mark as visited)
        q[r++] = 1;
        depth[1] = 1;

        while (l < r) {
            int u = q[l++];
            // u's neighbors are located at indices [head[u - 1], head[u])
            int start = head[u - 1];
            int end = head[u];
            for (int i = start; i < end; i++) {
                int v = adj[i];
                if (depth[v] == 0) { // If unvisited
                    depth[v] = depth[u] + 1;
                    q[r++] = v;
                }
            }
        }

        // The last node in the queue (q[r - 1]) has the maximum depth.
        // We subtract 2: 1 because depth was 1-based, and another 1 to convert from depth to path length.
        return (int) modPow(2, depth[q[r - 1]] - 2);
    }

    private long modPow(long a, int b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) == 1) res = res * a % MOD;
            a = a * a % MOD;
            b >>= 1;
        }
        return res;
    }
}