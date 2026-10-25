/*
 * Classical Boruvka minimum spanning tree: in each round every current
 * component picks its own cheapest outgoing edge and all those edges are
 * added at once, so the number of components at least halves per round
 * and O(log n) rounds suffice.
 *
 * T marks the chosen edges; cid[v] is the representative of v's component,
 * recomputed each round by flooding the edges already in T. A round that
 * adds no edge means the graph is disconnected, which is what progress
 * stops on -- otherwise the loop would spin forever below n-1 edges.
 *
 * Auto-generated from Boruvka.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Boruvka {
  Boruvka(n, U, V, W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.m = this.U.length;
    this.treeEdges = 0;
    this.T = new Array(this.m).fill(false);
    this.cid = new Array(this.n).fill(0);
    this.progress = true;
    while (((this.treeEdges < (this.n - 1)) && this.progress)) {
        let roundStart = this.treeEdges;
        this.components(this.n, this.U, this.V, this.T, this.cid);
        let mwe = new Array(this.n).fill(0);
        let dist = new Array(this.n).fill(0.0);
        for (let i = 0; i <= ((this.n - 1)); i++) {
            mwe[i] = (-1);
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            dist[i] = Infinity;
        }
        for (let e = 0; e <= ((this.m - 1)); e++) {
            if ((this.cid[this.U[e]] !== this.cid[this.V[e]])) {
                this.relax(this.cid[this.U[e]], e, this.W, dist, mwe);
                this.relax(this.cid[this.V[e]], e, this.W, dist, mwe);
            }
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if ((((this.cid[i] === i) && (mwe[i] !== (-1))) && (!this.T[mwe[i]]))) {
                this.T[mwe[i]] = true;
                this.treeEdges = (this.treeEdges + 1);
            }
        }
        this.progress = (this.treeEdges > roundStart);
    }
    return this.T;
  }

  relax(comp, e, W, dist, mwe) {
    if ((W[e] < dist[comp])) {
        dist[comp] = W[e];
        mwe[comp] = e;
    }
  }

  components(n, U, V, T, cid) {
    let visited = new Array(n).fill(false);
    let deg = new Array(n).fill(0);
    let fill = new Array(n).fill(0);
    let tadj = new Array(n).fill(null);
    for (let e = 0; e <= ((U.length - 1)); e++) {
        if (T[e]) {
            deg[U[e]] = (deg[U[e]] + 1);
            deg[V[e]] = (deg[V[e]] + 1);
        }
    }
    for (let i = 0; i <= ((n - 1)); i++) {
        tadj[i] = new Array(deg[i]).fill(0);
    }
    for (let e = 0; e <= ((U.length - 1)); e++) {
        if (T[e]) {
            tadj[U[e]][fill[U[e]]] = V[e];
            fill[U[e]] = (fill[U[e]] + 1);
            tadj[V[e]][fill[V[e]]] = U[e];
            fill[V[e]] = (fill[V[e]] + 1);
        }
    }
    for (let i = 0; i <= ((n - 1)); i++) {
        if ((!visited[i])) {
            this.bfs(i, i, visited, cid, tadj);
        }
    }
  }

  bfs(v, root, visited, cid, tadj) {
    visited[v] = true;
    cid[v] = root;
    for (let q = 0; q <= ((tadj[v].length - 1)); q++) {
        let u = tadj[v][q];
        if ((!visited[u])) {
            this.bfs(u, root, visited, cid, tadj);
        }
    }
  }

  neighborIf(a, b, v) {
    if ((a === v)) {
        return b;
    }
    if ((b === v)) {
        return a;
    }
    return (-1);
  }
}

if (typeof module !== 'undefined') module.exports = Boruvka;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new Boruvka();
    const result = prog.Boruvka(U.length, U, V, W);
    console.log(JSON.stringify(result));
}
