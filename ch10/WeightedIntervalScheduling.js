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
    let n = this.s.length;
    let p = new Array(n).fill(0);
    let G = new Array(n).fill(0);
    let sel = new Array(n).fill(0);
    for (let j = 1; j <= ((n - 1)); j++) {
        p[j] = this.lastCompatible(j, this.s, this.f);
    }
    G[0] = 0;
    for (let cur = 1; cur <= ((n - 1)); cur++) {
        G[cur] = Math.max((this.w[cur] + G[p[cur]]), G[(cur - 1)]);
    }
    let cur = (n - 1);
    while ((cur > 0)) {
        if (((this.w[cur] + G[p[cur]]) >= G[(cur - 1)])) {
            sel[cur] = 1;
            cur = p[cur];
        } else {
            cur = (cur - 1);
        }
    }
    return sel;
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
