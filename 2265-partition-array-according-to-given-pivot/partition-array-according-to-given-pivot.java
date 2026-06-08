class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int lessCount = 0, equalCount = 0;

        // Pass 1: count less and equal elements
        for (int num : nums) {
            if      (num < pivot) lessCount++;
            else if (num == pivot) equalCount++;
        }

        // Precompute start indices for each partition
        int l = 0;                        // start of "less" section
        int e = lessCount;                // start of "equal" section
        int g = lessCount + equalCount;   // start of "greater" section

        int[] result = new int[nums.length];

        // Pass 2: place directly into result — no extra lists
        for (int num : nums) {
            if      (num < pivot)  result[l++] = num;
            else if (num == pivot) result[e++] = num;
            else                   result[g++] = num;
        }

        return result;
    }
}