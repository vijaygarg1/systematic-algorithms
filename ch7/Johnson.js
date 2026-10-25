/*
 * Classical Johnson all-pairs shortest paths: BellmanFord computes a
 * non-negative price vector that reweights edges, then Dijkstra runs from
 * every source on the reweighted graph. Returns null on negative cycle.
 *
 * Calls the sibling Dijkstra program (`Dijkstra(adj, wPrime, u)`, compiling
 * to `new Dijkstra().shortestPath(adj, wPrime, u)`) instead of keeping its own
 * copy of the single-source relaxation loop -- confirmed correct against
 * the standard CLRS Johnson's-algorithm worked example. This makes
 * Johnson.java a multi-file program: it requires Dijkstra.java alongside
 * it to compile (validate-site.py's Java check and the website's
 * download links both know about this dependency; see JAVA_MULTI_FILE
 * in validate-site.py and the `javaRequires` demo-spec field in llp.js).
 *
 * The book's box says "Run BellmanFord" and "Run Dijkstra" symmetrically,
 * but only Dijkstra is called here; step 1 keeps its own relaxation loop.
 * That is a capability gap, not an oversight. Sibling BellmanFord.llp
 * takes an edge list -- shortestPath(n, U, V, W, s) -- where Johnson has
 * an adjacency matrix, and it runs its n-1 passes and returns dist with
 * no extra pass and no way to report a negative cycle. Johnson needs
 * exactly that report, so calling it would drop the check the book names
 * explicitly.
 *
 * The graph is a weighted adjacency list: adj[i] holds the out-neighbours
 * of i and the parallel aw[i] their edge weights, so the weight of
 * (i, adj[i][t]) is aw[i][t]. The reweighted graph is stored the same way,
 * in awPrime, which shares adj since reweighting changes no edge -- so the
 * only Theta(n^2) object left is the all-pairs output D itself, which is
 * Theta(n^2) by definition.
 *
 * Both relaxation loops walk adj[i] rather than all n candidate heads.
 * That is what gives the book's O(nm log n): step 1 is n-1 passes over m
 * edges, and step 4 is n Dijkstra runs of O(m log n) each. Scanning all
 * pairs instead makes step 1 Theta(n^3) on its own, which already exceeds
 * the bound on any sparse graph.
 *
 * No edge weight is ever INF here: every pair in adj is a real edge, so
 * the "w[i][j] < INF" guards the matrix version needed are gone.
 *
 * Auto-generated from Johnson.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Dijkstra {
  Dijkstra(adj, aw, s) {
    this.adj = adj;
    this.aw = aw;
    this.s = s;
    this.n = this.adj.length;
    this.d = new Array(this.n).fill(0);
    this.fixed = new Array(this.n).fill(false);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.d[i] = 2147483647;
    }
    this.d[this.s] = 0;
    let H = [];
    this._heapPush(H, this.d[this.s], this.s);
    while ((!(H.length === 0))) {
        let j = H[0][1];
        this._heapPop(H);
        if ((!this.fixed[j])) {
            this.fixed[j] = true;
            for (let t = 0; t <= ((this.adj[j].length - 1)); t++) {
                let k = this.adj[j][t];
                if (((!this.fixed[k]) && ((this.d[j] + this.aw[j][t]) < this.d[k]))) {
                    this.d[k] = (this.d[j] + this.aw[j][t]);
                    this._heapPush(H, this.d[k], k);
                }
            }
        }
    }
    return this.d;
  }

  // Binary min-heap of [key, value] pairs, ordered by key and
  // then by value -- the order the Java PriorityQueue<int[]>
  // comparator gives. JavaScript has no heap in its standard
  // library, so the three operations the program uses are
  // carried with it.
  _heapLess(a, b) {
    return a[0] !== b[0] ? a[0] < b[0] : a[1] < b[1];
  }

  _heapPush(h, k, v) {
    h.push([k, v]);
    let i = h.length - 1;
    while (i > 0) {
      const p = Math.trunc((i - 1) / 2);
      if (!this._heapLess(h[i], h[p])) break;
      const t = h[i]; h[i] = h[p]; h[p] = t;
      i = p;
    }
  }

  _heapPop(h) {
    const top = h[0];
    const last = h.pop();
    if (h.length > 0) {
      h[0] = last;
      let i = 0;
      while (true) {
        const l = 2 * i + 1, r = l + 1;
        let m = i;
        if (l < h.length && this._heapLess(h[l], h[m])) m = l;
        if (r < h.length && this._heapLess(h[r], h[m])) m = r;
        if (m === i) break;
        const t = h[i]; h[i] = h[m]; h[m] = t;
        i = m;
      }
    }
    return top;
  }
}

class Johnson {
  Johnson(adj, aw) {
    this.adj = adj;
    this.aw = aw;
    this.n = this.adj.length;
    this.INF = 2147483647;
    this.dist = new Array(this.n).fill(0);
    this.k = 0;
    while ((this.k < (this.n - 1))) {
        let changed = false;
        for (let i = 0; i <= ((this.n - 1)); i++) {
            for (let t = 0; t <= ((this.adj[i].length - 1)); t++) {
                let j = this.adj[i][t];
                if (((this.dist[i] + this.aw[i][t]) < this.dist[j])) {
                    this.dist[j] = (this.dist[i] + this.aw[i][t]);
                    changed = true;
                }
            }
        }
        if (changed) {
            this.k = (this.k + 1);
        } else {
            this.k = this.n;
        }
    }
    let negCycle = false;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        for (let t = 0; t <= ((this.adj[i].length - 1)); t++) {
            if (((this.dist[i] + this.aw[i][t]) < this.dist[this.adj[i][t]])) {
                negCycle = true;
            }
        }
    }
    if (negCycle) {
        return null;
    }
    let price = new Array(this.n).fill(0);
    for (let v = 0; v <= ((this.n - 1)); v++) {
        price[v] = (0 - this.dist[v]);
    }
    let awPrime = new Array(this.n).fill(null);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        awPrime[i] = new Array(this.adj[i].length).fill(0);
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        for (let t = 0; t <= ((this.adj[i].length - 1)); t++) {
            awPrime[i][t] = this.reweight(this.aw[i][t], price[this.adj[i][t]], price[i]);
        }
    }
    let D = Array.from({length: this.n}, () => new Array(this.n).fill(0));
    for (let u = 0; u <= ((this.n - 1)); u++) {
        let distPrime = new Dijkstra().Dijkstra(this.adj, awPrime, u);
        for (let v = 0; v <= ((this.n - 1)); v++) {
            D[u][v] = this.reweight(distPrime[v], price[u], price[v]);
        }
    }
    return D;
  }

  reweight(len_, plus, minus) {
    if ((len_ >= 2147483647)) {
        return 2147483647;
    }
    return ((len_ + plus) - minus);
  }
}

if (typeof module !== 'undefined') module.exports = Johnson;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new Johnson();
    const result = prog.Johnson(adj, aw);
    console.log(JSON.stringify(result));
}
