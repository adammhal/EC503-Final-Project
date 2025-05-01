import java.util.*;

public class RedBlackTree<T extends Comparable<T>> {

    private enum Color {
        RED, BLACK
    }

    private final class Node {

        T key;
        Color color;
        Node left, right, parent;

        Node(T key) {
            this.key = key;
            this.color = Color.RED;
            this.left = NIL;
            this.right = NIL;
            this.parent = NIL;
        }
    }

    private final Node NIL = new Node(null);
    private Node root = NIL;

    public RedBlackTree() {
        NIL.color = Color.BLACK;
        NIL.left = NIL.right = NIL.parent = NIL;
    }

    /* ---------- search ---------- */
    public boolean contains(T key) {
        return searchNode(key) != NIL;
    }

    private Node searchNode(T key) {
        Node n = root;
        while (n != NIL) {
            int c = key.compareTo(n.key);
            if (c == 0) {
                return n;
            }
            n = c < 0 ? n.left : n.right;
        }
        return NIL;
    }

  
    public void insert(T key) {
        Node z = new Node(key), y = NIL, x = root;
        while (x != NIL) {
            y = x;
            x = key.compareTo(x.key) < 0 ? x.left : x.right;
        }
        z.parent = y;
        if (y == NIL) {
            root = z; 
        }else if (key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }
        insertFix(z);
    }

    private void insertFix(Node z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y.color == Color.RED) {
                    z.parent.color = y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y.color == Color.RED) {
                    z.parent.color = y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK;
    }


    public void delete(T key) {
        Node z = searchNode(key);
        if (z == NIL) {
            return;
        }
        Node y = z, x;
        Color yOrig = y.color;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOrig = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y; 
            }else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOrig == Color.BLACK) {
            deleteFix(x);
        }
    }

    private void deleteFix(Node x) {
        while (x != root && x.color == Color.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.right.color == Color.BLACK) {
                        w.left.color = Color.BLACK;
                        w.color = Color.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.parent.color = Color.RED;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == Color.BLACK) {
                        w.right.color = Color.BLACK;
                        w.color = Color.RED;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = Color.BLACK;
                    w.left.color = Color.BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = Color.BLACK;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        
        }v.parent = u.parent;
    }

    private Node minimum(Node n) {
        while (n.left != NIL) {
            n = n.left;
        
        }return n;
    }


    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        
        }y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        
        }x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }


    public String toJson() {
        return toJson(root);
    }

    private String toJson(Node n) {
        if (n == NIL) {
            return "null";
        
        }return "{\"key\":" + n.key + ",\"color\":\"" + (n.color == Color.RED ? "red" : "black") + "\",\"left\":" + toJson(n.left) + ",\"right\":" + toJson(n.right) + "}";
    }

    public List<T> toList() {
        List<T> r = new ArrayList<>();
        inorder(root, r);
        return r;
    }

    private void inorder(Node n, List<T> out) {
        if (n == NIL) {
            return;
        
        }inorder(n.left, out);
        out.add(n.key);
        inorder(n.right, out);
    }

    public void inorderPrint() {
        inorderPrint(root);
        System.out.println();
    }

    private void inorderPrint(Node n) {
        if (n == NIL) {
            return;
        
        }inorderPrint(n.left);
        System.out.print(n.key + (n.color == Color.RED ? "R " : "B "));
        inorderPrint(n.right);
    }
}
