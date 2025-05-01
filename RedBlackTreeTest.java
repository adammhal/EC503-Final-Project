import java.util.*;

public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        TreeSet<Integer> ref = new TreeSet<>();
        int[] vals = {10, 20, 30, 15, 25, 5, 17, 3, 8};
        for (int v : vals) {
            t.insert(v);
            ref.add(v);
            if (!t.toList().equals(new ArrayList<>(ref))) {
                throw new AssertionError("insert mismatch");
            }
            assert rbOk(t);
        }
        int[] dels = {20, 5, 10};
        for (int d : dels) {
            t.delete(d);
            ref.remove(d);
            if (!t.toList().equals(new ArrayList<>(ref))) {
                throw new AssertionError("delete mismatch");
            }
            assert rbOk(t);
        }
        assert t.contains(15) && ref.contains(15);
        assert !t.contains(99) && !ref.contains(99);
        System.out.println("All tests passed. Final inorder list: " + ref);
        System.out.println(t.toJson());
    }

    private static boolean rbOk(RedBlackTree<Integer> t) {
        return bh(t.toJson()) != -1;
    }

    private static int bh(String j) {
        if (j == null || j.equals("null")) {
            return 1;
        }
        boolean red = j.contains("\"color\":\"red\"");
        int li = j.indexOf("\"left\":");
        int ri = j.indexOf("\"right\":");
        String l = "null", r = "null";
        if (li != -1) {
            int s = j.indexOf('{', li);
            if (s != -1) {
                l = j.substring(s, match(j, s) + 1);
        
            }}
        if (ri != -1) {
            int s = j.indexOf('{', ri);
            if (s != -1) {
                r = j.substring(s, match(j, s) + 1);
        
            }}
        if (red && (l.contains("red") || r.contains("red"))) {
            return -1;
        }
        int lb = bh(l), rb = bh(r);
        if (lb == -1 || rb == -1 || lb != rb) {
            return -1;
        }
        return red ? lb : lb + 1;
    }

    private static int match(String s, int p) {
        int d = 0;
        for (int i = p; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '{') {
                d++;
            
            }if (c == '}') {
                d--;
            
            }if (d == 0) {
                return i;
        
            }}
        return s.length() - 1;
    }
}
