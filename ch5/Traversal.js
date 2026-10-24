/*
 * Generic queue-based reachability from vertex 0.
 *
 * Auto-generated from Traversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Traversal {
  reach(succ) {
    this.succ = succ;
    this.n = this.succ.length;
    this.G = new Array(this.n).fill(0);
    this.Q = new Array(this.n).fill(0);
    this.head = 0;
    this.tail = 0;
    this.G[0] = 1;
    this.Q[this.tail] = 0;
    this.tail = (this.tail + 1);
    while ((this.head < this.tail)) {
        let j = this.Q[this.head];
        this.head = (this.head + 1);
        for (const k of this.succ[j]) {
            if ((this.G[k] === 0)) {
                this.G[k] = 1;
                this.Q[this.tail] = k;
                this.tail = (this.tail + 1);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Traversal;
