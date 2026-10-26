/*
 * LLP housing market, matching bxx-housing.tex Algorithm
 * LLP-Housing-Market-Algorithm. Houses are identified with agent
 * indices (agent j initially owns house j). wish(i) is the house agent
 * i currently proposes for. S(G), the largest submatching, is the set
 * of agents lying on a cycle of the wish functional graph (i -> wish(i))
 *
 * Auto-generated from LLPHousingMarket.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPHousingMarket {
  LLPHousingMarket(pref) {
    this.pref = pref;
    this.n = this.pref.length;
    this.G = new Array(this.n).fill(0);
    this.inS = new Array(this.n).fill(false);
    this.indeg = new Array(this.n).fill(0);
    this.q = new Array(this.n).fill(0);
    this.fresh = false;
    for (let k = 0; k <= ((this.n - 1)); k++) {
        this.G[k] = 0;
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((!this.inSubmatching(j, this.G, this.pref)) && this.inSubmatching(this.wish(j, this.G, this.pref), this.G, this.pref))) {
                this.G[j] = (this.G[j] + 1);
                this.fresh = false;
                _changed = true;
            }
        }
    }
    return this.G;
  }

  wish(i, G, pref) {
    return pref[i][G[i]];
  }

  inSubmatching(j, G, pref) {
    this.refresh(G, pref);
    return this.inS[j];
  }

  refresh(G, pref) {
    if (this.fresh) {
        return;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.inS[i] = true;
        this.indeg[i] = 0;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.indeg[this.wish(i, G, pref)] = (this.indeg[this.wish(i, G, pref)] + 1);
    }
    let qt = 0;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        if ((this.indeg[i] === 0)) {
            this.q[qt] = i;
            qt = (qt + 1);
        }
    }
    let qh = 0;
    while ((qh < qt)) {
        let x = this.q[qh];
        qh = (qh + 1);
        this.inS[x] = false;
        let y = this.wish(x, G, pref);
        this.indeg[y] = (this.indeg[y] - 1);
        if ((this.indeg[y] === 0)) {
            this.q[qt] = y;
            qt = (qt + 1);
        }
    }
    this.fresh = true;
  }
}

if (typeof module !== 'undefined') module.exports = LLPHousingMarket;

if (typeof require !== 'undefined' && require.main === module) {
    const pref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPHousingMarket();
    const result = prog.LLPHousingMarket(pref);
    console.log(JSON.stringify(result));
}
