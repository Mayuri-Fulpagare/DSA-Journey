class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        List<Integer> less    = new ArrayList<>();
        List<Integer> equal   = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        for (int num : nums) {
            if      (num < pivot) less.add(num);
            else if (num > pivot) greater.add(num);
            else                  equal.add(num);
        }

        int[] result = new int[nums.length];
        int i = 0;

        for (int num : less)    result[i++] = num;
        for (int num : equal)   result[i++] = num;
        for (int num : greater) result[i++] = num;

        return result;
    }
}