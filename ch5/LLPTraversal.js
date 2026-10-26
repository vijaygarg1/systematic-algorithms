/*
 * Reachability as a 1-bit LLP: forbidden = unreached vertex with reached predecessor.
 *
 * Auto-generated from LLPTraversal.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPTraversal {
  LLPTraversal(pre, G) {
    this.pre = pre;
    this.G = G;
    this.n = this.pre.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((!this.G[j]) && (() => { for (const i of this.pre[j]) { if (this.G[i]) return true; } return false; })())) {
                this.G[j] = true;
                _changed = true;
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = LLPTraversal;
