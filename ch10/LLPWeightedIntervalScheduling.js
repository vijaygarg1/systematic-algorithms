/*
 * LLP weighted interval scheduling. The book states B as two families of
 * inequalities, G[j] >= G[j-1] and G[j] >= w[j] + G[p[j]], which is
 * exactly what one `ensure` over their maximum says; the forbidden
 * predicate and advance are then read off B rather than restated.
 * Index 0 is pinned at 0, so the guard ranges over 1..n.
 * Since p[j] < j and j-1 < j, priority(j) = j settles each index in one
 * advance -- that schedule is what the boolean `fixed` array records in
 * the book's earlier presentation, and it is an efficiency choice, not
 * part of the predicate.
 *
 * Auto-generated from LLPWeightedIntervalScheduling.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPWeightedIntervalScheduling {
  LLPWeightedIntervalScheduling(w, p) {
    this.w = w;
    this.p = p;
    this.n = this.w.length;
    this.G = new Array(this.n).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 1; j <= ((this.n - 1)); j++) {
            if ((!(this.G[j] >= Math.max(this.G[(j - 1)], (this.w[j] + this.G[this.p[j]]))))) {
                this.G[j] = Math.max(this.G[(j - 1)], (this.w[j] + this.G[this.p[j]]));
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPWeightedIntervalScheduling;

if (typeof require !== 'undefined' && require.main === module) {
    const w = [0, 0, 1, 2];
    const p = [0, 0, 1, 1];
    const prog = new LLPWeightedIntervalScheduling();
    const result = prog.LLPWeightedIntervalScheduling(w, p);
    console.log(JSON.stringify(result));
}
