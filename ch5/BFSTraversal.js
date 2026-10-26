/*
 * Sequential BFS distance from a source vertex (FIFO queue).
 *
 * Auto-generated from BFSTraversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BFSTraversal {
  traverse(succ, s) {
    this.succ = succ;
    this.s = s;
    this.n = this.succ.length;
    this.G = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i] = 2147483647;
    }
    this.G[this.s] = 0;
    let Q = new Array(this.n).fill(0);
    let head = 0;
    let tail = 0;
    Q[tail] = this.s;
    tail = (tail + 1);
    while ((head < tail)) {
        let j = Q[head];
        head = (head + 1);
        for (const k of this.succ[j]) {
            if ((this.G[k] > (this.G[j] + 1))) {
                this.G[k] = (this.G[j] + 1);
                Q[tail] = k;
                tail = (tail + 1);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = BFSTraversal;
