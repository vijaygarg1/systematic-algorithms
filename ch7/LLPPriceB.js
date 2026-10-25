/*
 * Price_b: find the minimum price vector using LLP.
 * forbidden(j): exists predecessor i with p[j] < p[i] - w[i,j].
 * advance: p[j] := max { p[i] - w[i,j] | i in pre(j) }.
 *
 * Auto-generated from LLPPriceB.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPPriceB {
  LLPPriceB(pre, w) {
    this.pre = pre;
    this.w = w;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((() => { for (const i of this.pre[j]) { if ((this.G[j] < (this.G[i] - this.w[i][j]))) return true; } return false; })()) {
                this.G[j] = this.maxPricePred(j, this.pre, this.w, this.G);
                _changed = true;
            }
        }
    }
  }

  maxPricePred(j, pre, w, G) {
    let best = G[j];
    for (let k = 0; k <= ((pre[j].length - 1)); k++) {
        let i = pre[j][k];
        let val = (G[i] - w[i][j]);
        if ((val > best)) {
            best = val;
        }
    }
    return best;
  }
}

if (typeof module !== 'undefined') module.exports = LLPPriceB;

if (typeof require !== 'undefined' && require.main === module) {
    const pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]];
    const w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPPriceB();
    prog.LLPPriceB(pre, w);
    console.log(JSON.stringify(pre));
    console.log(JSON.stringify(w));
}
