/*
 * LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.
 * style: fixpoint
 *
 * Auto-generated from LLPBFS.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPBFS {
  LLPBFS(pre) {
    this.pre = pre;
    this.n = this.pre.length;
    this.G = new Array(this.n).fill(2147483647);
    this.G[0] = 0;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((this.G[j] > (() => { let _a = 2147483647, _s = false; for (const i of this.pre[j]) { if (!((this.G[i] < 2147483647))) continue; const _v = (this.G[i] + 1); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })())) {
                this.G[j] = (() => { let _a = 2147483647, _s = false; for (const i of this.pre[j]) { if (!((this.G[i] < 2147483647))) continue; const _v = (this.G[i] + 1); if (!_s || _v < _a) { _a = _v; _s = true; } } return _a; })();
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPBFS;
