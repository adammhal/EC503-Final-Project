
public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        int[] vals = {10, 20, 30, 15, 25, 5};
        for (int v : vals) {
            t.insert(v);
        }
        assert t.contains(15);
        assert !t.contains(99);
        System.out.println(t.toJson());  
        t.inorderPrint();
    }
}
