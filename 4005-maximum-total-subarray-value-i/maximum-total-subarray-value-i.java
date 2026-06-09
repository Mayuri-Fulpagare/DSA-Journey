class Solution {
    public long maxTotalValue(int[] nums, int k) {
        int max = nums[0], min = nums[0];
        for (int x : nums) {
            max = Math.max(max, x);
            min = Math.min(min, x);
        }
        return (long) k * (max - min);
    }
}