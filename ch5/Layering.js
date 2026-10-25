/*
 * Layering, as bxx-graph.tex's Algorithm Layering states it:
 *
 * var Q: queue of int                       // initially empty
 * indeg[j] := |pre(j)| for all j
 * forall j in V: if indeg[j] = 0: G[j] := 0; Q.enqueue(j)
 * while not Q.empty():
 * j := Q.dequeue()
 * forall k in succ(j):
 * indeg[k] := indeg[k] - 1
 * if indeg[k] = 0: G[k] := 1 + max_{i in pre(k)} G[i]; Q.enqueue(k)
 * return G
 *
 * Kahn's algorithm: a vertex gets its layer once every predecessor has
 * one. The book's `queue of int` is a list<int> -- add is enqueue,
 * removeFirst is dequeue.
 *
 * Auto-generated from Layering.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Layering {
  Layering(pre, succ) {
    this.pre = pre;
    this.succ = succ;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    this.indeg = new Array(this.n).fill(0);
    for (let j = 0; j <= ((this.n - 1)); j++) {
        this.indeg[j] = this.pre[j].length;
    }
    let Q = [];
    for (let j = 0; j <= ((this.n - 1)); j++) {
        if ((this.indeg[j] === 0)) {
            this.G[j] = 0;
            Q.push(j);
        }
    }
    while ((!(Q.length === 0))) {
        let j = Q.shift();
        for (const k of this.succ[j]) {
            this.indeg[k] = (this.indeg[k] - 1);
            if ((this.indeg[k] === 0)) {
                this.G[k] = (1 + (() => { let _a = -2147483647, _s = false; for (const i of this.pre[k]) { const _v = this.G[i]; if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
                Q.push(k);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Layering;
