/*
 * 2-approximation vertex cover: greedily pick both endpoints of an
 * uncovered edge, matching bxx-approximate.tex Algorithm
 * ApproxVertexCover. The book's input is the edge set E, and the loop
 * walks it once, which is what makes the algorithm O(|E|).
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
    this.e = 0;
    while ((this.e < this.m)) {
        let u = this.eu[this.e];
        let v = this.ev[this.e];
        if (((!this.C[u]) && (!this.C[v]))) {
            this.C[u] = true;
            this.C[v] = true;
        }
        this.e = (this.e + 1);
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
