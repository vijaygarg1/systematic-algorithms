/*
 * Koenig's theorem: minimum vertex cover of size |M| from a maximum
 * bipartite matching, via alternating reachability. Matches
 * bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
 * of vertices reachable from an unmatched L-vertex by an alternating path
 * (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
 *
 * Auto-generated from VertexCoverFromMatching.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class VertexCoverFromMatching {
  VertexCoverFromMatching(adj, nr, matchL) {
    this.adj = adj;
    this.nr = nr;
    this.matchL = matchL;
    this.n = this.adj.length;
    this.L = this.adj.length;
    this.R = this.nr;
    this.matchR = new Array(this.R).fill(0);
    for (let v = 0; v <= ((this.R - 1)); v++) {
        this.matchR[v] = (0 - 1);
    }
    for (let j = 0; j <= ((this.L - 1)); j++) {
        if ((this.matchL[j] !== (0 - 1))) {
            this.matchR[this.matchL[j]] = j;
        }
    }
    let inZ = new Array((this.L + this.R)).fill(false);
    let Q = new Array((this.L + this.R)).fill(0);
    let head = 0;
    let tail = 0;
    for (let j = 0; j <= ((this.L - 1)); j++) {
        if ((this.matchL[j] === (0 - 1))) {
            inZ[j] = true;
            Q[tail] = j;
            tail = (tail + 1);
        }
    }
    while ((head < tail)) {
        let w = Q[head];
        head = (head + 1);
        if ((w < this.L)) {
            let t = 0;
            while ((t < this.adj[w].length)) {
                let v = this.adj[w][t];
                if (((this.matchL[w] !== v) && (!inZ[(this.L + v)]))) {
                    inZ[(this.L + v)] = true;
                    Q[tail] = (this.L + v);
                    tail = (tail + 1);
                }
                t = (t + 1);
            }
        } else {
            let r = (w - this.L);
            if (((this.matchR[r] !== (0 - 1)) && (!inZ[this.matchR[r]]))) {
                inZ[this.matchR[r]] = true;
                Q[tail] = this.matchR[r];
                tail = (tail + 1);
            }
        }
    }
    let C = new Array((this.L + this.R)).fill(false);
    for (let j = 0; j <= ((this.L - 1)); j++) {
        C[j] = (!inZ[j]);
    }
    for (let v = 0; v <= ((this.R - 1)); v++) {
        C[(this.L + v)] = inZ[(this.L + v)];
    }
    return C;
  }
}

if (typeof module !== 'undefined') module.exports = VertexCoverFromMatching;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const matchL = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new VertexCoverFromMatching();
    const result = prog.VertexCoverFromMatching(adj, adj.length, matchL);
    console.log(JSON.stringify(result));
}
