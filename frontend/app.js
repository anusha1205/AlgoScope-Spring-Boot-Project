// === app.js ===

// 1. Utility Functions ----------------------------------------------------
function showToast(message) {
    const toast = document.createElement("div");
    toast.className = "toast";
    toast.textContent = message;
    document.body.appendChild(toast);
    setTimeout(() => toast.remove(), 2500);
}

function showTab(tabId) {
    document.querySelectorAll('.tab-content').forEach(t => t.style.display = 'none');
    document.getElementById(tabId).style.display = 'block';
}

function confirmAndReset(url, outputId, message) {
    if (confirm("Are you sure you want to reset?")) {
        fetch(url, { method: "POST" }).then(() => {
            document.getElementById(outputId).innerHTML = message;
            showToast("Reset successful!");
            if (url.includes("/tree")) document.getElementById("treeSVG").innerHTML = "";
            if (url.includes("/graph")) document.getElementById("graphSVG").innerHTML = "";
        });
    }
}

// 2. Sorting Visualizer ----------------------------------------------------
function startSort() {
    const arr = document.getElementById("arrayInput").value.split(",").map(Number);
    const container = document.getElementById("barContainer");
    container.innerHTML = "";

    arr.forEach(v => {
        const bar = document.createElement("div");
        bar.className = "bar";
        bar.style.height = `${v * 15}px`;

        // Create the value element and position it on top of the bar
        const value = document.createElement("span");
        value.className = "bar-value";
        value.textContent = v;
        bar.appendChild(value);

        container.appendChild(bar);
    });

    fetch(`http://localhost:8080/api/sort/${document.getElementById("algorithm").value}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(arr)
    })
        .then(r => r.json())
        .then(animateBars);
}

function animateBars(steps) {
    const bars = document.querySelectorAll(".bar");
    steps.forEach((step, i) => {
        setTimeout(() => {
            step.forEach((v, j) => {
                bars[j].style.width = `${40}px`;
                bars[j].style.height = `${v * 35}px`;  // Updated the multiplier back to 15
                bars[j].querySelector(".bar-value").textContent = v;  // Update the value on the bar
                bars[j].classList.add("active");
            });

            setTimeout(() => {
                bars.forEach(b => b.classList.remove("active"));
            }, 300);
        }, i * 1100); // Reduced the speed by increasing the timeout
    });
}



// 3. Linked List -----------------------------------------------------------
function resetLinkedList() {
    confirmAndReset('http://localhost:8080/api/ll/reset', 'llOutput', '<p>Current List: (empty)</p>');
}
function insertAtHead() {
    const v = document.getElementById('llValue').value;
    fetch(`http://localhost:8080/api/ll/insertHead?value=${v}`, { method: 'POST' })
        .then(r => r.json()).then(updateLL);
}
function insertAtTail() {
    const v = document.getElementById('llValue').value;
    fetch(`http://localhost:8080/api/ll/insertTail?value=${v}`, { method: 'POST' })
        .then(r => r.json()).then(updateLL);
}
function deleteAtIndex() {
    const idx = document.getElementById('llDeleteIndex').value;
    fetch(`http://localhost:8080/api/ll/delete/${idx}`, { method: 'DELETE' })
        .then(r => r.json()).then(updateLL);
}
function updateLL(list) {
    document.getElementById('llOutput').innerHTML = `<p>Current List: ${list.join(' → ')}</p>`;
    drawLinkedList(list);
}
function drawLinkedList(arr) {
    const svg = document.getElementById('llSVG'); svg.innerHTML = '';
    const startX = 30, startY = 50, gapX = 100;
    arr.forEach((v, i) => {
        const x = startX + i * gapX;
        svg.innerHTML += `<circle cx='${x}' cy='${startY}' r='20' fill='#4caf50'/><text x='${x}' y='${startY + 5}' fill='white' text-anchor='middle'>${v}</text>`;
        if (i < arr.length - 1) {
            svg.innerHTML += `<line x1='${x + 20}' y1='${startY}' x2='${x + gapX - 20}' y2='${startY}' stroke='white' marker-end='url(#arrow)'/>`;
        }
    });
}

// 4. Tree Visualizer -------------------------------------------------------
function insertTreeNode() {
    fetch(`http://localhost:8080/api/tree/insert?value=${document.getElementById('treeValue').value}`, { method: 'POST' })
        .then(r => r.json())
        .then(data => {
            updateTree(data);
            fetchAndDrawTree();              // ← redraw SVG immediately
        });
}
function traverseTree() {
    fetch(`http://localhost:8080/api/tree/traverse/${document.getElementById("treeTraversal").value}`)
        .then(r => r.json())
        .then(updateTree);
}
function updateTree(data) {
    document.getElementById("treeOutput").innerHTML = `<p>Traversal: ${data.join(" → ")}</p>`;
}
function resetTree() {
    confirmAndReset(
        "http://localhost:8080/api/tree/reset",
        "treeOutput",
        "<p>Traversal: (empty)</p>"
    );
}
function fetchAndDrawTree() {
    fetch("http://localhost:8080/api/tree/structure")
        .then(r => r.json())
        .then(drawTree);
}
function drawTree(root) {
    const svg = document.getElementById("treeSVG");
    svg.innerHTML = "";
    const spacingX = 50, spacingY = 70;
    const positions = {};
    let idx = 0;
    (function assign(node, d) {
        if (!node) return;
        assign(node.left, d + 1);
        positions[node.val] = { x: idx++ * spacingX + 30, y: d * spacingY + 30 };
        assign(node.right, d + 1);
    })(root, 0);
    (function connect(node) {
        if (!node) return;
        const p = positions[node.val];
        if (node.left) {
            const c = positions[node.left.val];
            svg.innerHTML += `<line x1="${p.x}" y1="${p.y}" x2="${c.x}" y2="${c.y}" stroke="white"/>`;
        }
        if (node.right) {
            const c = positions[node.right.val];
            svg.innerHTML += `<line x1="${p.x}" y1="${p.y}" x2="${c.x}" y2="${c.y}" stroke="white"/>`;
        }
        connect(node.left);
        connect(node.right);
    })(root);
    (function draw(node) {
        if (!node) return;
        const p = positions[node.val];
        svg.innerHTML += `<circle cx="${p.x}" cy="${p.y}" r="20" fill="#4caf50"/><text x="${p.x}" y="${p.y + 5}" text-anchor="middle" fill="white">${node.val}</text>`;
        draw(node.left);
        draw(node.right);
    })(root);
}

// 5. Graph Visualizer ------------------------------------------------------
function addGraphNode() {
    const n = document.getElementById("graphNode").value.trim();
    if (!n) return showToast("Enter a node name");
    fetch(`http://localhost:8080/api/graph/addNode?node=${n}`, { method: "POST" })
        .then(fetchAndDrawGraph);
}
function addGraphEdge() {
    const f = document.getElementById("graphFrom").value.trim();
    const t = document.getElementById("graphTo").value.trim();
    if (!f || !t) return showToast("Enter both From and To");
    fetch(`http://localhost:8080/api/graph/addEdge?from=${f}&to=${t}`, { method: "POST" })
        .then(fetchAndDrawGraph);
}
function resetGraph() {
    fetch("http://localhost:8080/api/graph/reset", { method: "POST" })
        .then(() => {
            document.getElementById("bfsOutput").innerHTML = "<strong>BFS:</strong> (reset)";
            document.getElementById("dfsOutput").innerHTML = "<strong>DFS:</strong> (reset)";
            document.getElementById("graphSVG").innerHTML = "";
        });
}
function fetchAndDrawGraph() {
    fetch("http://localhost:8080/api/graph/structure")
        .then(r => r.json())
        .then(drawGraph);
}
function drawGraph(data) {
    const svg = document.getElementById("graphSVG");
    svg.innerHTML = "";
    const nodes = Object.keys(data);
    const cx = 300, cy = 200, r = 150;
    const pos = {};
    nodes.forEach((n, i) => {
        const a = 2 * Math.PI * i / nodes.length;
        pos[n] = { x: cx + r * Math.cos(a), y: cy + r * Math.sin(a) };
    });
    nodes.forEach(from => data[from].forEach(to => {
        const A = pos[from], B = pos[to];
        svg.innerHTML += `<line x1="${A.x}" y1="${A.y}" x2="${B.x}" y2="${B.y}" stroke="white"/>`;
    }));
    nodes.forEach(n => {
        const { x, y } = pos[n];
        svg.innerHTML += `<circle id="node-${n}" cx="${x}" cy="${y}" r="20" fill="#2196f3"/><text x="${x}" y="${y + 5}" text-anchor="middle" fill="white">${n}</text>`;
    });
}
function animateGraphTraversal(path) {
    fetchAndDrawGraph();
    path.forEach((n, i) => setTimeout(() => {
        const c = document.getElementById("node-" + n);
        if (c) c.setAttribute("fill", "orange");
    }, i * 500));
}
function bfsGraph() {
    const s = document.getElementById("graphStart").value.trim();
    if (!s) return showToast("Enter a start node");
    fetch(`http://localhost:8080/api/graph/bfs?start=${s}`)
        .then(r => r.json())
        .then(path => {
            document.getElementById("bfsOutput").innerHTML = `<strong>BFS:</strong> ${path.join(" → ")}`;
            animateGraphTraversal(path);
        });
}
function dfsGraph() {
    const s = document.getElementById("graphStart").value.trim();
    if (!s) return showToast("Enter a start node");
    fetch(`http://localhost:8080/api/graph/dfs?start=${s}`)
        .then(r => r.json())
        .then(path => {
            document.getElementById("dfsOutput").innerHTML = `<strong>DFS:</strong> ${path.join(" → ")}`;
            animateGraphTraversal(path);
        });
}

// Init on load -------------------------------------------------------------
window.addEventListener("DOMContentLoaded", () => {
    fetchAndDrawTree();
    fetchAndDrawGraph();
});
