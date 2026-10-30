/*
 * Classical Kruskal MST: sort edges, union-find with rank and path-compression.
 * Calls the sibling UnionFind program (`UnionFind.union(parent, rank, u, v)`,
 * compiling to `new UnionFind().union(parent, rank, u, v)`) instead of
 * keeping its own copy of find/union-by-rank -- UnionFind.union() already
 * finds both roots internally and reports whether they were distinct, so
 *
 * Auto-generated from Kruskal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class UnionFind {
  find(parent, x) {
    let n = parent.length;
    if ((parent[x] !== x)) {
        parent[x] = this.find(parent, parent[x]);
    }
    return parent[x];
  }

  union(parent, rank, x, y) {
    let rx = this.find(parent, x);
    let ry = this.find(parent, y);
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
  mst(n, U, V, W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    let m = this.U.length;
    let T = new Array(m).fill(false);
    let parent = new Array(this.n).fill(0);
    let rank = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        parent[i] = i;
    }
    let chosen = 0;
    let e = 0;
    while (((e < m) && (chosen < (this.n - 1)))) {
        if (new UnionFind().union(parent, rank, this.U[e], this.V[e])) {
            T[e] = true;
            chosen = (chosen + 1);
        }
        e = (e + 1);
    }
    return T;
  }
}

if (typeof module !== 'undefined') module.exports = Kruskal;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new Kruskal();
    const result = prog.mst(U.length, U, V, W);
    console.log(JSON.stringify(result));
}
