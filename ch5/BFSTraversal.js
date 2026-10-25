/*
 * BFS-Traversal, exactly as bxx-graph.tex's Algorithm BFS-Traversal
 * states it:
 *
 * var S: queue of int               // discovered but not yet explored
 * S.enqueue(s); dist[s] := 0
 * while not S.empty():
 * j := S.dequeue()
 * forall k in succ(j):
 * if dist[k] > dist[j] + 1:
 * dist[k] := dist[j] + 1; parent[k] := j; S.enqueue(k)
 * return dist
 *
 * The book's `queue of int` is a list<int> here: add is enqueue and
 * removeFirst is dequeue, so the list is used strictly FIFO.
 *
 * parent is the BFS tree the book declares as a second output: parent[k]
 * is the vertex k was first reached from, and stays -1 for the source and
 * for anything unreachable. Only dist is returned, as the book returns.
 *
 * Auto-generated from BFSTraversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BFSTraversal {
  BFSTraversal(succ, s) {
    this.succ = succ;
    this.s = s;
    this.n = this.succ.length;
    this.dist = new Array(this.n).fill(0);
    this.parent = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.dist[i] = 2147483647;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.parent[i] = (-1);
    }
    let S = [];
    S.push(this.s);
    this.dist[this.s] = 0;
    while ((!(S.length === 0))) {
        let j = S.shift();
        for (const k of this.succ[j]) {
            if ((this.dist[k] > (this.dist[j] + 1))) {
                this.dist[k] = (this.dist[j] + 1);
                this.parent[k] = j;
                S.push(k);
            }
        }
    }
    return this.dist;
  }
}

if (typeof module !== 'undefined') module.exports = BFSTraversal;
