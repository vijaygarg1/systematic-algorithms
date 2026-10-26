/*
 * LLP parallel 2-approximation for Vertex Cover: pick every endpoint of a lex-minimal uncovered edge.
 *
 * Auto-generated from LLPLexMatchVertexCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPLexMatchVertexCover {
  LLPLexMatchVertexCover(adj) {
    this.adj = adj;
    this.n = this.adj.length;
    this.G = new Array(this.n).fill(false);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this.isLexMin(j)) {
                let i = this.minNbr(j);
                this.G[j] = true;
                this.G[i] = true;
                _changed = true;
            }
        }
    }
    return this.G;
  }

  isLexMin(j) {
    if (this.G[j]) {
        return false;
    }
    let i = this.minNbr(j);
    if ((i < 0)) {
        return false;
    }
    return (this.minNbr(i) === j);
  }

  minNbr(u) {
    let k = 0;
    while ((k < this.adj[u].length)) {
        if ((!this.G[this.adj[u][k]])) {
            return this.adj[u][k];
        }
        k = (k + 1);
    }
    return (0 - 1);
  }
}

if (typeof module !== 'undefined') module.exports = LLPLexMatchVertexCover;

if (typeof require !== 'undefined' && require.main === module) {
    const adj = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPLexMatchVertexCover();
    const result = prog.LLPLexMatchVertexCover(adj);
    console.log(JSON.stringify(result));
}
