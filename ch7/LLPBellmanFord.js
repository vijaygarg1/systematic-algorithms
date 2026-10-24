/*
 * LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
 * The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
 * recomputes every other G[k] from its predecessors, as in the book's box.
 *
 * Auto-generated from LLPBellmanFord.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPBellmanFord {
  LLPBellmanFord(pre, w) {
    this.pre = pre;
    this.w = w;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(Infinity);
    this.G[0] = 0;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((() => { for (const i of this.pre[j]) { if ((this.G[j] > (this.G[i] + this.w[i][j]))) return true; } return false; })()) {
                for (let k = 1; k <= ((this.n - 1)); k++) {
                    this.G[k] = (() => { let _a = Infinity, _s = false; for (const i of this.pre[k]) { const _v = (this.G[i] + this.w[i][k]); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })();
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPBellmanFord;
