/*
 * Precedences: composition program enforcing precedence constraints
 * pre(j) on job starts.  Each job j cannot start until every i in
 * pre(j) has finished (start G[i] plus processing time t[i]).
 * Composed onto LLP-MinMaxLateness via predicate conjunction:
 * [ LLP-MinMaxLateness(t, d, G) && Precedences(pre, t, G) ].
 *
 * Auto-generated from Precedences.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Precedences {
  Precedences(pre, t, G) {
    this.pre = pre;
    this.t = t;
    this.G = G;
    this.n = this.pre.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((!(this.G[j] >= (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + this.t[i]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })()))) {
                this.G[j] = (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + this.t[i]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Precedences;
