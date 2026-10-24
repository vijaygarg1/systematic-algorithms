/*
 * LLP-GCD: greatest common divisor of two numbers via Euclidean
 * reduction, matching bxx-numberTheory.tex Algorithm LLP-GCD. G is the
 * two-element vector (a, b); forbidden picks any entry strictly larger
 * than another, and advance replaces it by its remainder mod the smaller
 * (or by the smaller itself when it divides exactly, so entries stay at
 * least 1). At termination G[0] = G[1] = gcd(a, b).
 *
 * Each advance replaces G[j] by G[j] mod G[i] < G[i] <= G[j], which at
 * least halves it, so the algorithm performs O(log(min(a,b))) advances --
 * the bound the chapter states. `n` has to be set explicitly, as in
 * LLPExtGCD: it is normally inferred from an array *parameter*'s length,
 * but G is locally allocated rather than passed in.
 *
 * Auto-generated from LLPGCD.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPGCD {
  LLPGCD(a, b) {
    this.a = a;
    this.b = b;
    this.G = new Array(2).fill(0);
    this.n = 2;
    this.G[0] = this.a;
    this.G[1] = this.b;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
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
    return this.G[0];
  }

  _pick_i(j) {
    for (let i = 0; i <= (1); i++) {
      if ((this.G[j] > this.G[i])) { this.i = i; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPGCD;

if (typeof require !== 'undefined' && require.main === module) {
    const a = 0;
    const b = 0;
    const prog = new LLPGCD();
    const result = prog.LLPGCD(a, b);
    console.log(JSON.stringify(result));
}
