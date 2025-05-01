// js/drawTree.js

function drawTree(_, treeJSON) {
  const svg = document.getElementById('tree-svg');
  // clear
  while (svg.firstChild) svg.removeChild(svg.firstChild);

  const levelH = 80;  // vertical spacing

  function recurse(n, x, y, s) {
    if (!n) return;
    // left branch
    if (n.left) {
      const lx = x - s, ly = y + levelH;
      svg.appendChild(makeLine(x, y, lx, ly));
      recurse(n.left, lx, ly, s/2);
    }
    // right branch
    if (n.right) {
      const rx = x + s, ry = y + levelH;
      svg.appendChild(makeLine(x, y, rx, ry));
      recurse(n.right, rx, ry, s/2);
    }
    // node
    svg.appendChild(makeNode(x, y, n.key, n.color));
  }

  const W = svg.clientWidth;
  recurse(treeJSON, W/2, 120, W/4);  // root at y=120 to clear header+controls
}

function makeNode(cx, cy, key, color) {
  const ns = "http://www.w3.org/2000/svg";
  const g  = document.createElementNS(ns, "g");
  g.classList.add("node");

  const c = document.createElementNS(ns, "circle");
  c.setAttribute("cx", cx);
  c.setAttribute("cy", cy);
  c.setAttribute("r", 20);
  c.setAttribute("fill", color);
  c.setAttribute("stroke", "#333");
  g.appendChild(c);

  const t = document.createElementNS(ns, "text");
  t.setAttribute("x", cx);
  t.setAttribute("y", cy + 5);
  t.setAttribute("text-anchor", "middle");
  t.setAttribute("fill", "#fff");
  t.textContent = key;
  g.appendChild(t);

  return g;
}

function makeLine(x1, y1, x2, y2) {
  const ns = "http://www.w3.org/2000/svg";
  const l  = document.createElementNS(ns, "line");
  l.setAttribute("x1", x1);
  l.setAttribute("y1", y1 + 20);
  l.setAttribute("x2", x2);
  l.setAttribute("y2", y2 - 20);
  l.setAttribute("stroke", "#555");
  l.setAttribute("stroke-width", "2");
  return l;
}

window.drawTree = drawTree;
