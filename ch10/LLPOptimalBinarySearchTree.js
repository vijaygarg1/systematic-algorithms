/*
 * LLP-OptimalBinarySearchTree: ensure G[i][j] >= optimal cost over interval [i..j].
 *
 * Auto-generated from LLPOptimalBinarySearchTree.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPOptimalBinarySearchTree {
  LLPOptimalBinarySearchTree(p, G) {
    this.p = p;
    this.G = G;
    this.n = this.p.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let i = 0; i <= ((this.n - 1)); i++) {
            for (let j = 0; j <= ((this.n - 1)); j++) {
                if (((i <= j) && (this.G[i][j] < this.optCost(this.p, this.G, i, j)))) {
                    this.G[i][j] = this.optCost(this.p, this.G, i, j);
                    _changed = true;
                }
            }
        }
    }
  }

  optCost(p, G, i, j) {
    let best = Infinity;
    let s = 0.0;
    for (let l = i; l <= (j); l++) {
        s = (s + p[l]);
    }
    for (let k = i; k <= (j); k++) {
        let leftCost = 0.0;
        if ((k > i)) {
            leftCost = G[i][(k - 1)];
        }
        let rightCost = 0.0;
        if ((k < j)) {
            rightCost = G[(k + 1)][j];
        }
        let cost = ((leftCost + s) + rightCost);
        best = Math.min(best, cost);
    }
    return best;
  }
}

if (typeof module !== 'undefined') module.exports = LLPOptimalBinarySearchTree;

if (typeof require !== 'undefined' && require.main === module) {
    const p = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const G = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]];
    const prog = new LLPOptimalBinarySearchTree();
    prog.LLPOptimalBinarySearchTree(p, G);
    console.log(JSON.stringify(p));
    console.log(JSON.stringify(G));
}
