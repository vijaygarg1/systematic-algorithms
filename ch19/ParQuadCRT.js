/*
 * Par-QuadCRT: simultaneous quadratic congruences x^2 ≡ a[j] (mod m[j]).
 * Generalises Par-CRT to a non-linear local predicate.  G[j] is initialised
 * to the smallest non-negative root of x^2 ≡ a[j] (mod m[j]); the
 * forbidden / advance pair drives every G[j] up to a common value that
 * satisfies all r congruences simultaneously.
 *
 * The advance jumps G[j] forward by multiples of m[j] until x^2 ≡ a[j].
 * In the worst case one walks the period of m[j] (cost O(m[j]) per
 * advance); for prime m[j] the two square roots can be precomputed.
 *
 * Auto-generated from ParQuadCRT.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ParQuadCRT {
  ParQuadCRT(m, a, roots) {
    this.m = m;
    this.a = a;
    this.roots = roots;
    this.n = this.m.length;
    this.G = this.roots.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this._pick_i(j)) {
                this.G[j] = (this.G[j] + (Math.trunc((((this.G[this.i] - this.G[j]) + this.m[j]) - 1) / this.m[j]) * this.m[j]));
                _changed = true;
            }
        }
    }
    return this.G;
  }

  _pick_i(j) {
    for (let i = 0; i <= ((this.n - 1)); i++) {
      if ((this.G[j] < this.G[i])) { this.i = i; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = ParQuadCRT;

if (typeof require !== 'undefined' && require.main === module) {
    const m = [3, 5, 7, 11];
    const a = [0, 0, 1, 2];
    const roots = [0, 0, 1, 1];
    const prog = new ParQuadCRT();
    const result = prog.ParQuadCRT(m, a, roots);
    console.log(JSON.stringify(result));
}
