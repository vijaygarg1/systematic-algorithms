/*
 * Fractional knapsack: greedy by value-density v/w (last item may be split).
 *
 * Auto-generated from FractionalKnapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FractionalKnapsack {
  solve(v, w, W) {
    this.v = v;
    this.w = w;
    this.W = W;
    this.n = this.v.length;
    this.x = new Array(this.n).fill(0.0);
    this.rem = this.W;
    this.done = false;
    this.i = 0;
    while (((this.i < this.n) && (!this.done))) {
        if ((this.w[this.i] <= this.rem)) {
            this.x[this.i] = 1.0;
            this.rem = (this.rem - this.w[this.i]);
        } else {
            this.x[this.i] = (this.rem / this.w[this.i]);
            this.done = true;
        }
        this.i = (this.i + 1);
    }
    return this.x;
  }
}

if (typeof module !== 'undefined') module.exports = FractionalKnapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const v = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const W = 10.0;
    const prog = new FractionalKnapsack();
    const result = prog.solve(v, w, W);
    console.log(JSON.stringify(result));
}
