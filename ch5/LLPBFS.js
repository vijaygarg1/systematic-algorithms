/*
 * LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.
 * style: fixpoint
 *
 * Auto-generated from LLPBFS.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPBFS {
  LLPBFS(pre, G) {
    this.pre = pre;
    this.G = G;
    this.n = this.pre.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((this.G[j] > (() => { let _a = 2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + 1); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })())) {
                this.G[j] = (() => { let _a = 2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + 1); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })();
                _changed = true;
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = LLPBFS;
