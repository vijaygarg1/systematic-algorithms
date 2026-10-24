/*
 * LLP job scheduling: forbidden when G[j] < max_{i in pre[j]} G[i] + t[j].
 *
 * Auto-generated from LLPJobSchedulingForbidden.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPJobSchedulingForbidden {
  LLPJobSchedulingForbidden(t, pre) {
    this.t = t;
    this.pre = pre;
    this.n = this.t.length;
    this.G = this.t.slice();
    // topological order over pre, declared acyclic
    let _indeg = new Array(this.n).fill(0);
    let _succ = Array.from({length: this.n}, () => []);
    for (let _k = 0; _k <= (this.n - 1); _k++) {
        for (const _i of this.pre[_k]) {
            if (_i >= 0 && _i < this.n) {
                _indeg[_k] += 1;
                _succ[_i].push(_k);
            }
        }
    }
    let _q = [];
    for (let _k = 0; _k <= (this.n - 1); _k++) {
        if (_indeg[_k] === 0) {
            _q.push(_k);
        }
    }
    let _seen = 0;
    let _h = 0;
    let j = 0;
    while (_h < _q.length) {
        j = _q[_h];
        _h += 1;
        _seen += 1;
        if ((this.G[j] < (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + this.t[j]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })())) {
            this.G[j] = (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + this.t[j]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
        }
        for (const _x of _succ[j]) {
            _indeg[_x] -= 1;
            if (_indeg[_x] === 0) {
                _q.push(_x);
            }
        }
    }
    let _total = (this.n - 1) - (0) + 1;
    if (_seen !== _total) {
        throw new Error("pre has a cycle; setAcyclic promises it does not");
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPJobSchedulingForbidden;
