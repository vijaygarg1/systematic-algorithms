/*
 * Interval scheduling, as bxx-greedy.tex's Algorithm Interval states it:
 *
 * G[1] := 1                       // first interval always selected
 * last := 1                       // index of last selected interval
 * for i := 2 to n:
 * if s[i] >= f[last]:
 * G[i] := 1                   // interval i is compatible
 * last := i
 *
 * Intervals arrive sorted by finish time, so the earliest-finishing
 * compatible interval is always the right greedy choice.
 *
 * The loop is `for`, not `forall`: last carries from one iteration to the
 * next, so the iterations are not independent.
 *
 * The book indexes from 1 and selects G[1] unconditionally, so it assumes
 * n >= 1; this translation keeps that precondition.
 *
 * Auto-generated from Interval.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Interval {
  Interval(s, f) {
    this.s = s;
    this.f = f;
    this.n = this.s.length;
    this.G = new Array(this.n).fill(0);
    this.G[0] = 1;
    let last = 0;
    for (let i = 1; i <= ((this.n - 1)); i++) {
        if ((this.s[i] >= this.f[last])) {
            this.G[i] = 1;
            last = i;
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Interval;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const prog = new Interval();
    const result = prog.Interval(s, f);
    console.log(JSON.stringify(result));
}
