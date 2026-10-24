/*
 * Mandatory: composition program forcing the intervals in subset S
 * into the schedule.  Halts with "infeasible" (returns null) when two
 * mandatory intervals overlap.  Composed onto LLP-IntervalScheduling
 * via predicate conjunction:
 * [ LLP-IntervalScheduling(s, f, G) && Mandatory(S, G) ].
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
    this.G = new Array(this.n).fill(false);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((this.S[j] && (!this.G[j]))) {
                if ((() => { for (let k = 0; k <= ((this.n - 1)); k++) { if ((this.G[k] && this.overlap(j, k, this.s, this.f))) return true; } return false; })()) {
                    return null;
                }
                this.G[j] = true;
                _changed = true;
            }
        }
    }
    return this.G;
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
