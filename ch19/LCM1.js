/*
 * LCM1: ascending LLP to find least common multiple.
 * Forbidden when G[j] < G[i]; advance jumps to next multiple of A[j] >= G[i].
 *
 * Auto-generated from LCM1.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LCM1 {
  LCM1(A) {
    this.A = A;
    this.n = this.A.length;
    this.G = this.A.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if (this._pick_i(j)) {
                this.G[j] = (this.G[j] + (Math.trunc((((this.G[this.i] - this.G[j]) + this.A[j]) - 1) / this.A[j]) * this.A[j]));
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

if (typeof module !== 'undefined') module.exports = LCM1;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LCM1();
    const result = prog.LCM1(A);
    console.log(JSON.stringify(result));
}
