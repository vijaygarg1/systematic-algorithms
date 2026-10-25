/*
 * Classical Bellman-Ford, as bxx-shortestPath.tex's Algorithm BellmanFord
 * states it:
 *
 * forall v in V: d[v] := infinity; p[v] := null
 * d[s] := 0
 * for k = 1 to n-1:
 * forall edge (u,v) in E:
 * if d[u] + w[u,v] < d[v]: d[v] := d[u] + w[u,v];  p[v] := u
 * forall edge (u,v) in E:
 * if d[u] + w[u,v] < d[v]: return "Negative cycle detected"
 * return d[], p[]
 *
 * Edges arrive as three parallel arrays: edge e runs U[e] -> V[e] with
 * weight W[e]. That is the book's "forall edge (u,v) in E" written out.
 *
 * p[v] = -1 stands for the book's null: -1 is not a vertex, since vertices
 * are 0..n-1. The book declares p as a second output; LL returns one value,
 * so d is returned and p is computed alongside it.
 *
 * A negative cycle makes the program return null, LL's "no solution".
 *
 * d[u] != infinity guards the relaxation: the book's d[u] + w[u,v] is
 * infinity + w = infinity when u is unreached, which is never < d[v]. In
 * finite ints infinity + w overflows to a large negative instead, so the
 * test has to be made explicitly.
 *
 * The k loop and the edge loops are `for`, not `forall`: each pass reads
 * the distances the previous pass wrote.
 *
 * Auto-generated from BellmanFord.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BellmanFord {
  BellmanFord(n, U, V, W, s) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.s = s;
    this.d = new Array(this.n).fill(0);
    this.p = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.d[i] = 2147483647;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.p[i] = (-1);
    }
    this.d[this.s] = 0;
    for (let k = 1; k <= ((this.n - 1)); k++) {
        for (let e = 0; e <= ((this.U.length - 1)); e++) {
            let u = this.U[e];
            let v = this.V[e];
            if (((this.d[u] !== 2147483647) && ((this.d[u] + this.W[e]) < this.d[v]))) {
                this.d[v] = (this.d[u] + this.W[e]);
                this.p[v] = u;
            }
        }
    }
    for (let e = 0; e <= ((this.U.length - 1)); e++) {
        let u = this.U[e];
        let v = this.V[e];
        if (((this.d[u] !== 2147483647) && ((this.d[u] + this.W[e]) < this.d[v]))) {
            return null;
        }
    }
    return this.d;
  }
}

if (typeof module !== 'undefined') module.exports = BellmanFord;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new BellmanFord();
    const result = prog.BellmanFord(U.length, U, V, W, 0);
    console.log(JSON.stringify(result));
}
