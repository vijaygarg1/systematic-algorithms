/*
 * Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
 * path in the residual graph by depth-first search and push the largest
 * amount that fits along it, until no augmenting path remains.
 *
 * Auto-generated from FordFulkerson.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FordFulkerson {
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
    seen[s] = true;
    parent[s] = s;
    return this.dfs(c, f, s, t, parent, seen);
  }

  dfs(c, f, u, t, parent, seen) {
    if ((u === t)) {
        return 1;
    }
    for (let v = 0; v <= ((c.length - 1)); v++) {
        if (((!seen[v]) && (this.residual(c, f, u, v) > 0))) {
            seen[v] = true;
            parent[v] = u;
            if ((this.dfs(c, f, v, t, parent, seen) === 1)) {
                return 1;
            }
        }
    }
    return 0;
  }

  residual(c, f, u, v) {
    return (c[u][v] - f[u][v]);
  }
}

if (typeof module !== 'undefined') module.exports = FordFulkerson;

if (typeof require !== 'undefined' && require.main === module) {
    const c = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new FordFulkerson();
    const result = prog.maxflow(c, 0, 1);
    console.log(JSON.stringify(result));
}
