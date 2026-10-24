/*
 * LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
 * time G[j] to the prefix sum of earlier processing times.
 *
 * The targets are the prefix sums S[j] = t[0] + ... + t[j-1], and they are
 * built once in a left-to-right scan rather than written inline as
 * `sum i in [0..j-1] : t[i]`.  Written inline, that reduction is an O(j)
 * loop the guard re-runs for every j -- and the advance runs it a second
 * time -- so the program costs O(n^2) even though every target is already
 * known from the one before it.  Computing them in a single scan is what
 * makes the stated O(n) bound (Section 6.3, "a left-to-right scan") true
 * of this program and not just of the algorithm it encodes.
 *
 * The deadlines d are the sort order of the input, not something the
 * predicate reads: j's target depends only on the processing times ahead
 * of it in that order.
 *
 * Auto-generated from LLPMinMaxLateness.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPMinMaxLateness {
  LLPMinMaxLateness(t, d) {
    this.t = t;
    this.d = d;
    this.n = this.t.length;
    this.S = new Array(this.n).fill(0);
    this.G = new Array(this.n).fill(0);
    for (let j = 1; j <= ((this.n - 1)); j++) {
        this.S[j] = (this.S[(j - 1)] + this.t[(j - 1)]);
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((!(this.G[j] >= this.S[j]))) {
                this.G[j] = this.S[j];
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPMinMaxLateness;

if (typeof require !== 'undefined' && require.main === module) {
    const t = [0, 0, 1, 2];
    const d = [0, 0, 1, 1];
    const prog = new LLPMinMaxLateness();
    const result = prog.LLPMinMaxLateness(t, d);
    console.log(JSON.stringify(result));
}
