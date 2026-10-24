/*
 * Classical in-degree zero queue algorithm for DAG layering.
 *
 * Auto-generated from Layering.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Layering {
  layer(pre, succ) {
    this.pre = pre;
    this.succ = succ;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(0);
    this.indeg = new Array(this.n).fill(0);
    for (let j = 0; j <= ((this.n - 1)); j++) {
        for (const i of this.pre[j]) {
            this.indeg[j] = (this.indeg[j] + 1);
        }
    }
    let Q = new Array(this.n).fill(0);
    let head = 0;
    let tail = 0;
    for (let j = 0; j <= ((this.n - 1)); j++) {
        if ((this.indeg[j] === 0)) {
            this.G[j] = 0;
            Q[tail] = j;
            tail = (tail + 1);
        }
    }
    while ((head < tail)) {
        let j = Q[head];
        head = (head + 1);
        for (const k of this.succ[j]) {
            this.indeg[k] = (this.indeg[k] - 1);
            if ((this.indeg[k] === 0)) {
                let best = 0;
                for (const i of this.pre[k]) {
                    if (((this.G[i] + 1) > best)) {
                        best = (this.G[i] + 1);
                    }
                }
                this.G[k] = best;
                Q[tail] = k;
                tail = (tail + 1);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Layering;
