/*
 * Classical 2-SAT via Kosaraju's strongly-connected components.
 *
 * Auto-generated from TwoSAT.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class TwoSAT {
  TwoSAT(clauseA, clauseB) {
    this.clauseA = clauseA;
    this.clauseB = clauseB;
    this.m = this.clauseA.length;
    this.n = Math.max(0, (() => { let _a = -2147483647, _s = false; for (let i = 0; i <= ((this.m - 1)); i++) { const _v = Math.max(Math.abs(this.clauseA[i]), Math.abs(this.clauseB[i])); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
    this.sz = ((2 * this.n) + 2);
    this.cap = ((2 * this.m) + 1);
    this.adjFwd = Array.from({length: this.sz}, () => new Array(this.cap).fill(0));
    this.adjRev = Array.from({length: this.sz}, () => new Array(this.cap).fill(0));
    this.degFwd = new Array(this.sz).fill(0);
    this.degRev = new Array(this.sz).fill(0);
    for (let i = 0; i <= ((this.m - 1)); i++) {
        this.addImplication(this.litIndex((0 - this.clauseA[i]), this.n), this.litIndex(this.clauseB[i], this.n), this.adjFwd, this.degFwd, this.adjRev, this.degRev);
        this.addImplication(this.litIndex((0 - this.clauseB[i]), this.n), this.litIndex(this.clauseA[i], this.n), this.adjFwd, this.degFwd, this.adjRev, this.degRev);
    }
    let order = new Array(this.sz).fill(0);
    let orderSize = 0;
    let v = 0;
    let visited = new Array(this.sz).fill(false);
    while ((v < this.sz)) {
        if ((!visited[v])) {
            orderSize = this.dfs1(v, this.adjFwd, this.degFwd, visited, order, orderSize);
        }
        v = (v + 1);
    }
    let comp = new Array(this.sz).fill(0);
    for (let k = 0; k <= ((this.sz - 1)); k++) {
        comp[k] = (0 - 1);
    }
    let numComp = 0;
    let idx = (orderSize - 1);
    while ((idx >= 0)) {
        if ((comp[order[idx]] < 0)) {
            this.dfs2(order[idx], this.adjRev, this.degRev, comp, numComp);
            numComp = (numComp + 1);
        }
        idx = (idx - 1);
    }
    let result = new Array((this.n + 1)).fill(false);
    for (let x = 1; x <= (this.n); x++) {
        result[x] = (comp[x] > comp[(this.n + x)]);
    }
    return result;
  }

  addImplication(from_, to, adjFwd, degFwd, adjRev, degRev) {
    adjFwd[from_][degFwd[from_]] = to;
    degFwd[from_] = (degFwd[from_] + 1);
    adjRev[to][degRev[to]] = from_;
    degRev[to] = (degRev[to] + 1);
  }

  dfs1(u, adj, deg, visited, order, orderSize) {
    visited[u] = true;
    for (let i = 0; i <= ((deg[u] - 1)); i++) {
        let w = adj[u][i];
        if ((!visited[w])) {
            orderSize = this.dfs1(w, adj, deg, visited, order, orderSize);
        }
    }
    order[orderSize] = u;
    return (orderSize + 1);
  }

  dfs2(u, adj, deg, comp, c) {
    comp[u] = c;
    for (let i = 0; i <= ((deg[u] - 1)); i++) {
        let w = adj[u][i];
        if ((comp[w] < 0)) {
            this.dfs2(w, adj, deg, comp, c);
        }
    }
  }

  litIndex(lit, n) {
    if ((lit > 0)) {
        return lit;
    }
    return (n + (0 - lit));
  }
}

if (typeof module !== 'undefined') module.exports = TwoSAT;

if (typeof require !== 'undefined' && require.main === module) {
    const clauseA = [0, 0, 1, 2];
    const clauseB = [0, 0, 1, 1];
    const prog = new TwoSAT();
    const result = prog.TwoSAT(clauseA, clauseB);
    console.log(JSON.stringify(result));
}
