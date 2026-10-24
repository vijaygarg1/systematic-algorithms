/*
 * LLP-WeightedVertexCover: primal-dual 2-approximation for weighted
 * vertex cover, matching bxx-approximate.tex Algorithm
 * LLP-WeightedVertexCover (fig:llp-wvc). The lattice is indexed by EDGES,
 * as the book's "G: array[E] of real" and "Forbidden(e)" say: G[e] is the
 * price on edge e. A vertex v is tight when its incident prices saturate
 * w[v]; an edge is slack when neither endpoint is tight; slackMin(e)
 * further requires e to be lex-minimal among every slack edge sharing an
 * endpoint with it. Advancing raises the edge's price by the smaller of
 * its two endpoints' remaining budgets, tightening at least one.
 *
 * The graph arrives as an edge list -- eu[e], ev[e] are the endpoints of
 * edge e, with the edges in lex order -- which is the book's
 * "E: set of edges". inc[v] lists the edges incident to v, built once from
 * that list in O(n + m). No n x n structure appears anywhere: the earlier
 * version indexed both the lattice (G[u][v]) and the graph (adj[u][v]) by
 * vertex PAIRS, which cost Theta(n^2) in memory and, because the forbidden
 * clause ranged over pairs, made the rescan enumerate all n^2 of them in
 * every sweep however few edges the graph had. Indexing by edge makes a
 * sweep O(m).
 *
 * Two things keep the predicate cheap. First, price[] is maintained by the
 * advance instead of being re-summed on every call, so tight() is O(1)
 * rather than an O(nv) sum. Second, the lex-minimality test does not scan
 * all edge pairs: with the edges in lex order, the lex-least slack edge at
 * a vertex is simply the first slack one in inc[v], so e is lex-minimal
 * among the slack edges touching it exactly when it is first at both ends.
 * minSlackEdge[] is rebuilt only after an advance has moved a price, and a
 * rebuild consults each vertex's own incident edges only, so it is O(m)
 * rather than O(n^2).
 *
 * Auto-generated from LLPWeightedVertexCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPWeightedVertexCover {
  LLPWeightedVertexCover(eu, ev, w) {
    this.eu = eu;
    this.ev = ev;
    this.w = w;
    this.n = this.eu.length;
    this.nv = this.w.length;
    this.m = this.eu.length;
    this.G = new Array(this.m).fill(0.0);
    this.price = new Array(this.nv).fill(0.0);
    this.minSlackEdge = new Array(this.nv).fill(0);
    this.edeg = new Array(this.nv).fill(0);
    this.efill = new Array(this.nv).fill(0);
    this.inc = new Array(this.nv).fill(null);
    this.fresh = false;
    for (let e = 0; e <= ((this.m - 1)); e++) {
        this.edeg[this.eu[e]] = (this.edeg[this.eu[e]] + 1);
        this.edeg[this.ev[e]] = (this.edeg[this.ev[e]] + 1);
    }
    for (let v = 0; v <= ((this.nv - 1)); v++) {
        this.inc[v] = new Array(this.edeg[v]).fill(0);
    }
    for (let e = 0; e <= ((this.m - 1)); e++) {
        this.inc[this.eu[e]][this.efill[this.eu[e]]] = e;
        this.efill[this.eu[e]] = (this.efill[this.eu[e]] + 1);
        this.inc[this.ev[e]][this.efill[this.ev[e]]] = e;
        this.efill[this.ev[e]] = (this.efill[this.ev[e]] + 1);
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let e = 0; e <= ((this.n - 1)); e++) {
            if (this.slackMin(e)) {
                let step = Math.min((this.w[this.eu[e]] - this.price[this.eu[e]]), (this.w[this.ev[e]] - this.price[this.ev[e]]));
                this.G[e] = (this.G[e] + step);
                this.price[this.eu[e]] = (this.price[this.eu[e]] + step);
                this.price[this.ev[e]] = (this.price[this.ev[e]] + step);
                this.fresh = false;
                _changed = true;
            }
        }
    }
    let C = new Array(this.nv).fill(false);
    for (let i = 0; i <= ((this.nv - 1)); i++) {
        C[i] = this.tight(i);
    }
    return C;
  }

  tight(v) {
    return (this.price[v] >= this.w[v]);
  }

  slack(e) {
    return ((!this.tight(this.eu[e])) && (!this.tight(this.ev[e])));
  }

  refresh() {
    if ((!this.fresh)) {
        for (let v = 0; v <= ((this.nv - 1)); v++) {
            this.minSlackEdge[v] = this.firstSlackEdge(v);
        }
        this.fresh = true;
    }
  }

  firstSlackEdge(v) {
    let k = 0;
    while ((k < this.inc[v].length)) {
        if (this.slack(this.inc[v][k])) {
            return this.inc[v][k];
        }
        k = (k + 1);
    }
    return (0 - 1);
  }

  slackMin(e) {
    this.refresh();
    if ((!this.slack(e))) {
        return false;
    }
    return ((this.minSlackEdge[this.eu[e]] === e) && (this.minSlackEdge[this.ev[e]] === e));
  }
}

if (typeof module !== 'undefined') module.exports = LLPWeightedVertexCover;

if (typeof require !== 'undefined' && require.main === module) {
    const eu = [0, 0, 1, 2];
    const ev = [0, 0, 1, 1];
    const w = [1.0, 2.0, 3.0, 4.0];
    const prog = new LLPWeightedVertexCover();
    const result = prog.LLPWeightedVertexCover(eu, ev, w);
    console.log(JSON.stringify(result));
}
