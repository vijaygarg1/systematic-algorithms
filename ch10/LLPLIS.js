/*
 * LLP-LIS. The book states B as two conjuncts, G[j] >= 1 and
 * G[j] >= max{G[i]+1 | i in pre(j)}, and proves it lattice-linear; one
 * `ensure` over their maximum says exactly that, so the forbidden
 * predicate and the advance are read off B rather than restated.
 * pre(j) = {i < j : A[i] < A[j]}, so priority(j) = j settles each index
 * in one advance -- the schedule the book's `fixed` array records, an
 * efficiency choice rather than part of the predicate.
 *
 * Auto-generated from LLPLIS.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPLIS {
  LLPLIS(A, pre) {
    this.A = A;
    this.pre = pre;
    this.n = this.A.length;
    this.G = new Array(this.n).fill(1);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((!(this.G[j] >= Math.max(1, (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + 1); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })())))) {
                this.G[j] = Math.max(1, (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + 1); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPLIS;
