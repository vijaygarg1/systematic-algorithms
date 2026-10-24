/*
 * LLP-ArrayGCD: forbidden when some G[i] < G[j]; advance shrinks G[j] via mod.
 *
 * Auto-generated from LLPArrayGCD.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPArrayGCD {
  LLPArrayGCD(A) {
    this.A = A;
    this.n = this.A.length;
    this.G = this.A.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this._pick_i(j)) {
                if (((this.G[j] % this.G[this.i]) === 0)) {
                    this.G[j] = this.G[this.i];
                } else {
                    this.G[j] = (this.G[j] % this.G[this.i]);
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }

  _pick_i(j) {
    for (let i = 0; i <= ((this.n - 1)); i++) {
      if ((this.G[j] > this.G[i])) { this.i = i; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPArrayGCD;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LLPArrayGCD();
    const result = prog.LLPArrayGCD(A);
    console.log(JSON.stringify(result));
}
