
// --- File: frontend/app.js ---
function showTab(tabId) {
  document.querySelectorAll('.tab-content').forEach(tab => {
    tab.style.display = 'none';
  });
  document.getElementById(tabId).style.display = 'block';
}

// Sorting
function startSort() {
  const input = document.getElementById("arrayInput").value;
  const algo = document.getElementById("algorithm").value;
  const arr = input.split(",").map(Number);

  fetch(`http://localhost:8080/api/sort/${algo}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(arr)
  })
    .then(res => res.json())
    .then(data => {
      const output = document.getElementById("output");
      output.innerHTML = "<h3>Sorting Steps:</h3>";
      data.forEach((step, i) => {
        const div = document.createElement("div");
        div.className = "step";
        div.textContent = `Step ${i + 1}: ${step.join(", ")}`;
        output.appendChild(div);
      });
    });
}

// Linked List
function insertAtHead() {
  const value = document.getElementById("llValue").value;
  fetch(`http://localhost:8080/api/ll/insertHead?value=${value}`, { method: "POST" })
    .then(res => res.json())
    .then(updateLL);
}

function insertAtTail() {
  const value = document.getElementById("llValue").value;
  fetch(`http://localhost:8080/api/ll/insertTail?value=${value}`, { method: "POST" })
    .then(res => res.json())
    .then(updateLL);
}

function deleteAtIndex() {
  const index = document.getElementById("llDeleteIndex").value;
  fetch(`http://localhost:8080/api/ll/delete/${index}`, { method: "DELETE" })
    .then(res => res.json())
    .then(updateLL);
}

function updateLL(data) {
  const out = document.getElementById("llOutput");
  out.innerHTML = `<p>Current List: ${data.join(" → ")}</p>`;
}

// Tree
function insertTreeNode() {
  const value = document.getElementById("treeValue").value;
  fetch(`http://localhost:8080/api/tree/insert?value=${value}`, { method: "POST" })
    .then(res => res.json())
    .then(updateTree);
}

function traverseTree() {
  const type = document.getElementById("treeTraversal").value;
  fetch(`http://localhost:8080/api/tree/traverse/${type}`)
    .then(res => res.json())
    .then(updateTree);
}

function updateTree(data) {
  const out = document.getElementById("treeOutput");
  out.innerHTML = `<p>Traversal: ${data.join(" → ")}</p>`;
}

// Graph
function addGraphNode() {
  const node = document.getElementById("graphNode").value;
  fetch(`http://localhost:8080/api/graph/addNode?node=${node}`, { method: "POST" })
    .then(res => res.json())
    .then(data => updateGraph("Nodes: " + data.join(", ")));
}

function addGraphEdge() {
  const from = document.getElementById("graphFrom").value;
  const to = document.getElementById("graphTo").value;
  fetch(`http://localhost:8080/api/graph/addEdge?from=${from}&to=${to}`, { method: "POST" })
    .then(res => res.json())
    .then(data => updateGraph("Edges: " + data.join("<br>\\")));
}

function bfsGraph() {
  const start = document.getElementById("graphStart").value;
  fetch(`http://localhost:8080/api/graph/bfs?start=${start}`)
    .then(res => res.json())
    .then(data => updateGraph("BFS: " + data.join(" → ")));
}

function dfsGraph() {
  const start = document.getElementById("graphStart").value;
  fetch(`http://localhost:8080/api/graph/dfs?start=${start}`)
    .then(res => res.json())
    .then(data => updateGraph("DFS: " + data.join(" → ")));
}

function updateGraph(content) {
  document.getElementById("graphOutput").innerHTML = `<p>${content}</p>`;
}
