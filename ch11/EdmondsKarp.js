/*
 * Classical Edmonds-Karp maximum flow: Ford-Fulkerson with the augmenting
 * path chosen by BREADTH-first search, so each augmentation uses a path
 * with the fewest arcs. That single change is what bounds the number of
 * augmentations by O(nm) and makes the running time independent of the
 * capacities.
 *
 * REPRESENTATION. The graph arrives as an edge list -- U[i], V[i], C[i]
 * for edge i -- and is turned into the standard PAIRED-ARC structure:
 * input edge i becomes arcs 2i (forward, capacity C[i]) and 2i+1
 * (reverse, capacity 0), adj[u] holds the ARC INDICES leaving u, and
 * head[e] is the arc's head. The reverse of arc e is
 * rev(e) = e + 1 - 2 * (e % 2)
 * (LL has no ^, so the usual e^1 is spelled out), which is what makes
 * "cancel the reverse arc first" an O(1) step.
 *
 * This replaces a capacity/flow MATRIX, and here the bound depends on it.
 * The book's O(n m^2) is O(nm) augmentations times O(m) per BFS. A matrix
 * makes every BFS scan all n candidate heads per vertex -- Theta(n^2)
 * whatever m is -- so the program would run in O(n^3 m), which equals the
 * book's bound only when m = Theta(n^2). On a sparse graph that is a
 * factor of n worse than the O(n m^2) printed beside it. The book writes
 * f(u,v) and c_f(u,v) throughout, never f[u,v]: a function of an edge,
 * not a matrix lookup.
 *
 * FLOW CONVENTION. flow stays NON-NEGATIVE -- a flow function, not an
 * antisymmetric skew matrix -- which is the book's own convention: its
 * inner loop reads `x := min(delta, f(v,u))`, cancels that much reverse
 * flow first, and only puts the remainder on (u,v). The matching residual
 * capacity is therefore
 * residual(e) = cap[e] - flow[e] + flow[rev(e)].
 *
 * The BFS carries its own queue q with head/tail indices and leaves the
 * path in parentArc[], as a chain of arcs running back from t to s.
 *
 * The returned array is indexed by INPUT edge: result[i] is the flow on
 * edge i, which is flow on arc 2i.
 *
 * Auto-generated from EdmondsKarp.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class EdmondsKarp {
  EdmondsKarp(n, U, V, C, s, t) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.C = C;
    this.s = s;
    this.t = t;
    this.m = this.U.length;
    this.head = new Array((2 * this.m)).fill(0);
    this.cap = new Array((2 * this.m)).fill(0);
    this.flow = new Array((2 * this.m)).fill(0);
    for (let i = 0; i <= ((this.m - 1)); i++) {
        this.head[(2 * i)] = this.V[i];
        this.cap[(2 * i)] = this.C[i];
        this.head[((2 * i) + 1)] = this.U[i];
        this.cap[((2 * i) + 1)] = 0;
    }
    let deg = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.m - 1)); i++) {
        deg[this.U[i]] = (deg[this.U[i]] + 1);
        deg[this.V[i]] = (deg[this.V[i]] + 1);
    }
    let adj = new Array(this.n).fill(null);
    for (let u = 0; u <= ((this.n - 1)); u++) {
        adj[u] = new Array(deg[u]).fill(0);
    }
    let fill = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.m - 1)); i++) {
        adj[this.U[i]][fill[this.U[i]]] = (2 * i);
        fill[this.U[i]] = (fill[this.U[i]] + 1);
        adj[this.V[i]][fill[this.V[i]]] = ((2 * i) + 1);
        fill[this.V[i]] = (fill[this.V[i]] + 1);
    }
    let parentArc = new Array(this.n).fill(0);
    while ((this.findAugmentingPath(this.n, adj, this.head, this.cap, this.flow, this.s, this.t, parentArc) === 1)) {
        this.augment(this.head, this.cap, this.flow, this.s, this.t, parentArc, this.bottleneck(this.head, this.cap, this.flow, this.s, this.t, parentArc));
    }
    let result = new Array(this.m).fill(0);
    for (let i = 0; i <= ((this.m - 1)); i++) {
        result[i] = this.flow[(2 * i)];
    }
    return result;
  }

  rev(e) {
    return ((e + 1) - (2 * (e % 2)));
  }

  residual(cap, flow, e) {
    return ((cap[e] - flow[e]) + flow[this.rev(e)]);
  }

  bottleneck(head, cap, flow, s, v, parentArc) {
    if ((v === s)) {
        return 2147483647;
    }
    let e = parentArc[v];
    return Math.min(this.residual(cap, flow, e), this.bottleneck(head, cap, flow, s, head[this.rev(e)], parentArc));
  }

  augment(head, cap, flow, s, v, parentArc, b) {
    if ((v === s)) {
        return;
    }
    let e = parentArc[v];
    let x = Math.min(b, flow[this.rev(e)]);
    if ((x > 0)) {
        flow[this.rev(e)] = (flow[this.rev(e)] - x);
    }
    if ((b > x)) {
        flow[e] = (flow[e] + (b - x));
    }
    this.augment(head, cap, flow, s, head[this.rev(e)], parentArc, b);
  }

  findAugmentingPath(n, adj, head, cap, flow, s, t, parentArc) {
    let seen = new Array(n).fill(false);
    let q = new Array(n).fill(0);
    q[0] = s;
    seen[s] = true;
    return this.bfs(adj, head, cap, flow, t, parentArc, seen, q, 0, 1);
  }

  bfs(adj, head, cap, flow, t, parentArc, seen, q, qh, qt) {
    if ((qh >= qt)) {
        return 0;
    }
    let u = q[qh];
    if ((u === t)) {
        return 1;
    }
    for (let z = 0; z <= ((adj[u].length - 1)); z++) {
        let e = adj[u][z];
        let v = head[e];
        if (((!seen[v]) && (this.residual(cap, flow, e) > 0))) {
            seen[v] = true;
            parentArc[v] = e;
            q[qt] = v;
            qt = (qt + 1);
        }
    }
    return this.bfs(adj, head, cap, flow, t, parentArc, seen, q, (qh + 1), qt);
  }
}

if (typeof module !== 'undefined') module.exports = EdmondsKarp;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const C = [0, 0, 0, 2];
    const prog = new EdmondsKarp();
    const result = prog.EdmondsKarp(U.length, U, V, C, 0, 1);
    console.log(JSON.stringify(result));
}
