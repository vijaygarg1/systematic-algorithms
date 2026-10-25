/*
 * LLP form of Johnson reweighting: raise prices G[j] until every reduced edge weight is non-negative.
 *
 * The graph is a weighted adjacency list of PREDECESSORS: pre[j] holds the
 * tails of the edges into j and the parallel pw[j] their weights, so the
 * weight of (pre[j][t], j) is pw[j][t]. No n x n matrix is involved, so the
 * whole instance is O(n + m). The matrix form cost Theta(n^2) to hold and
 * read, which put a floor under any bound expressed in m.
 *
 * The book states O(mn) for this program (bxx-sp-llp-johnson.tex:38: "Each
 * LLP iteration can be computed in O(m) time, so the graph is reweighted in
 * O(mn) time"). The O(nm log n) of the chapter summary table is for the
 * COMBINED all-pairs method -- this reweighting followed by Dijkstra from
 * every source -- which this program does not do; its output is the price
 * vector, as the algorithm box's KwOut says.
 *
 * Written inline, as Algorithm LLP-Johnson states it:
 * Forbidden (j in [0..n-1]): exists i in pre(j): G[j] < G[i] - w(i,j)
 * Advance : forall k: G[k] := max(0, max { G[i] - w(i,k) | i in pre(k) })
 *
 * The book's "exists i in pre(j) ... w(i,j)" is spelled over POSITIONS
 * here: pre[j][t] is the t-th predecessor of j and pw[j][t] the weight of
 * that edge. Both are O(m); an n x n weight matrix would cost Theta(n^2)
 * before the algorithm started (see the note in the chapter-7 Dijkstra
 * section on why the book writes w(i,j) and not w[i,j]).
 *
 * max over an empty range is the identity -INF, so a vertex with no
 * predecessors takes max(0, -INF) = 0 -- which is the book's max(0, ...)
 * with no special case.
 *
 * Auto-generated from LLPJohnson.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPJohnson {
  LLPJohnson(pre, pw) {
    this.pre = pre;
    this.pw = pw;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((() => { for (let t = 0; t <= ((this.pre[j].length - 1)); t++) { if ((this.G[j] < (this.G[this.pre[j][t]] - this.pw[j][t]))) return true; } return false; })()) {
                for (let k = 0; k <= ((this.n - 1)); k++) {
                    this.G[k] = Math.max(0, (() => { let _a = -2147483647, _s = false; for (let t = 0; t <= ((this.pre[k].length - 1)); t++) { const _v = (this.G[this.pre[k][t]] - this.pw[k][t]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPJohnson;

if (typeof require !== 'undefined' && require.main === module) {
    const pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]];
    const pw = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPJohnson();
    const result = prog.LLPJohnson(pre, pw);
    console.log(JSON.stringify(result));
}
