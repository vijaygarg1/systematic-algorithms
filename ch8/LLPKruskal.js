/*
 * LLP-Kruskal: edge-inclusion lattice driven by union-find.
 * Calls the sibling UnionFind program (`UnionFind.Find(parent, x)`,
 * `UnionFind.Union(parent, rank, x, y)`) instead of keeping its own
 * copy of find/union -- same multi-file treatment as Kruskal.llp. This
 * makes LLPKruskal.java a multi-file program: it requires UnionFind.java
 * alongside it to compile (see JAVA_MULTI_FILE in validate-site.py and
 * the `javaRequires` demo-spec field in llp.js).
 *
 * Auto-generated from LLPKruskal.llp by lang/llcjssrc.py.
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

class LLPKruskal {
  LLPKruskal(u, v, parent) {
    this.u = u;
    this.v = v;
    this.parent = parent;
    this.n = this.u.length;
    this.G = new Array(this.n).fill(false);
    this.rank = new Array(this.parent.length).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((!this.G[j]) && (new UnionFind().Find(this.parent, this.u[j]) !== new UnionFind().Find(this.parent, this.v[j])))) {
                this.G[j] = true;
                new UnionFind().Union(this.parent, this.rank, this.u[j], this.v[j]);
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPKruskal;

if (typeof require !== 'undefined' && require.main === module) {
    const u = [0, 0, 1, 2];
    const v = [0, 0, 1, 1];
    const parent = [0, 0, 0, 2];
    const prog = new LLPKruskal();
    const result = prog.LLPKruskal(u, v, parent);
    console.log(JSON.stringify(result));
}
