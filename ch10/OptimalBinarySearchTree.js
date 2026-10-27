/*
 * Optimal BST: O(n³) DP over interval [i, j] picking the root r.
 *
 * Auto-generated from OptimalBinarySearchTree.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class OptimalBinarySearchTree {
  solve(prob) {
    this.prob = prob;
    this.n = this.prob.length;
    this.G = Array.from({length: this.n}, () => new Array(this.n).fill(0.0));
    this.s = Array.from({length: this.n}, () => new Array(this.n).fill(0.0));
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i][i] = this.prob[i];
        this.s[i][i] = this.prob[i];
    }
    for (let len_ = 1; len_ <= ((this.n - 1)); len_++) {
        for (let lo = 0; lo <= (((this.n - 1) - len_)); lo++) {
            let hi = (lo + len_);
            this.s[lo][hi] = (this.s[lo][(hi - 1)] + this.prob[hi]);
            this.G[lo][hi] = Infinity;
            for (let r = lo; r <= (hi); r++) {
                let left = 0.0;
                let right = 0.0;
                if ((r > lo)) {
                    left = this.G[lo][(r - 1)];
                }
                if ((r < hi)) {
                    right = this.G[(r + 1)][hi];
                }
                let cost = ((this.s[lo][hi] + left) + right);
                this.G[lo][hi] = Math.min(this.G[lo][hi], cost);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = OptimalBinarySearchTree;

if (typeof require !== 'undefined' && require.main === module) {
    const prob = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const prog = new OptimalBinarySearchTree();
    const result = prog.solve(prob);
    console.log(JSON.stringify(result));
}
