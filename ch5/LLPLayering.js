/*
 * LLP-Layering: G[j] is the length of the longest path from a source to j.
 * The forbidden predicate is the negation of B_layer restricted to j's
 * incoming edges, so no index is forbidden exactly when B_layer holds.
 * A vertex with no predecessors is never forbidden and keeps its initial
 * label 0.
 *
 * Layering is defined only on a directed ACYCLIC graph -- a cycle has no
 * longest path -- so `setAcyclic(pre)` states that as a condition on valid
 * input.  It is the precondition that makes the schedule available: on an
 * acyclic graph every index can be settled once, in topological order, so
 * that when j is advanced all of its predecessors already hold their final
 * values.  That is the schedule the boolean `fixed` array implements in the
 * hand-written ports, and it is what gives the O(n+m) of Section 5.5.
 * Without the promise the program can only relax to a fixpoint, revisiting
 * a vertex every time a predecessor deepens, which costs O(n*m).
 *
 * Auto-generated from LLPLayering.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPLayering {
  LLPLayering(pre) {
    this.pre = pre;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    // topological order over pre, declared acyclic
    let _indeg = new Array(this.n).fill(0);
    let _succ = Array.from({length: this.n}, () => []);
    for (let _k = 0; _k <= ((this.n - 1)); _k++) {
        for (const _i of this.pre[_k]) {
            if (_i >= 0 && _i < this.n) {
                _indeg[_k] += 1;
                _succ[_i].push(_k);
            }
        }
    }
    let _q = [];
    for (let _k = 0; _k <= ((this.n - 1)); _k++) {
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
        if ((() => { for (const i of this.pre[j]) { if ((this.G[j] <= this.G[i])) return true; } return false; })()) {
            this.G[j] = Math.max(0, (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + 1); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
        }
        for (const _x of _succ[j]) {
            _indeg[_x] -= 1;
            if (_indeg[_x] === 0) {
                _q.push(_x);
            }
        }
    }
    let _total = ((this.n - 1)) - (0) + 1;
    if (_seen !== _total) {
        throw new Error("pre has a cycle; setAcyclic promises it does not");
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPLayering;
