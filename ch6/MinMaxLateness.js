/*
 * Minimizing maximum lateness, as bxx-greedy.tex's Algorithm
 * MinMaxLateness states it:
 *
 * last := 0; lateness := 0
 * for i := 1 to n:
 * finish := last + t[i]
 * G[i] := last
 * last := finish
 * if finish > d[i]: lateness := max(lateness, finish - d[i])
 * return G, lateness
 *
 * Jobs arrive sorted by deadline, which is the greedy choice that
 * minimizes the maximum lateness.
 *
 * The loop is `for`, not `forall`: last and lateness both carry from one
 * iteration to the next.
 *
 * The book returns the pair (G, lateness). LL returns one value, so G is
 * returned and lateness is computed alongside it, exactly as the book
 * computes it.
 *
 * Auto-generated from MinMaxLateness.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MinMaxLateness {
  MinMaxLateness(t, d) {
    this.t = t;
    this.d = d;
    this.n = this.t.length;
    this.G = new Array(this.n).fill(0);
    this.last = 0;
    this.lateness = 0;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        let finish = (this.last + this.t[i]);
        this.G[i] = this.last;
        this.last = finish;
        if ((finish > this.d[i])) {
            this.lateness = Math.max(this.lateness, (finish - this.d[i]));
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = MinMaxLateness;

if (typeof require !== 'undefined' && require.main === module) {
    const t = [0, 0, 1, 2];
    const d = [0, 0, 1, 1];
    const prog = new MinMaxLateness();
    const result = prog.MinMaxLateness(t, d);
    console.log(JSON.stringify(result));
}
