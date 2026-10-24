/*
 * Classical augmenting-path bipartite matching, matching
 * bxx-matchingReduced.tex Algorithm Seq-AugmentingPath (algo:seq-bipartite):
 * for each L-vertex j in turn, look for an alternating path to some free
 * R-vertex k (S[k] unmatched). The book's abstract "there exists an
 * alternating path..." step, plus its "assign S[] along the path"
 * follow-up, are both realized here by the alternatingPath(...) DFS --
 * the standard way of implementing that check.
 *
 * The graph is the book's (L, R, E): adj[j] lists the R-neighbours of the
 * L-vertex j, and nr is |R|, which cannot be recovered from the lists when
 * some R-vertex has no incident edge. A dense |L| x |R| matrix would make
 * every DFS step scan all of R however few edges exist -- and reading such
 * a matrix is Theta(|L| * |R|) before the algorithm starts, so no bound in
 * |E| is reachable from it at all.
 *
 * Auto-generated from SeqAugmentingPath.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class SeqAugmentingPath {
  SeqAugmentingPath(adj, nr) {
    this.adj = adj;
    this.nr = nr;
    this.n = this.adj.length;
    this.m = this.nr;
    this.G = new Array(this.n).fill(0);
    this.S = new Array(this.m).fill(0);
    for (let j = 0; j <= ((this.n - 1)); j++) {
        this.G[j] = 0;
    }
    for (let k = 0; k <= ((this.m - 1)); k++) {
        this.S[k] = (0 - 1);
    }
    for (let j = 0; j <= ((this.n - 1)); j++) {
        let seen = new Array(this.m).fill(false);
        if (this.alternatingPath(j, this.adj, this.S, seen)) {
            this.G[j] = 1;
        }
    }
    return this.G;
  }

  alternatingPath(j, adj, S, seen) {
    let t = 0;
    while ((t < adj[j].length)) {
        let k = adj[j][t];
        if ((!seen[k])) {
            seen[k] = true;
            if (((S[k] === (0 - 1)) || this.alternatingPath(S[k], adj, S, seen))) {
                S[k] = j;
                return true;
            }
        }
        t = (t + 1);
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = SeqAugmentingPath;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new SeqAugmentingPath();
    const result = prog.SeqAugmentingPath(adj, adj.length);
    console.log(JSON.stringify(result));
}
