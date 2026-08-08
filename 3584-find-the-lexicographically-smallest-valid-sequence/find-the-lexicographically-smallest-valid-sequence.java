class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        /*
         * suffix[i] = maximum number of characters from the suffix
         * of word2 that can be matched exactly using word1[i...n-1].
         */
        int[] suffix = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                suffix[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        /*
         * First, greedily take the earliest possible indices.
         *
         * We can use our one allowed mismatch at the first
         * position where word1[i] != word2[j], provided the
         * remaining part can be matched exactly.
         */
        while (i < n && j < m) {

            if (word1.charAt(i) == word2.charAt(j)) {
                // Exact match is always the best choice.
                ans[j] = i;
                j++;
            } 
            else if (suffix[i + 1] >= m - j - 1) {
                /*
                 * Use the one allowed mismatch here.
                 *
                 * After using this position, all remaining
                 * characters must match exactly.
                 */
                ans[j] = i;
                j++;

                i++;

                // Now remaining characters must be matched exactly.
                while (i < n && j < m) {
                    if (word1.charAt(i) == word2.charAt(j)) {
                        ans[j] = i;
                        j++;
                    }
                    i++;
                }

                break;
            }

            i++;
        }

        // Could not construct the complete sequence.
        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}