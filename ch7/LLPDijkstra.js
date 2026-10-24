/*
 * LLP-Dijkstra, matching bxx-sp-llp-dijkstra.tex Algorithm LLP-Dijkstra
 * (fig:alg-llp-dijk). The lattice points are PAIRS (fixed, G): a vertex is
 * settled by flipping fixed[j], and distances only ever decrease. The
 * predicate has two clauses -- every discovered vertex is settled, and the
 * shortest-path recurrence holds.
 *
 * The forbidden index is the cheapest unsettled discovered vertex. The
 * minimality is not tested here: setContainer(PriorityQueue, G[j], min)
 * holds the
 * candidate indices in a min-heap keyed by G, so the index offered to the
 * guard is already the smallest one, and all that remains to check is that
 * it is unsettled and discovered. That is the same division of labour as
 * the classical Dijkstra of Algorithm Dijkstra, whose heap of (j, c) pairs
 * supplies the vertex and whose `if not fixed[j]` supplies the rest.
 *
 * Lowering G[k] in the advance pushes k back onto the heap with its new
 * key, exactly as the classical algorithm does H.add(k, d[k]); the stale
 * entry is discarded when it surfaces and the guard rejects it. Each edge
 * contributes at most one push, so the whole loop runs in O(m log n).
 *
 * Completeness of this cheap guard needs non-negative weights: it never
 * looks at settled vertices, so it is a valid detector of a violated
 * recurrence only while every settled vertex has had its out-edges relaxed
 * and carries a key no larger than any unsettled one (Lemma lem:dijk).
 *
 * Auto-generated from LLPDijkstra.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPDijkstra {
  LLPDijkstra(adj, w) {
    this.adj = adj;
    this.w = w;
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
        for (const k of this.adj[j]) {
            if ((!this.fixed[k])) {
                let cand = (this.G[j] + this.w[j][k]);
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
