/*
 * GCD2: ascending GCD via ceiling-ratio operations.
 * Equivalent forbidden / advance form of the ensure-clause version
 * G[j] >= max_i (A[j] * G[i] + A[i] - 1) / A[i].
 *
 * Forbidden picks any i for which the bound is currently violated;
 * advance lifts G[j] to that single ratio.  Iterating over forbidden i's
 * converges to the maximum because the ensure constraint is monotone.
 *
 * Auto-generated from GCD2.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class GCD2 {
  GCD2(A) {
    this.A = A;
    this.n = this.A.length;
    this.G = new Array(this.n).fill(0);
    for (let k = 0; k <= ((this.n - 1)); k++) {
        this.G[k] = 1;
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this._pick_i(j)) {
                this.G[j] = Math.trunc((((this.A[j] * this.G[this.i]) + this.A[this.i]) - 1) / this.A[this.i]);
                _changed = true;
            }
        }
    }
    return this.G;
  }

  _pick_i(j) {
    for (let i = 0; i <= ((this.n - 1)); i++) {
      if (((this.G[j] * this.A[i]) < (this.A[j] * this.G[i]))) { this.i = i; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = GCD2;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new GCD2();
    const result = prog.GCD2(A);
    console.log(JSON.stringify(result));
}
