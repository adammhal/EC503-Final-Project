// js/main.js
document.addEventListener("DOMContentLoaded", () => {
  const insInput  = document.getElementById("insert-input");
  const btnIns    = document.getElementById("btn-insert");

  const delInput  = document.getElementById("delete-input");
  const btnDel    = document.getElementById("btn-delete");

  const findInput = document.getElementById("find-input");
  const btnFind   = document.getElementById("btn-find");

  const btnPrint  = document.getElementById("btn-print");
  const chkNull   = document.getElementById("chk-null");

  const viz       = document.getElementById("visualization");
  const out       = document.getElementById("output");

  const tree = new RBTree();

  // single render function
  function render() {
    drawTree(viz, tree.toJSON(), chkNull.checked);
  }

  // INITIAL DRAW
  render();

  // INSERT
  btnIns.addEventListener("click", () => {
    const k = Number(insInput.value);
    tree.insert(k);
    render();
  });

  // DELETE
  btnDel.addEventListener("click", () => {
    const k = Number(delInput.value);
    tree.delete(k);
    render();
  });

  // FIND
  btnFind.addEventListener("click", () => {
    const k = Number(findInput.value);
    const node = tree.search(k);
    if (node) {
      out.textContent = `Found key ${k}`;
      // Optionally: highlight node by extending drawTree
    } else {
      out.textContent = `Key ${k} not found`;
    }
    render();
  });

  // PRINT (in-order)
  btnPrint.addEventListener("click", () => {
    const result = [];
    (function inorder(n) {
      if (!n) return;
      inorder(n.left);
      result.push(n.key);
      inorder(n.right);
    })(tree.root === tree.nullLeaf ? null : tree.root);
    out.textContent = "In-order: " + result.join(", ");
  });

  // TOGGLE NULL LEAVES
  chkNull.addEventListener("change", render);
});
