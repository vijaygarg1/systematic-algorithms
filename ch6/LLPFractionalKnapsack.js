/*
 * LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each
 * fraction G[j] in [0,1] to its target G*[j], read in O(1) from one
 * prefix scan of the weights.
 *
 * S is the EXCLUSIVE prefix sum, S[k] = w[0] + ... + w[k-1], with
 * S[0] = 0 -- one slot longer than w. That is what removes target's
 * j > 0 special case: the weight before item j is just S[j], and the
 * weight through item j is S[j+1], so both ends of the breakpoint test
 * are plain reads. The scan stays a sequential while loop because
 * S[i+1] depends on S[i]; a forall would not be a prefix sum.
 *
 * Auto-generated from LLPFractionalKnapsack.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPFractionalKnapsack {
  LLPFractionalKnapsack(v, w, W) {
    this.v = v;
    this.w = w;
    this.W = W;
    this.n = this.v.length;
    this.G = new Array(this.n).fill(0.0);
    this.S = new Array((this.n + 1)).fill(0.0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.S[(i + 1)] = (this.S[i] + this.w[i]);
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((this.G[j] < this.target(j))) {
                this.G[j] = this.target(j);
                _changed = true;
            }
        }
    }
    return this.G;
  }

  target(j) {
    if ((this.S[(j + 1)] <= this.W)) {
        return 1.0;
    }
    if ((this.S[j] >= this.W)) {
        return 0.0;
    }
    return ((this.W - this.S[j]) / this.w[j]);
  }
}

if (typeof module !== 'undefined') module.exports = LLPFractionalKnapsack;

if (typeof require !== 'undefined' && require.main === module) {
    const v = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const W = 10.0;
    const prog = new LLPFractionalKnapsack();
    const result = prog.LLPFractionalKnapsack(v, w, W);
    console.log(JSON.stringify(result));
}
