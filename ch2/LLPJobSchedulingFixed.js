/*
 * LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.
 *
 * Auto-generated from LLPJobSchedulingFixed.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPJobSchedulingFixed {
  LLPJobSchedulingFixed(t, pre) {
    this.t = t;
    this.pre = pre;
    this.n = this.t.length;
    this.G = this.t.slice();
    this.fixed = new Array(this.n).fill(false);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((!this.fixed[j]) && (() => { for (const i of this.pre[j]) { if (!(this.fixed[i])) return false; } return true; })())) {
                this.G[j] = Math.max(this.t[j], (() => { let _a = -2147483647, _s = false; for (const i of this.pre[j]) { const _v = (this.G[i] + this.t[j]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })());
                this.fixed[j] = true;
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPJobSchedulingFixed;
