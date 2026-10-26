/*
 * Ord: multiplicative order of a modulo n via descending LLP on divisor lattice.
 * G[i] = exponent of p[i] in the current candidate k = prod p[i]^G[i].
 *
 * Auto-generated from Ord.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Ord {
  Ord(a, n, p, e) {
    this.a = a;
    this.n = n;
    this.p = p;
    this.e = e;
    this.s = this.p.length;
    this.G = this.e.slice();
    let k = 1;
    let i = 0;
    while ((i < this.s)) {
        let j = 0;
        while ((j < this.e[i])) {
            k = (k * this.p[i]);
            j = (j + 1);
        }
        i = (i + 1);
    }
    let changed = true;
    while (changed) {
        changed = false;
        i = 0;
        while ((i < this.s)) {
            if (((this.G[i] > 0) && (this.modpow(this.a, Math.trunc(k / this.p[i]), this.n) === 1))) {
                this.G[i] = (this.G[i] - 1);
                k = Math.trunc(k / this.p[i]);
                changed = true;
            }
            i = (i + 1);
        }
    }
    return k;
  }

  modpow(base, exp, mod) {
    let result = 1;
    let b = (base % mod);
    let e = exp;
    while ((e > 0)) {
        if (((e % 2) === 1)) {
            result = ((result * b) % mod);
        }
        e = Math.trunc(e / 2);
        b = ((b * b) % mod);
    }
    return result;
  }
}

if (typeof module !== 'undefined') module.exports = Ord;

if (typeof require !== 'undefined' && require.main === module) {
    const a = 0;
    const p = [2, 3, 5, 7];
    const e = [0, 0, 1, 2];
    const prog = new Ord();
    const result = prog.Ord(a, p.length, p, e);
    console.log(JSON.stringify(result));
}
