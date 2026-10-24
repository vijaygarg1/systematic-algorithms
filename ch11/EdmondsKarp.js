/*
 * Classical Edmonds-Karp maximum flow: Ford-Fulkerson with the augmenting
 * path chosen by BREADTH-first search, so each augmentation uses a path
 * with the fewest arcs. That single change is what bounds the number of
 * augmentations by O(VE) and makes the running time independent of the
 * capacities.
 *
 * f is the flow matrix; residual(u, v) = c[u][v] - f[u][v] is the spare
 * capacity of arc (u,v), and pushing b along (u,v) also subtracts b from
 * f[v][u], which is what lets a later path cancel earlier flow. The BFS
 * carries its own queue q with head/tail indices and leaves the path in
 * parent[], as a chain of predecessors running back from t to s.
 *
 * Auto-generated from EdmondsKarp.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class EdmondsKarp {
  maxflow(c, s, t) {
    this.c = c;
    this.s = s;
    this.t = t;
    this.n = this.c.length;
    this.f = Array.from({length: this.n}, () => new Array(this.n).fill(0));
    this.parent = new Array(this.n).fill(0);
    while ((this.findAugmentingPath(this.c, this.f, this.s, this.t, this.parent) === 1)) {
        this.augment(this.f, this.s, this.t, this.parent, this.bottleneck(this.c, this.f, this.s, this.t, this.parent));
    }
    return this.f;
  }

  bottleneck(c, f, s, v, parent) {
    if ((v === s)) {
        return 2147483647;
    }
    return Math.min(this.residual(c, f, parent[v], v), this.bottleneck(c, f, s, parent[v], parent));
  }

  augment(f, s, v, parent, b) {
    if ((v === s)) {
        return;
    }
    let u = parent[v];
    f[u][v] = (f[u][v] + b);
    f[v][u] = (f[v][u] - b);
    this.augment(f, s, u, parent, b);
  }

  findAugmentingPath(c, f, s, t, parent) {
    let seen = new Array(c.length).fill(false);
    let q = new Array(c.length).fill(0);
    q[0] = s;
    seen[s] = true;
    parent[s] = s;
    return this.bfs(c, f, t, parent, seen, q, 0, 1);
  }

  bfs(c, f, t, parent, seen, q, head, tail) {
    if ((head >= tail)) {
        return 0;
    }
    let u = q[head];
    if ((u === t)) {
        return 1;
    }
    for (let v = 0; v <= ((c.length - 1)); v++) {
        if (((!seen[v]) && (this.residual(c, f, u, v) > 0))) {
            seen[v] = true;
            parent[v] = u;
            q[tail] = v;
            tail = (tail + 1);
        }
    }
    return this.bfs(c, f, t, parent, seen, q, (head + 1), tail);
  }

  residual(c, f, u, v) {
    return (c[u][v] - f[u][v]);
  }
}

if (typeof module !== 'undefined') module.exports = EdmondsKarp;

if (typeof require !== 'undefined' && require.main === module) {
    const c = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new EdmondsKarp();
    const result = prog.maxflow(c, 0, 1);
    console.log(JSON.stringify(result));
}
