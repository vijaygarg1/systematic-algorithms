/*
 * Classical FPTAS for 0/1 Knapsack: scale the profits down by
 * mu = eps*L/nf, then solve the scaled problem exactly by a profit-indexed
 * DP. Rounding loses at most eps*OPT, so the whole thing is polynomial in
 * nf and 1/eps.
 *
 * Auto-generated from FPTASKnapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FPTASKnapsack {
  FPTASKnapsack(w, v, W, epsNum, epsDen) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.epsNum = epsNum;
    this.epsDen = epsDen;
    this.n = this.w.length;
    this.S = new Array(this.w.length).fill(false);
    this.orig = new Array(this.w.length).fill(0);
    this.nf = 0;
    for (let t = 0; t <= ((this.w.length - 1)); t++) {
        if ((this.w[t] <= this.W)) {
            this.orig[this.nf] = t;
            this.nf = (this.nf + 1);
        }
    }
    if ((this.nf === 0)) {
        return this.S;
    }
    let fw = new Array(this.nf).fill(0);
    let fv = new Array(this.nf).fill(0);
    let k = 0;
    while ((k < this.nf)) {
        fw[k] = this.w[this.orig[k]];
        fv[k] = this.v[this.orig[k]];
        k = (k + 1);
    }
    let M = (() => { let _a = -2147483647, _s = false; for (let j = 0; j <= ((this.nf - 1)); j++) { const _v = fv[j]; if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
    let used = new Array(this.nf).fill(false);
    let room = this.W;
    let gval = 0;
    let picks = 0;
    while ((picks < this.nf)) {
        let cand = (0 - 1);
        let t = 0;
        while ((t < this.nf)) {
            if (((!used[t]) && (fw[t] <= room))) {
                if ((cand < 0)) {
                    cand = t;
                } else {
                    if (((fv[t] * fw[cand]) > (fv[cand] * fw[t]))) {
                        cand = t;
                    }
                }
            }
            t = (t + 1);
        }
        if ((cand < 0)) {
            picks = this.nf;
        } else {
            used[cand] = true;
            gval = (gval + fv[cand]);
            room = (room - fw[cand]);
            picks = (picks + 1);
        }
    }
    let L = gval;
    if ((M > L)) {
        L = M;
    }
    let vPrime = new Array(this.nf).fill(0);
    let total = 0;
    let INF = 1;
    k = 0;
    while ((k < this.nf)) {
        vPrime[k] = Math.trunc(((fv[k] * this.nf) * this.epsDen) / (this.epsNum * L));
        total = (total + vPrime[k]);
        INF = (INF + fw[k]);
        k = (k + 1);
    }
    let Vp = (Math.trunc(((2 * this.nf) * this.epsDen) / this.epsNum) + 1);
    if ((total < Vp)) {
        Vp = total;
    }
    let D = Array.from({length: (this.nf + 1)}, () => new Array((Vp + 1)).fill(0));
    for (let a = 0; a <= (this.nf); a++) {
        for (let b = 1; b <= (Vp); b++) {
            D[a][b] = INF;
        }
    }
    for (let i = 1; i <= (this.nf); i++) {
        for (let p = 1; p <= (Vp); p++) {
            D[i][p] = Math.min(D[(i - 1)][p], (fw[(i - 1)] + D[(i - 1)][Math.max(0, (p - vPrime[(i - 1)]))]));
        }
    }
    let bp = 0;
    for (let q = 0; q <= (Vp); q++) {
        if ((D[this.nf][q] <= this.W)) {
            bp = q;
        }
    }
    let i = this.nf;
    while ((i > 0)) {
        if ((D[i][bp] < D[(i - 1)][bp])) {
            this.S[this.orig[(i - 1)]] = true;
            bp = Math.max(0, (bp - vPrime[(i - 1)]));
        }
        i = (i - 1);
    }
    return this.S;
  }
}

if (typeof module !== 'undefined') module.exports = FPTASKnapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const w = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const W = 10;
    const epsNum = 1;
    const epsDen = 2;
    const prog = new FPTASKnapsack();
    const result = prog.FPTASKnapsack(w, v, W, epsNum, epsDen);
    console.log(JSON.stringify(result));
}
