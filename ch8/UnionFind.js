/*
 * Disjoint-set with path compression in find and union-by-rank.
 *
 * Auto-generated from UnionFind.llp by lang/llcjssrc.py.
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

if (typeof module !== 'undefined') module.exports = UnionFind;

if (typeof require !== 'undefined' && require.main === module) {
    const parent = [3, 1, 6, 1, 6, 3, 6, 4];
    const x = 0;
    const prog = new UnionFind();
    const result = prog.Find(parent, x);
    console.log(JSON.stringify(result));
}
