/*
 * Traversal, as bxx-graph.tex's Algorithm Traversal states it:
 *
 * var S: set of int                 // discovered but not yet explored
 * S.add(s); visited[s] := 1
 * while not S.empty():
 * j := S.removeAny(visited)
 * forall k in succ(j):
 * if visited[k] = 0: visited[k] := 1; S.add(k)
 * return visited
 *
 * The book's S is a SET and removeAny may return any member -- that is the
 * point of this algorithm: BFS-Traversal and DFS-Traversal are the same
 * code with the order of removal pinned down. LL has no scalar set type
 * with a "remove any" operation, so S is a list<int> here and removeFirst
 * makes one concrete choice. Any other choice visits the same vertices;
 * only the order differs, and the result (the reachable set) is the same.
 *
 * Auto-generated from Traversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Traversal {
  Traversal(succ, s) {
    this.succ = succ;
    this.s = s;
    this.n = this.succ.length;
    this.visited = new Array(this.n).fill(0);
    this.S = [];
    this.S.push(this.s);
    this.visited[this.s] = 1;
    while ((!(this.S.length === 0))) {
        let j = this.S.shift();
        for (const k of this.succ[j]) {
            if ((this.visited[k] === 0)) {
                this.visited[k] = 1;
                this.S.push(k);
            }
        }
    }
    return this.visited;
  }
}

if (typeof module !== 'undefined') module.exports = Traversal;
