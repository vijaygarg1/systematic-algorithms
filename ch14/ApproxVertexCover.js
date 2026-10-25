/*
 * 2-approximation vertex cover: greedily pick both endpoints of an
 * uncovered edge, matching bxx-approximate.tex Algorithm
 * ApproxVertexCover. The book's input is the edge set E, and the loop
 * walks it once, which is what makes the algorithm O(|E|).
 *
 * The book's "remove all edges incident to u or v from E'" needs no
 * bookkeeping: an edge is still in E' exactly when neither of its
 * endpoints has been taken into C, so the test below is that membership
 * test. Taking the edge set rather than an n x n adjacency matrix is
 * what keeps this O(|E|) -- a matrix forces every pass to scan all n^2
 * cells no matter how few edges exist, and reading the input alone would
 * already be Theta(n^2).
 *
 * Auto-generated from ApproxVertexCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ApproxVertexCover {
  ApproxVertexCover(eu, ev, n) {
    this.eu = eu;
    this.ev = ev;
    this.n = n;
    this.C = new Array(this.n).fill(false);
    this.m = this.eu.length;
    for (let e = 0; e <= ((this.m - 1)); e++) {
        let u = this.eu[e];
        let v = this.ev[e];
        if (((!this.C[u]) && (!this.C[v]))) {
            this.C[u] = true;
            this.C[v] = true;
        }
    }
    return this.C;
  }
}

if (typeof module !== 'undefined') module.exports = ApproxVertexCover;

if (typeof require !== 'undefined' && require.main === module) {
    const eu = [0, 0, 1, 2];
    const ev = [0, 0, 1, 1];
    const prog = new ApproxVertexCover();
    const result = prog.ApproxVertexCover(eu, ev, eu.length);
    console.log(JSON.stringify(result));
}
