/*
 * LLP FPTAS for Knapsack, matching bxx-approximate.tex Algorithm
 * LLP-ApproxKnapsack (fig:llp-approxknapsack): discard items that cannot
 * fit, then run a profit-indexed min-weight DP D[i][p] on a lattice of
 * size (nf+1) x (V'+1), V' = O(nf/eps), where nf is the number of
 * feasible items. Fully polynomial.
 *
 * The profits are scaled by mu = eps*L/nf against a 2-approximation lower
 * bound L on OPT (the better of a density-greedy solution and the most
 * profitable single item), not against the maximum profit M. Scaling by L
 * still loses only nf*mu = eps*L <= eps*OPT, and because OPT <= 2L no
 * feasible solution reaches scaled profit beyond 2*nf/eps, so the axis is
 * capped there: O(nf/eps) columns instead of O(nf^2/eps), taking the lattice
 * from O(nf^3/eps) to O(nf^2/eps).
 * D[i][p] = minimum total weight of a subset of the first i feasible
 * items with scaled profit >= p; the answer is max { p : D[nf][p] <= W },
 * profit mu * p.
 *
 * Every value the guard or its advance touches by bare name (D, fw,
 * vPrime, nf, Vp) must be a class field for the auto-generated
 * forbidden/advance helpers to see it, and the compiler grants that only
 * to a *leading, uninterrupted run* of declarations at the top of the
 * entry method. Each preprocessing step is therefore written as a single
 * declaration initialised by a helper call, so the run is never broken;
 * the loops themselves live in those helpers below.
 *
 * The guard's shared bound n is set to max(nf,Vp)+1 (rather than nf+1 or
 * Vp+1 individually, since the compiler infers one bound for every index
 * of a guard) and the predicate explicitly re-restricts i to [1..nf] and
 * p to [1..Vp], so out-of-range (i,p) pairs in the padded square scan are
 * simply never forbidden.
 *
 * The aggregates deliberately avoid i and p as bound variables: a
 * quantifier helper is generated taking the guard's index variables as
 * parameters, so a bound i inside a forbidden (i, p) program collides
 * with its own parameter and will not compile.
 *
 * Auto-generated from LLPApproxKnapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPApproxKnapsack {
  LLPApproxKnapsack(w, v, W, epsNum, epsDen) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.epsNum = epsNum;
    this.epsDen = epsDen;
    this.n = this.w.length;
    this.feas = this.feasible(this.w, this.W);
    this.nf = this.feas.length;
    this.fw = this.gather(this.w, this.feas);
    this.fv = this.gather(this.v, this.feas);
    this.M = (() => { let _a = -2147483647, _s = false; for (let k = 0; k <= ((this.nf - 1)); k++) { const _v = this.fv[k]; if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
    this.L = this.lowerBound(this.fw, this.fv, this.nf, this.W, this.M);
    this.vPrime = this.scaled(this.fv, this.nf, this.epsNum, this.epsDen, this.L);
    this.Vp = this.cappedAxis(this.vPrime, this.nf, this.epsNum, this.epsDen);
    this.INF = (1 + (() => { let _a = 0; for (let k = 0; k <= ((this.nf - 1)); k++) { _a += this.fw[k]; } return _a; })());
    this.D = Array.from({length: (this.nf + 1)}, () => new Array((this.Vp + 1)).fill(0));
    for (let a = 0; a <= (this.nf); a++) {
        for (let b = 1; b <= (this.Vp); b++) {
            this.D[a][b] = this.INF;
        }
    }
    this.n = (Math.max(this.nf, this.Vp) + 1);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let i = 0; i <= ((this.n - 1)); i++) {
            for (let p = 0; p <= ((this.n - 1)); p++) {
                if ((((((i >= 1) && (i <= this.nf)) && (p >= 1)) && (p <= this.Vp)) && (this.D[i][p] > Math.min(this.D[(i - 1)][p], (this.fw[(i - 1)] + this.D[(i - 1)][Math.max(0, (p - this.vPrime[(i - 1)]))]))))) {
                    this.D[i][p] = Math.min(this.D[(i - 1)][p], (this.fw[(i - 1)] + this.D[(i - 1)][Math.max(0, (p - this.vPrime[(i - 1)]))]));
                    _changed = true;
                }
            }
        }
    }
    return this.bestProfit();
  }

  bestProfit() {
    let best = 0;
    for (let q = 0; q <= (this.Vp); q++) {
        if ((this.D[this.nf][q] <= this.W)) {
            best = q;
        }
    }
    return best;
  }

  feasible(w, W) {
    let idx = new Array(w.length).fill(0);
    let c = 0;
    for (let t = 0; t <= ((w.length - 1)); t++) {
        if ((w[t] <= W)) {
            idx[c] = t;
            c = (c + 1);
        }
    }
    return idx.slice(0, ((c - 1)) + 1);
  }

  gather(a, idx) {
    let out = new Array(idx.length).fill(0);
    for (let k = 0; k <= ((idx.length - 1)); k++) {
        out[k] = a[idx[k]];
    }
    return out;
  }

  scaled(fv, nf, epsNum, epsDen, L) {
    let out = new Array(nf).fill(0);
    for (let k = 0; k <= ((nf - 1)); k++) {
        out[k] = Math.trunc(((fv[k] * nf) * epsDen) / (epsNum * L));
    }
    return out;
  }

  lowerBound(fw, fv, nf, W, M) {
    let used = new Array(nf).fill(false);
    let room = W;
    let gval = 0;
    let picks = 0;
    while ((picks < nf)) {
        let cand = (0 - 1);
        let t = 0;
        while ((t < nf)) {
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
            picks = nf;
        } else {
            used[cand] = true;
            gval = (gval + fv[cand]);
            room = (room - fw[cand]);
            picks = (picks + 1);
        }
    }
    if ((M > gval)) {
        return M;
    }
    return gval;
  }

  cappedAxis(vPrime, nf, epsNum, epsDen) {
    let total = (() => { let _a = 0; for (let k = 0; k <= ((nf - 1)); k++) { _a += vPrime[k]; } return _a; })();
    let capv = (Math.trunc(((2 * nf) * epsDen) / epsNum) + 1);
    if ((total < capv)) {
        return total;
    }
    return capv;
  }
}

if (typeof module !== 'undefined') module.exports = LLPApproxKnapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const w = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const W = 10;
    const epsNum = 1;
    const epsDen = 2;
    const prog = new LLPApproxKnapsack();
    const result = prog.LLPApproxKnapsack(w, v, W, epsNum, epsDen);
    console.log(JSON.stringify(result));
}
