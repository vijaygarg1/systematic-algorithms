/*
 * Extended Euclidean algorithm, cast as an LLP forbidden/advance program
 * (matching the book's LLP-ExtGCD algorithm box in bxx-numberTheory.tex,
 * \S Extended GCD Algorithm): maintains G alongside Bezout coefficients
 * (X, Y) with the invariant G[k] = X[k]*a + Y[k]*b. Forbidden picks the
 * larger of the two entries; advance reduces it via the same quotient
 * q := floor((G[j]-1)/G[i]) used by LLP-GCD, applying the identical
 * subtraction to the Bezout pair so the invariant is preserved. At
 * termination G[0] = G[1] = gcd(a,b) and (X[0],Y[0]) is a Bezout pair.
 *
 * All three state arrays (G, X, Y) must be declared consecutively, before
 * any assignment statement -- the parser only promotes a *leading,
 * uninterrupted* run of `TYPE NAME = ...;` declarations to instance
 * fields visible from forbidden/advance; an assignment statement between
 * two declarations ends that run early, silently leaving the later ones
 * as method-local variables invisible to advance() (a real llc.py
 * limitation, confirmed and documented rather than worked around in the
 * compiler, since only this one program has needed more than one top-
 * level state array so far). `n` likewise has to be set explicitly here:
 * it is normally inferred from an array *parameter*'s length, but G/X/Y
 * are locally allocated, not parameters, so nothing would ever set it
 * otherwise -- left at Java's default 0, the main loop would silently
 * iterate zero times.
 *
 * Auto-generated from LLPExtGCD.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPExtGCD {
  LLPExtGCD(a, b) {
    this.a = a;
    this.b = b;
    this.G = new Array(2).fill(0);
    this.X = new Array(2).fill(0);
    this.Y = new Array(2).fill(0);
    this.n = 2;
    this.G[0] = this.a;
    this.G[1] = this.b;
    this.X[0] = 1;
    this.Y[0] = 0;
    this.X[1] = 0;
    this.Y[1] = 1;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if (this._pick_i(j)) {
                let q = Math.trunc((this.G[j] - 1) / this.G[this.i]);
                this.G[j] = (this.G[j] - (q * this.G[this.i]));
                this.X[j] = (this.X[j] - (q * this.X[this.i]));
                this.Y[j] = (this.Y[j] - (q * this.Y[this.i]));
                _changed = true;
            }
        }
    }
    let result = new Array(3).fill(0);
    result[0] = this.G[0];
    result[1] = this.X[0];
    result[2] = this.Y[0];
    return result;
  }

  _pick_i(j) {
    for (let i = 0; i <= (1); i++) {
      if ((this.G[j] > this.G[i])) { this.i = i; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPExtGCD;

if (typeof require !== 'undefined' && require.main === module) {
    const a = 0;
    const b = 0;
    const prog = new LLPExtGCD();
    const result = prog.LLPExtGCD(a, b);
    console.log(JSON.stringify(result));
}
