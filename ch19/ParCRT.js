/*
 * Par-CRT: ascending parallel Chinese Remainder Theorem.
 * Forbidden when G[j] < G[i] for some i;
 * advance jumps to next multiple-of-m[j] congruence >= G[i].
 *
 * Auto-generated from ParCRT.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ParCRT {
  ParCRT(m, b) {
    this.m = m;
    this.b = b;
    this.n = this.m.length;
    this.G = this.b.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
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

if (typeof module !== 'undefined') module.exports = ParCRT;

if (typeof require !== 'undefined' && require.main === module) {
    const m = [3, 5, 7, 11];
    const b = [0, 0, 1, 2];
    const prog = new ParCRT();
    const result = prog.ParCRT(m, b);
    console.log(JSON.stringify(result));
}
