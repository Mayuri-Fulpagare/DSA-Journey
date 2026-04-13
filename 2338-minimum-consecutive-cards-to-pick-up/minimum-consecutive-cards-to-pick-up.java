class Solution {
    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> map = new HashMap<>();
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < cards.length; i++) {
            if (map.containsKey(cards[i])) {
                int lastIndex = map.get(cards[i]);
                int length = i - lastIndex + 1;
                minLength = Math.min(minLength, length);
            }
            map.put(cards[i], i);
        }

        return minLength == Integer.MAX_VALUE ? -1 : minLength;
    }
}