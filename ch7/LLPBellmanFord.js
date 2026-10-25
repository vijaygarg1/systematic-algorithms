/*
 * LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
 * The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
 * recomputes every other G[k] from its predecessors, as in the book's box.
 *
 * The book writes the weight as w(i,j), with parentheses, to say that it
 * comes from the edge and not from an n x n array -- an array would cost
 * Theta(n^2) to build before Bellman-Ford's own O(nm) could start. So the
 * edges arrive as two parallel ragged arrays: pre[j] lists j's predecessors
 * and pw[j][t] is the weight of the edge pre[j][t] -> j. Both are O(m).
 * LLPJohnson.llp uses the same pair.
 *
 * G is double, so an unreached predecessor contributes infinity + w =
 * infinity under IEEE arithmetic and never wins the min. (The int version
 * of this pattern overflows instead -- see LLPBFS.llp.)
 *
 * Auto-generated from LLPBellmanFord.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPBellmanFord {
  LLPBellmanFord(pre, pw) {
    this.pre = pre;
    this.pw = pw;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(Infinity);
    this.G[0] = 0;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((() => { for (let t = 0; t <= ((this.pre[j].length - 1)); t++) { if ((this.G[j] > (this.G[this.pre[j][t]] + this.pw[j][t]))) return true; } return false; })()) {
                for (let k = 1; k <= ((this.n - 1)); k++) {
                    this.G[k] = (() => { let _a = Infinity, _s = false; for (let t = 0; t <= ((this.pre[k].length - 1)); t++) { const _v = (this.G[this.pre[k][t]] + this.pw[k][t]); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })();
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPBellmanFord;

if (typeof require !== 'undefined' && require.main === module) {
    const pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]];
    const pw = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPBellmanFord();
    const result = prog.LLPBellmanFord(pre, pw);
    console.log(JSON.stringify(result));
}
