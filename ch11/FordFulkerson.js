/*
 * Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
 * path in the residual graph by depth-first search and push the largest
 * amount that fits along it, until no augmenting path remains.
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
 * This replaces a capacity/flow MATRIX. The book's bound is O(f* m): each
 * augmenting-path search is O(m), because it walks the edges actually
 * present. A matrix forces every search to scan all n candidate heads per
 * vertex -- Theta(n^2) per search no matter how few edges there are --
 * which makes the program O(f* n^2) and matches the book only on a dense
 * graph. The book writes f(u,v) and c_f(u,v) throughout, never f[u,v]:
 * a function of an edge, not a matrix lookup.
 *
 * FLOW CONVENTION. flow stays NON-NEGATIVE -- a flow function, not an
 * antisymmetric skew matrix -- which is the book's own convention: its
 * inner loop reads `x := min(delta, f(v,u))`, cancels that much reverse
 * flow first, and only puts the remainder on (u,v). The matching residual
 * capacity is therefore
 * residual(e) = cap[e] - flow[e] + flow[rev(e)].
 *
 * The returned array is indexed by INPUT edge: result[i] is the flow on
 * edge i, which is flow on arc 2i.
 *
 * Auto-generated from FordFulkerson.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FordFulkerson {
  FordFulkerson(n, U, V, C, s, t) {
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
    seen[s] = true;
    return this.dfs(adj, head, cap, flow, s, t, parentArc, seen);
  }

  dfs(adj, head, cap, flow, u, t, parentArc, seen) {
    if ((u === t)) {
        return 1;
    }
    for (let q = 0; q <= ((adj[u].length - 1)); q++) {
        let e = adj[u][q];
        let v = head[e];
        if (((!seen[v]) && (this.residual(cap, flow, e) > 0))) {
            seen[v] = true;
            parentArc[v] = e;
            if ((this.dfs(adj, head, cap, flow, v, t, parentArc, seen) === 1)) {
                return 1;
            }
        }
    }
    return 0;
  }
}

if (typeof module !== 'undefined') module.exports = FordFulkerson;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const C = [0, 0, 0, 2];
    const prog = new FordFulkerson();
    const result = prog.FordFulkerson(U.length, U, V, C, 0, 1);
    console.log(JSON.stringify(result));
}
