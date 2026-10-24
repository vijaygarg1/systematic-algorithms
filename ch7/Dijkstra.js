/*
 * Classical Dijkstra (bxx-shortestPath.tex, fig:dijk): binary min-heap
 * H of (vertex, cost) pairs keyed by cost, with lazy deletion -- a
 * vertex may be pushed more than once as its distance improves; stale
 * entries are simply skipped on removal via the fixed[] check -- all
 * exactly matching the book's H.add()/H.removeMin() algorithm, not a
 * linear O(n) scan for the minimum each round. Uses the heap<int>
 * builtin (-> java.util.PriorityQueue<int[]>, keyed by cost, carrying
 * vertex as the payload) instead of hand-rolled heapPush/heapPop/
 * heapify, matching the hand-written Python (heapq) and Rust
 * (BinaryHeap) versions of this same program, which already lean on
 * their standard library's heap.
 *
 * The graph is a weighted adjacency list: adj[j] holds the out-neighbours
 * of j and the parallel aw[j] holds their edge weights, so the weight of
 * (j, adj[j][t]) is aw[j][t]. There is no n x n matrix anywhere, so the
 * whole structure is O(n + m) rather than Theta(n^2).
 *
 * The relaxation loop walks adj[j], which is the book's
 * "forall k: not fixed[k] and (j,k) in E". That is what makes the
 * algorithm O(m log n): each edge is relaxed once and contributes at most
 * one heap push. Scanning all n vertices instead -- reading weights out
 * of a dense matrix -- costs Theta(n^2) however few edges the graph has,
 * and so cannot meet the book's bound on a sparse graph.
 *
 * Auto-generated from Dijkstra.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Dijkstra {
  shortestPath(adj, aw, s) {
    this.adj = adj;
    this.aw = aw;
    this.s = s;
    this.n = this.adj.length;
    this.INF = 2147483647;
    this.dist = new Array(this.n).fill(0);
    this.fixed = new Array(this.n).fill(false);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.dist[i] = this.INF;
    }
    this.dist[this.s] = 0;
    let H = [];
    this._heapPush(H, this.dist[this.s], this.s);
    while ((!(H.length === 0))) {
        let j = H[0][1];
        this._heapPop(H);
        if ((!this.fixed[j])) {
            this.fixed[j] = true;
            let t = 0;
            while ((t < this.adj[j].length)) {
                let k = this.adj[j][t];
                if (((!this.fixed[k]) && ((this.dist[j] + this.aw[j][t]) < this.dist[k]))) {
                    this.dist[k] = (this.dist[j] + this.aw[j][t]);
                    this._heapPush(H, this.dist[k], k);
                }
                t = (t + 1);
            }
        }
    }
    return this.dist;
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

if (typeof module !== 'undefined') module.exports = Dijkstra;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new Dijkstra();
    const result = prog.shortestPath(adj, aw, 0);
    console.log(JSON.stringify(result));
}
