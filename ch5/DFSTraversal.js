/*
 * Recursive DFS recording discovery and finish times.
 *
 * Auto-generated from DFSTraversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class DFSTraversal {
  traverse(succ) {
    this.succ = succ;
    this.n = this.succ.length;
    this.G = new Array(this.n).fill(0);
    this.parent = new Array(this.n).fill(0);
    this.discovered = new Array(this.n).fill(0);
    this.finished = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.parent[i] = (-1);
    }
    let t = new Array(1).fill(0);
    t[0] = 1;
    this.visit(0, this.succ, this.G, this.parent, this.discovered, this.finished, t);
    return this.discovered;
  }

  visit(j, succ, G, parent, discovered, finished, t) {
    G[j] = 1;
    discovered[j] = t[0];
    t[0] = (t[0] + 1);
    for (const k of succ[j]) {
        if ((G[k] === 0)) {
            parent[k] = j;
            this.visit(k, succ, G, parent, discovered, finished, t);
        }
    }
    finished[j] = t[0];
    t[0] = (t[0] + 1);
  }
}

if (typeof module !== 'undefined') module.exports = DFSTraversal;
