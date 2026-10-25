/*
 * LLP-Dijkstra: forbidden when j is not yet fixed and its distance is
 * finite; the advance fixes j and relaxes every edge out of it. The
 * setContainer line gives the LLP loop a priority queue keyed on G[j],
 * so the forbidden index chosen is always the closest unfixed vertex --
 * which is exactly Dijkstra's greedy choice.
 *
 * The book writes the weight as w(i,j), with parentheses, to say that it
 * comes from the edge and not from an n x n array -- an array would cost
 * Theta(n^2) to build before Dijkstra's own O(m log n) could start. So the
 * edges arrive as two parallel ragged arrays: adj[j] lists j's successors
 * and aw[j][t] is the weight of the edge j -> adj[j][t]. Both are O(m).
 * Dijkstra.llp and LLPJohnson.llp use the same pair.
 *
 * Auto-generated from LLPDijkstra.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPDijkstra {
  LLPDijkstra(adj, aw) {
    this.adj = adj;
    this.aw = aw;
    this.n = this.adj.length;
    this.G = new Array(this.n).fill(Infinity);
    this.fixed = new Array(this.n).fill(false);
    this.G[0] = 0;
    // setContainer: indices are offered smallest-key-first, so the
    // predicate below need not establish global minimality itself.
    let _container = [];
    for (let j = 0; j <= ((this.n - 1)); j++) {
        this._heapPush(_container, this.G[j], j);
    }
    while (_container.length > 0) {
        let j = this._heapPop(_container)[1];
        if ((!(((!this.fixed[j]) && (this.G[j] < Infinity))))) {
            continue;
        }
        this.fixed[j] = true;
        for (let t = 0; t <= ((this.adj[j].length - 1)); t++) {
            let k = this.adj[j][t];
            if ((!this.fixed[k])) {
                let cand = (this.G[j] + this.aw[j][t]);
                if ((cand < this.G[k])) {
                    this.G[k] = cand;
                    this._heapPush(_container, this.G[k], k);
                }
            }
        }
    }
    return this.G;
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

if (typeof module !== 'undefined') module.exports = LLPDijkstra;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const aw = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]];
    const prog = new LLPDijkstra();
    const result = prog.LLPDijkstra(adj, aw);
    console.log(JSON.stringify(result));
}
