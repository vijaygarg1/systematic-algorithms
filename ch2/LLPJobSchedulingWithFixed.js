/*
 * Kahn's-style topological sweep: finalise each job once its predecessors are fixed.
 *
 * Auto-generated from LLPJobSchedulingWithFixed.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPJobSchedulingWithFixed {
  LLPJobSchedulingWithFixed(t, pre, succ) {
    this.t = t;
    this.pre = pre;
    this.succ = succ;
    this.n = this.t.length;
    let n = this.t.length;
    let G = new Array(n).fill(0);
    let count = new Array(n).fill(0);
    let candidate = new Array(n).fill(0);
    let head = 0;
    let tail = 0;
    for (let k = 0; k <= ((n - 1)); k++) {
        G[k] = this.t[k];
    }
    for (let k = 0; k <= ((n - 1)); k++) {
        count[k] = this.pre[k].length;
    }
    for (let k = 0; k <= ((n - 1)); k++) {
        if ((count[k] === 0)) {
            candidate[tail] = k;
            tail = (tail + 1);
        }
    }
    while ((head < tail)) {
        let j = candidate[head];
        head = (head + 1);
        G[j] = Math.max(G[j], (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (G[i] + this.t[j]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
        for (const k of this.succ[j]) {
            count[k] = (count[k] - 1);
            if ((count[k] === 0)) {
                candidate[tail] = k;
                tail = (tail + 1);
            }
        }
    }
    return G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPJobSchedulingWithFixed;
