/*
 * DFS-Traversal, as bxx-graph.tex's Algorithm DFS-Traversal states it:
 *
 * var tick: int initially 1
 * function DFS(j):
 * visited[j] := 1
 * discovered[j] := tick; tick := tick + 1
 * for k in succ(j):
 * if visited[k] = 0: parent[k] := j; DFS(k)
 * finished[j] := tick; tick := tick + 1
 * DFS(s)
 *
 * The book's DFS(j) closes over visited/parent/discovered/finished/tick;
 * LL has no closures, so DFS takes them as parameters.
 *
 * tick is the book's `var tick: int initially 1`, a counter shared by every
 * DFS call. A plain `int tick` would be local to the entry method and
 * invisible to DFS (verified: the generated Java does not compile), so it
 * travels as a one-slot array -- the standard way to pass a mutable scalar.
 *
 * The book declares four outputs (visited, parent, discovered, finished);
 * LL returns one value, so discovered is returned and the rest stay
 * internal, exactly as they are computed here.
 *
 * Auto-generated from DFSTraversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class DFSTraversal {
  DFSTraversal(succ, s) {
    this.succ = succ;
    this.s = s;
    this.n = this.succ.length;
    this.visited = new Array(this.n).fill(0);
    this.parent = new Array(this.n).fill(0);
    this.discovered = new Array(this.n).fill(0);
    this.finished = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.parent[i] = (-1);
    }
    let tick = new Array(1).fill(0);
    tick[0] = 1;
    this.DFS(this.s, this.succ, this.visited, this.parent, this.discovered, this.finished, tick);
    return this.discovered;
  }

  DFS(j, succ, visited, parent, discovered, finished, tick) {
    visited[j] = 1;
    discovered[j] = tick[0];
    tick[0] = (tick[0] + 1);
    for (const k of succ[j]) {
        if ((visited[k] === 0)) {
            parent[k] = j;
            this.DFS(k, succ, visited, parent, discovered, finished, tick);
        }
    }
    finished[j] = tick[0];
    tick[0] = (tick[0] + 1);
  }
}

if (typeof module !== 'undefined') module.exports = DFSTraversal;
