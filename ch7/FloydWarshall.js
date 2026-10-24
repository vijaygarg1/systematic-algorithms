/*
 * Floyd-Warshall APSP: triple loop on intermediate vertex, matching the
 * book's three nested \For loops (bxx-shortestPath.tex, alg:floyd-warshall).
 *
 * Auto-generated from FloydWarshall.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FloydWarshall {
  FloydWarshall(G) {
    this.G = G;
    this.n = this.G.length;
    for (let k = 0; k <= ((this.n - 1)); k++) {
        for (let i = 0; i <= ((this.n - 1)); i++) {
            for (let j = 0; j <= ((this.n - 1)); j++) {
                if (((this.G[i][k] !== 2147483647) && (this.G[k][j] !== 2147483647))) {
                    if (((this.G[i][k] + this.G[k][j]) < this.G[i][j])) {
                        this.G[i][j] = (this.G[i][k] + this.G[k][j]);
                    }
                }
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = FloydWarshall;

if (typeof require !== 'undefined' && require.main === module) {
    const G = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new FloydWarshall();
    prog.FloydWarshall(G);
    console.log(JSON.stringify(G));
}
