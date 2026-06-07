class Solution {
    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> nodes = new HashMap<>();
        Set<Integer> children = new HashSet<>();

        // Helper to get or create a node
        for (int[] desc : descriptions) {
            int parentVal = desc[0], childVal = desc[1], isLeft = desc[2];

            // Create nodes if they don't exist
            nodes.putIfAbsent(parentVal, new TreeNode(parentVal));
            nodes.putIfAbsent(childVal, new TreeNode(childVal));

            TreeNode parent = nodes.get(parentVal);
            TreeNode child = nodes.get(childVal);

            // Attach child to parent
            if (isLeft == 1) {
                parent.left = child;
            } else {
                parent.right = child;
            }

            // Mark childVal as a child node
            children.add(childVal);
        }

        // Root is the node that is never a child
        for (int[] desc : descriptions) {
            int parentVal = desc[0];
            if (!children.contains(parentVal)) {
                return nodes.get(parentVal);
            }
        }

        return null; // Should never reach here for valid input
    }
}