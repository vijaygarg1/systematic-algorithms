/*
 * Mandatory-Intervals: interval scheduling when subset S must appear in the answer.
 * Returns null ("infeasible") exactly when two mandatory intervals overlap.
 * Intervals arrive indexed in finish-time order.
 * NOT a conjunct on LLP-IntervalScheduling: that composition rejects feasible
 * instances (interval 0=[0,2) optional, 1=[1,3) mandatory). Three phases instead.
 *
 * Auto-generated from MandatoryIntervals.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MandatoryIntervals {
  MandatoryIntervals(s, f, S) {
    this.s = s;
    this.f = f;
    this.S = S;
    this.n = this.s.length;
    this.sel = new Array(this.n).fill(false);
    for (let j = 0; j <= ((this.n - 1)); j++) {
        if (this.S[j]) {
            for (let k = 0; k <= ((this.n - 1)); k++) {
                if (((this.S[k] && (k !== j)) && this.overlap(j, k, this.s, this.f))) {
                    return null;
                }
            }
            this.sel[j] = true;
        }
    }
    let cand = new Array(this.n).fill(false);
    for (let j = 0; j <= ((this.n - 1)); j++) {
        if ((!this.S[j])) {
            let ok = true;
            for (let k = 0; k <= ((this.n - 1)); k++) {
                if ((this.S[k] && this.overlap(j, k, this.s, this.f))) {
                    ok = false;
                }
            }
            cand[j] = ok;
        }
    }
    let chosen = false;
    let lastFinish = 0;
    for (let j = 0; j <= ((this.n - 1)); j++) {
        if (cand[j]) {
            if (((!chosen) || (this.s[j] >= lastFinish))) {
                this.sel[j] = true;
                lastFinish = this.f[j];
                chosen = true;
            }
        }
    }
    return this.sel;
  }

  overlap(j, k, s, f) {
    return ((s[j] < f[k]) && (s[k] < f[j]));
  }
}

if (typeof module !== 'undefined') module.exports = MandatoryIntervals;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const S = [false, false, false, false];
    const prog = new MandatoryIntervals();
    const result = prog.MandatoryIntervals(s, f, S);
    console.log(JSON.stringify(result));
}
