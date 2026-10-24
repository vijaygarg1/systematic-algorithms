/*
 * LLP-Knapsack: the book's two rules over the (i, c) lattice, joined by
 * `&&` so they share one fixpoint, with each index carrying its own
 * bound -- i over items, c over capacities.
 *
 * Auto-generated from LLPKnapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPKnapsack {
  LLPKnapsack(w, v, W) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.n = (this.w.length - 1);
    this.G = Array.from({length: (this.n + 1)}, () => new Array((this.W + 1)).fill(0));
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let i = 1; i <= (this.n); i++) {
            for (let c = 1; c <= (this.W); c++) {
                if ((this.G[i][c] < this.G[(i - 1)][c])) {
                    this.G[i][c] = this.G[(i - 1)][c];
                    _changed = true;
                }
                if (((this.w[i] <= c) && (this.G[i][c] < (this.G[(i - 1)][(c - this.w[i])] + this.v[i])))) {
                    this.G[i][c] = (this.G[(i - 1)][(c - this.w[i])] + this.v[i]);
                    _changed = true;
                }
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPKnapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const w = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const W = 10;
    const prog = new LLPKnapsack();
    const result = prog.LLPKnapsack(w, v, W);
    console.log(JSON.stringify(result));
}
