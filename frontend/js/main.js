document.addEventListener("DOMContentLoaded", () => {
  const ins   = document.getElementById("insert-input");
  const del   = document.getElementById("delete-input");
  const find  = document.getElementById("find-input");

  const btnIns   = document.getElementById("btn-insert");
  const btnDel   = document.getElementById("btn-delete");
  const btnFind  = document.getElementById("btn-find");
  const btnPrint = document.getElementById("btn-print");

  const viz  = document.getElementById("visualization");
  const out  = document.getElementById("output");
  const chkNull = document.getElementById("chk-null");

  async function fetchTree(url) {
    const r = await fetch(url);
    if (!r.ok) { out.textContent = "Server error"; return null; }
    return (await r.json());
  }

  async function refresh(treeJson) {
    drawTree(viz, treeJson);                
  }

  async function postTree(cmd, value) {
    const res = await fetch('http://localhost:8080/tree', {
      method : 'POST',
      headers: { 'Content-Type': 'text/plain' },
      body   : `${cmd}=${value}`   // "insert=20", "delete=5", …
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();             // tree JSON or {"found":true}
  }

  const api = async (cmd, val) =>
    (await fetch('http://localhost:8080/tree', {
      method: 'POST',
      headers: { 'Content-Type': 'text/plain' },
      body: `${cmd}=${val}`,
  })).json();

  
  btnIns.onclick = async () => {
    const v = +document.getElementById('insert-input').value;
    drawTree(viz, await api('insert', v));
  };

  btnDel.onclick = async () => {
    const v = +document.getElementById('delete-input').value;
    drawTree(viz, await api('delete', v));
  };

  btnFind.onclick = async () => {
    const v   = +document.getElementById('find-input').value;
    const res = await api('search', v);
    alert(res.found ? `${v} is in the tree` : `${v} not found`);
  };

  btnPrint.onclick = async () => {
    const json = await fetchTree("http://localhost:8080/tree");
    if (!json) return;
    const inorder = [];
    (function dfs(n){ if(!n) return; dfs(n.left); inorder.push(n.key); dfs(n.right); })(json);
    out.textContent = "In Order Traversal: " + inorder.join(", ");
  };

  refresh(null);
});
