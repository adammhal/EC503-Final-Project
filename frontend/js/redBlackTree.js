// js/redBlackTree.js

class Node {
  constructor(key = null, color = 'black', left = null, right = null, parent = null) {
    this.key    = key;
    this.color  = color;
    this.left   = left;
    this.right  = right;
    this.parent = parent;
  }
}

class RBTree {
  constructor() {
    this.nullLeaf = new Node(null, 'black');
    this.root     = this.nullLeaf;
  }

  // ─── ROTATIONS ─────────────────────────────────────────────────────────────

  leftRotate(x) {
    const y = x.right;
    x.right = y.left;
    if (y.left !== this.nullLeaf) y.left.parent = x;
    y.parent = x.parent;
    if (!x.parent) this.root = y;
    else if (x === x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    y.left = x; x.parent = y;
  }

  rightRotate(y) {
    const x = y.left;
    y.left = x.right;
    if (x.right !== this.nullLeaf) x.right.parent = y;
    x.parent = y.parent;
    if (!y.parent) this.root = x;
    else if (y === y.parent.right) y.parent.right = x;
    else y.parent.left = x;
    x.right = y; y.parent = x;
  }
  

  // ─── INSERT ───────────────────────────────────────────────────────────────
  insert(key) {
    let z = new Node(key, 'red', this.nullLeaf, this.nullLeaf, null);
    let y = null, x = this.root;

    // BST-insert
    while (x !== this.nullLeaf) {
      y = x;
      x = key < x.key ? x.left : x.right;
    }
    z.parent = y;
    if (!y)            this.root = z;
    else if (key < y.key) y.left  = z;
    else                  y.right = z;

    // Fix-up
    if (!z.parent) {
      z.color = 'black'; return;
    }
    if (!z.parent.parent) return;
    this.fixInsert(z);
  }

  fixInsert(z) {
    while (z.parent.color === 'red') {
      const gp = z.parent.parent;
      if (z.parent === gp.left) {
        const uncle = gp.right;
        if (uncle.color === 'red') {
          z.parent.color = uncle.color = 'black';
          gp.color = 'red';
          z = gp;
        } else {
          if (z === z.parent.right) {
            z = z.parent; this.leftRotate(z);
          }
          z.parent.color = 'black';
          gp.color        = 'red';
          this.rightRotate(gp);
        }
      } else {
        const uncle = gp.left;
        if (uncle.color === 'red') {
          z.parent.color = uncle.color = 'black';
          gp.color = 'red';
          z = gp;
        } else {
          if (z === z.parent.left) {
            z = z.parent; this.rightRotate(z);
          }
          z.parent.color = 'black';
          gp.color        = 'red';
          this.leftRotate(gp);
        }
      }
      if (z === this.root) break;
    }
    this.root.color = 'black';
  }

    // ─── Helper: replace subtree u with subtree v ──────────────────
    transplant(u, v) {
      if (!u.parent)          this.root = v;
      else if (u === u.parent.left)  u.parent.left  = v;
      else                       u.parent.right = v;
      v.parent = u.parent;
    }
  
    // ─── Helper: find minimum in subtree ────────────────────────
    minimum(x) {
      while (x.left !== this.nullLeaf) {
        x = x.left;
      }
      return x;
    }
  

  // ─── SEARCH ───────────────────────────────────────────────────────────────
  search(key) {
    let cur = this.root;
    while (cur !== this.nullLeaf && cur.key !== key) {
      cur = key < cur.key ? cur.left : cur.right;
    }
    return cur === this.nullLeaf ? null : cur;
  }

  // ─── DELETE (stub) ─────────────────────────────────────────────────────────
  delete(key) {
    // 1) Locate node z to delete
    let z = this.root;
    while (z !== this.nullLeaf && z.key !== key) {
      z = key < z.key ? z.left : z.right;
    }
    if (z === this.nullLeaf) return;

    // 2) Standard BST removal with y = the node actually removed
    let y       = z;
    let yOrigColor = y.color;
    let x;  // will point to the child that replaces y

    if (z.left === this.nullLeaf) {
      x = z.right;
      this.transplant(z, z.right);
    } else if (z.right === this.nullLeaf) {
      x = z.left;
      this.transplant(z, z.left);
    } else {
      y = this.minimum(z.right);
      yOrigColor = y.color;
      x = y.right;
      if (y.parent === z) {
        x.parent = y;
      } else {
        this.transplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      this.transplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }

    // 3) Fix double-black if we removed a black node
    if (yOrigColor === 'black') {
      this.fixDelete(x);
    }
  }

  fixDelete(x) {
    while (x !== this.root && x.color === 'black') {
      if (x === x.parent.left) {
        let w = x.parent.right;
        if (w.color === 'red') {
          w.color = 'black';
          x.parent.color = 'red';
          this.leftRotate(x.parent);
          w = x.parent.right;
        }
        if (w.left.color === 'black' && w.right.color === 'black') {
          w.color = 'red';
          x = x.parent;
        } else {
          if (w.right.color === 'black') {
            w.left.color = 'black';
            w.color = 'red';
            this.rightRotate(w);
            w = x.parent.right;
          }
          w.color = x.parent.color;
          x.parent.color = 'black';
          w.right.color = 'black';
          this.leftRotate(x.parent);
          x = this.root;
        }
      } else {
        // mirror cases when x is right child
        let w = x.parent.left;
        if (w.color === 'red') {
          w.color = 'black';
          x.parent.color = 'red';
          this.rightRotate(x.parent);
          w = x.parent.left;
        }
        if (w.right.color === 'black' && w.left.color === 'black') {
          w.color = 'red';
          x = x.parent;
        } else {
          if (w.left.color === 'black') {
            w.right.color = 'black';
            w.color = 'red';
            this.leftRotate(w);
            w = x.parent.left;
          }
          w.color = x.parent.color;
          x.parent.color = 'black';
          w.left.color = 'black';
          this.rightRotate(x.parent);
          x = this.root;
        }
      }
    }
    x.color = 'black';
  }


  // ─── SERIALIZATION ─────────────────────────────────────────────────────────
  toJSON(node = this.root) {
    if (node === this.nullLeaf || node.key == null) return null;
    return {
      key:   node.key,
      color: node.color,
      left:  this.toJSON(node.left),
      right: this.toJSON(node.right)
    };
  }
}

window.RBTree = RBTree;
