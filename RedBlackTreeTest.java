
import java.util.*;

public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree<Integer> myTree = new RedBlackTree<>();
        TreeSet<Integer> refTree = new TreeSet<>();
        int[] vals = {10, 20, 30, 15, 25, 5, 17, 3, 8};
        for (int v : vals) {
            myTree.insert(v);
            refTree.add(v);
            if (!myTree.toList().equals(new ArrayList<>(refTree))) {
                throw new AssertionError("Mismatch");
            }
            assert rbOk(myTree);
        }
        assert myTree.contains(15) && refTree.contains(15);
        assert !myTree.contains(99) && !refTree.contains(99);
        System.out.println("All tests passed. Final inorder list: " + refTree);
        System.out.println("JSON dump: " + myTree.toJson());
    }

    private static boolean rbOk(RedBlackTree<Integer> t) {
        return blackHeight(t.toJson()) != -1;
    }

    private static int blackHeight(String json) {
        if (json == null || json.equals("null")) {
            return 1;
        }
        boolean isRed = json.contains("\"color\":\"red\"");
        int leftIdx = json.indexOf("\"left\":");
        int rightIdx = json.indexOf("\"right\":");
        String leftJson = "null", rightJson = "null";
        if (leftIdx != -1) {
            int start = json.indexOf('{', leftIdx);
            if (start != -1) {
                int cut = findMatching(json, start);
                leftJson = json.substring(start, cut + 1);
            }
        }
        if (rightIdx != -1) {
            int start = json.indexOf('{', rightIdx);
            if (start != -1) {
                int cut = findMatching(json, start);
                rightJson = json.substring(start, cut + 1);
            }
        }
        if (isRed) {
            if (leftJson.contains("\"color\":\"red\"") || rightJson.contains("\"color\":\"red\"")) {
                return -1;
            }
        }
        int leftBlack = blackHeight(leftJson);
        int rightBlack = blackHeight(rightJson);
        if (leftBlack == -1 || rightBlack == -1 || leftBlack != rightBlack) {
            return -1;
        }
        return isRed ? leftBlack : leftBlack + 1;
    }

    private static int findMatching(String s, int pos) {
        if (pos == -1) {
            return -1;
        }
        int depth = 0;
        for (int i = pos; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{') {
                depth++;
            }
            if (c == '}') {
                depth--;
            }
            if (depth == 0) {
                return i;
            }
        }
        return s.length() - 1;
    }
}
