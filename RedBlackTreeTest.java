public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();

        // Insert multiple values into the tree
        int[] vals = {10, 20, 30, 15, 25, 5};
        for (int v : vals) {
            t.insert(v);
        }

        System.out.println("Initial Tree (in-order traversal with color):");
        t.inorderPrint();  // Expect: sorted order with R/B labels
        System.out.println("Initial Tree (JSON format):");
        System.out.println(t.toJson());  // Tree as a JSON string

        // Search tests
        assert t.contains(15);  // Value 15 should exist
        assert !t.contains(99); // Value 99 does not exist

        // Delete a leaf node (no children)
        System.out.println("\nDeleting leaf node: 5");
        t.delete(5);
        assert !t.contains(5);  // Confirm 5 is deleted
        t.inorderPrint();

        // Delete a node with one child
        System.out.println("\nDeleting node with one child: 30");
        t.delete(30);
        assert !t.contains(30);
        t.inorderPrint();

        // Delete a node with two children
        System.out.println("\nDeleting node with two children: 20");
        t.delete(20);
        assert !t.contains(20);
        t.inorderPrint();

        // Delete the root node (in this case, likely 10)
        System.out.println("\nDeleting root node: 10");
        t.delete(10);
        assert !t.contains(10);
        t.inorderPrint();

        // Delete remaining nodes to empty the tree
        System.out.println("\nDeleting remaining nodes: 15, 25");
        t.delete(15);
        t.delete(25);
        t.inorderPrint();

        // Tree should now be empty (only NIL nodes remain)
        System.out.println("\nTree after all deletions (JSON):");
        System.out.println(t.toJson());

        // Final assertions to confirm tree is empty
        assert !t.contains(15);
        assert !t.contains(25);
    }
}
