/*
 * Fast connected components: SlowComponents accelerated by a
 * pointer-jumping clause G[j] >= G[G[j]], which doubles the effective
 * reach of the label-propagation step at each round and brings the
 * number of rounds down from O(diameter) to O(log diameter).
 *
 * Auto-generated from FastComponents.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class FastComponents {
  FastComponents(adj) {
    this.adj = adj;
    this.n = this.adj.length;
    this.G = new Array(this.n).fill(0);
    for (let _ix = 0; _ix <= (this.n - 1); _ix++) {
        this.G[_ix] = _ix;
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((!(this.G[j] >= this.G[this.G[j]]))) {
                this.G[j] = this.G[this.G[j]];
                _changed = true;
            }
        }
    }
    let _changed1 = true;
    while (_changed1) {
        _changed1 = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((!(this.G[j] >= (() => { let _a = -2147483647, _s = false; for (const i of this.adj[j]) { const _v = this.G[i]; if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })()))) {
                this.G[j] = (() => { let _a = -2147483647, _s = false; for (const i of this.adj[j]) { const _v = this.G[i]; if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
                _changed1 = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = FastComponents;
