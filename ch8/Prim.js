/*
 * Classical Prim MST (bxx-mst.tex, algo:prim): d[v] is the cost of adding
 * v to the fragment, parent[v] the fragment vertex responsible for it,
 * fixed[v] marks vertices already in the fragment. Same control structure
 * as Dijkstra (bxx-shortestPath.tex's "Comparison with Dijkstra's
 * algorithm" paragraph): a heap<int> keyed by d finds the minimum
 *
 * Auto-generated from Prim.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Prim {
  mst(adj, aw) {
    this.adj = adj;
    this.aw = aw;
    this.n = this.adj.length;
    this.d = new Array(this.n).fill(0);
    this.parent = new Array(this.n).fill(0);
    this.fixed = new Array(this.n).fill(false);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.d[i] = 2147483647;
        this.parent[i] = (-1);
    }
    this.d[0] = 0;
    let H = [];
    this._heapPush(H, this.d[0], 0);
    let count = 0;
    while ((count < this.n)) {
        if ((H.length === 0)) {
            return null;
        }
        let v = H[0][1];
        this._heapPop(H);
        if ((!this.fixed[v])) {
            this.fixed[v] = true;
            count = (count + 1);
            let t = 0;
            while ((t < this.adj[v].length)) {
                let k = this.adj[v][t];
                if (((!this.fixed[k]) && (this.aw[v][t] < this.d[k]))) {
                    this.d[k] = this.aw[v][t];
                    this.parent[k] = v;
                    this._heapPush(H, this.d[k], k);
                }
                t = (t + 1);
            }
        }
    }
    return this.parent;
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

if (typeof module !== 'undefined') module.exports = Prim;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const aw = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new Prim();
    const result = prog.mst(adj, aw);
    console.log(JSON.stringify(result));
}
