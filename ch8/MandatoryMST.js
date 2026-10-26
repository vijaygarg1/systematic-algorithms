/*
 * MandatoryMST: composition program forcing the edges in subset M
 * into the spanning tree.  Halts "infeasible" (returns null) when a
 * mandatory edge closes a cycle with previously committed mandatory
 * edges; equivalently, when M itself is not acyclic.  Composed onto
 * LLP-Kruskal via predicate conjunction:
 *
 * Auto-generated from MandatoryMST.llp by lang/llcjssrc.py.
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

class MandatoryMST {
  MandatoryMST(u, v, M, parent) {
    this.u = u;
    this.v = v;
    this.M = M;
    this.parent = parent;
    this.n = this.u.length;
    this.G = new Array(this.n).fill(false);
    this.rank = new Array(this.parent.length).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((this.M[j] && (!this.G[j]))) {
                if ((new UnionFind().find(this.parent, this.u[j]) === new UnionFind().find(this.parent, this.v[j]))) {
                    return null;
                }
                this.G[j] = true;
                new UnionFind().union(this.parent, this.rank, this.u[j], this.v[j]);
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = MandatoryMST;

if (typeof require !== 'undefined' && require.main === module) {
    const u = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const M = [false, false, false, false];
    const parent = [0, 0, 0, 2];
    const prog = new MandatoryMST();
    const result = prog.MandatoryMST(u, v, M, parent);
    console.log(JSON.stringify(result));
}
