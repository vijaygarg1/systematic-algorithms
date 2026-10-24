/*
 * 0/1 knapsack: O(nW) bottom-up table fill.
 *
 * Auto-generated from Knapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Knapsack {
  solve(w, v, W) {
    this.w = w;
    this.v = v;
    this.W = W;
    this.n = (this.w.length - 1);
    this.G = Array.from({length: (this.n + 1)}, () => new Array((this.W + 1)).fill(0));
    for (let c = 0; c <= (this.W); c++) {
        this.G[0][c] = 0;
    }
    for (let i = 0; i <= (this.n); i++) {
        this.G[i][0] = 0;
    }
    for (let i = 1; i <= (this.n); i++) {
        for (let c = 1; c <= (this.W); c++) {
            if ((this.w[i] > c)) {
                this.G[i][c] = this.G[(i - 1)][c];
            } else {
                this.G[i][c] = Math.max(this.G[(i - 1)][c], (this.G[(i - 1)][(c - this.w[i])] + this.v[i]));
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Knapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const w = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const W = 10;
    const prog = new Knapsack();
    const result = prog.solve(w, v, W);
    console.log(JSON.stringify(result));
}
