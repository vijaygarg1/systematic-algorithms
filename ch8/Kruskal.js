/*
 * Classical Kruskal MST: sort edges, union-find with rank and path-compression.
 * Calls the sibling UnionFind program (`UnionFind.Union(parent, rank, u, v)`,
 * compiling to `new UnionFind().union(parent, rank, u, v)`) instead of
 * keeping its own copy of find/union-by-rank -- UnionFind.Union() already
 * finds both roots internally and reports whether they were distinct, so
 * Kruskal's main loop needs no separate find() call of its own. This makes
 * Kruskal.java a multi-file program: it requires UnionFind.java alongside
 * it to compile (see JAVA_MULTI_FILE in validate-site.py and the
 * `javaRequires` demo-spec field in llp.js).
 *
 * Auto-generated from Kruskal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class UnionFind {
  Find(parent, x) {
    let n = parent.length;
    if ((parent[x] !== x)) {
        parent[x] = this.Find(parent, parent[x]);
    }
    return parent[x];
  }

  Union(parent, rank, x, y) {
    let rx = this.Find(parent, x);
    let ry = this.Find(parent, y);
    if ((rx === ry)) {
        return false;
    }
    if ((rank[rx] < rank[ry])) {
        parent[rx] = ry;
    } else {
        if ((rank[rx] > rank[ry])) {
            parent[ry] = rx;
        } else {
            parent[ry] = rx;
            rank[rx] = (rank[rx] + 1);
        }
    }
    return true;
  }
}

class Kruskal {
  Kruskal(n, U, V, W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.m = this.U.length;
    this.T = new Array(this.m).fill(false);
    this.parent = new Array(this.n).fill(0);
    this.rank = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.parent[i] = i;
    }
    let chosen = 0;
    let e = 0;
    while (((e < this.m) && (chosen < (this.n - 1)))) {
        if (new UnionFind().Union(this.parent, this.rank, this.U[e], this.V[e])) {
            this.T[e] = true;
            chosen = (chosen + 1);
        }
        e = (e + 1);
    }
    return this.T;
  }
}

if (typeof module !== 'undefined') module.exports = Kruskal;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new Kruskal();
    const result = prog.Kruskal(U.length, U, V, W);
    console.log(JSON.stringify(result));
}
