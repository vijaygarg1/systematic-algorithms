/*
 * LLP housing market, matching bxx-housing.tex Algorithm
 * LLP-Housing-Market-Algorithm. Houses are identified with agent
 * indices (agent j initially owns house j). wish(i) is the house agent
 * i currently proposes for. S(G), the largest submatching, is the set
 * of agents lying on a cycle of the wish functional graph (i -> wish(i))
 * -- exactly the agents who can all trade among themselves. forbidden:
 * agent j is not itself in S(G) but wishes for a house held by an agent
 * who is; advance moves j to its next preference.
 *
 * S(G) is built for the whole market at once, which is what makes the
 * round cost O(n) and the algorithm O(n^2), as the chapter states: every
 * agent wishes for exactly one house, so the wish graph is functional
 * and its cycles are precisely what survives repeatedly discarding the
 * agents nobody wishes for. Asking the question one agent at a time
 * instead -- following wishes from j until they return to j -- answers
 * it in O(n) per agent and so costs O(n^2) per round, a full factor of n
 * too much. inS[] is rebuilt only after an advance has moved a wish.
 *
 * Earlier version of this file (kept for reference, not used): treated
 * an agent as "in the submatching" whenever no other agent currently
 * wished for the same house -- i.e. current-wish uniqueness -- rather
 * than genuine cycle membership in the wish graph. This is a different,
 * weaker condition: on the book's own running example (G=[0,0,0,0],
 * wishes 0->1, 1->0, 2->0, 3->1, 0-indexed), the old inSubmatching(0,...)
 * returned false (since agent 3 also wishes for house 1... texts differ
 * per index, see the book's own trace) where the book states agents
 * {1,2} (1-indexed) are in S(G) via the mutual wish-cycle 0<->1.
 *
 * class LLPHousingMarket {
 * int[] LLPHousingMarket(int[][] pref) {
 * int n = pref.length;
 * int[] G = new int[n];
 * forall k in [0..n-1] : G[k] = 0;
 * forbidden (j) : !inSubmatching(j, G, pref) && wishInSubmatching(j, G, pref) =>
 * advance : G[j] = G[j] + 1;
 * return G;
 * }
 *
 * boolean inSubmatching(int j, int[] G, int[][] pref) {
 * int n = G.length;
 * int target = pref[j][G[j]];
 * int i = 0;
 * while (i < n) {
 * if (i != j && pref[i][G[i]] == target) {
 * return false;
 * };
 * i = i + 1;
 * };
 * return true;
 * }
 *
 * boolean wishInSubmatching(int j, int[] G, int[][] pref) {
 * int n = G.length;
 * int wish = pref[j][G[j]];
 * int i = 0;
 * while (i < n) {
 * if (pref[i][G[i]] == wish && inSubmatching(i, G, pref)) {
 * return true;
 * };
 * i = i + 1;
 * };
 * return false;
 * }
 * }
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
