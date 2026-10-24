/*
 * LLP-ShortestPath: Dijkstra-style LLP with priority-queue scheduling.
 * always: parent[j][i] = (i in pre[j]) && (G[j] >= G[i] + w[i][j])
 * always: fixed[j] = (j == s) || (exists i : parent[j][i] && fixed[i])
 *
 * Auto-generated from LLPShortestPath.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPShortestPath {
  LLPShortestPath(s, pre, w) {
    this.s = s;
    this.pre = pre;
    this.w = w;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    this.fixed = new Array(this.n).fill(false);
    this.fixed[this.s] = true;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((!this.fixed[j])) {
                this.G[j] = this.minCrossCut(j, this.pre, this.w, this.G, this.fixed);
                this.fixed[j] = true;
                _changed = true;
            }
        }
    }
  }

  minCrossCut(j, pre, w, G, fixed) {
    let best = 2147483647;
    let k = 0;
    while ((k < pre[j].length)) {
        let i = pre[j][k];
        if (fixed[i]) {
            let d = (G[i] + w[i][j]);
            if ((d < best)) {
                best = d;
            }
        }
        k = (k + 1);
    }
    return best;
  }
}

if (typeof module !== 'undefined') module.exports = LLPShortestPath;

if (typeof require !== 'undefined' && require.main === module) {
    const pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]];
    const w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPShortestPath();
    prog.LLPShortestPath(0, pre, w);
    console.log(JSON.stringify(pre));
    console.log(JSON.stringify(w));
}
