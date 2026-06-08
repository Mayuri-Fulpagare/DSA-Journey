class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        int n = nums.length;
        int lessCount = 0, equalCount = 0;

        // Pass 1: count
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if      (num < pivot)  lessCount++;
            else if (num == pivot) equalCount++;
        }

        int[] result = new int[n];
        int l = 0;
        int g = lessCount + equalCount; // greater starts here

        // Pass 2: fill less and greater ONLY (skip pivot)
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if      (num < pivot) result[l++] = num;
            else if (num > pivot) result[g++] = num;
        }

        // Fill pivot section using JVM SIMD intrinsic (much faster than loop)
        Arrays.fill(result, lessCount, lessCount + equalCount, pivot);

        return result;
    }
}