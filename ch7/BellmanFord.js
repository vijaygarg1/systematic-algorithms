/*
 * Classical Bellman-Ford: n-1 relaxation passes over every edge.
 *
 * Auto-generated from BellmanFord.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BellmanFord {
  shortestPath(n, U, V, W, s) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.s = s;
    this.dist = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.dist[i] = 2147483647;
    }
    this.dist[this.s] = 0;
    for (let k = 1; k <= ((this.n - 1)); k++) {
        for (let e = 0; e <= ((this.U.length - 1)); e++) {
            let u = this.U[e];
            let v = this.V[e];
            if (((this.dist[u] !== 2147483647) && ((this.dist[u] + this.W[e]) < this.dist[v]))) {
                this.dist[v] = (this.dist[u] + this.W[e]);
            }
        }
    }
    return this.dist;
  }
}

if (typeof module !== 'undefined') module.exports = BellmanFord;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new BellmanFord();
    const result = prog.shortestPath(U.length, U, V, W, 0);
    console.log(JSON.stringify(result));
}
