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
            if (this.reducedNegative(j)) {
                for (let k = 0; k <= ((this.n - 1)); k++) {
                    this.G[k] = this.raised(k);
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }

  reducedNegative(j) {
    let t = 0;
    while ((t < this.pre[j].length)) {
        if ((this.G[j] < (this.G[this.pre[j][t]] - this.pw[j][t]))) {
            return true;
        }
        t = (t + 1);
    }
    return false;
  }

  raised(k) {
    let best = this.G[k];
    let t = 0;
    while ((t < this.pre[k].length)) {
        let cand = (this.G[this.pre[k][t]] - this.pw[k][t]);
        if ((cand > best)) {
            best = cand;
        }
        t = (t + 1);
    }
    return best;
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
