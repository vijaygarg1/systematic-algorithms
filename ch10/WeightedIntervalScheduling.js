/*
 * Classical sequential weighted-interval-scheduling DP. Intervals are
 * presorted once by finish time, with index 0 a 0-weight sentinel, so
 * p[j] -- the last interval that finishes no later than j starts -- is
 * derived here rather than supplied: it is the largest such index, or the
 * sentinel 0 when no earlier interval fits.
 *
 * Auto-generated from WeightedIntervalScheduling.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class WeightedIntervalScheduling {
  schedule(s, f, w) {
    this.s = s;
    this.f = f;
    this.w = w;
    this.n = this.s.length;
    this.p = new Array(this.n).fill(0);
    this.opt = new Array(this.n).fill(0);
    this.G = new Array(this.n).fill(0);
    for (let j = 1; j <= ((this.n - 1)); j++) {
        this.p[j] = this.lastCompatible(j, this.s, this.f);
    }
    this.opt[0] = 0;
    for (let cur = 1; cur <= ((this.n - 1)); cur++) {
        this.opt[cur] = Math.max((this.w[cur] + this.opt[this.p[cur]]), this.opt[(cur - 1)]);
    }
    let cur = (this.n - 1);
    while ((cur > 0)) {
        if (((this.w[cur] + this.opt[this.p[cur]]) >= this.opt[(cur - 1)])) {
            this.G[cur] = 1;
            cur = this.p[cur];
        } else {
            cur = (cur - 1);
        }
    }
    return this.G;
  }

  lastCompatible(j, s, f) {
    let lo = 1;
    let hi = (j - 1);
    let best = 0;
    while ((lo <= hi)) {
        let mid = Math.trunc((lo + hi) / 2);
        if ((f[mid] <= s[j])) {
            best = mid;
            lo = (mid + 1);
        } else {
            hi = (mid - 1);
        }
    }
    return best;
  }
}

if (typeof module !== 'undefined') module.exports = WeightedIntervalScheduling;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const w = [0, 0, 0, 2];
    const prog = new WeightedIntervalScheduling();
    const result = prog.schedule(s, f, w);
    console.log(JSON.stringify(result));
}
