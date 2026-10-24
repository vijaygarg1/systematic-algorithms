/*
 * LLP-Antichain: maximum antichain by advancing chain indices to dominate-free positions.
 *
 * Auto-generated from LLPAntichain.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPAntichain {
  LLPAntichain(chains, len_, leq) {
    this.chains = chains;
    this.len_ = len_;
    this.leq = leq;
    this.n = this.chains.length;
    this.G = new Array(this.n).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((this.G[j] < this.len_[j]) && (() => { for (let k = 0; k <= ((this.n - 1)); k++) { if (((k !== j) && this.leq[this.chains[j][this.G[j]]][this.chains[k][this.G[k]]])) return true; } return false; })())) {
                this.G[j] = (this.G[j] + 1);
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPAntichain;

if (typeof require !== 'undefined' && require.main === module) {
    const chains = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const len_ = [3, 1, 6, 1, 6, 3, 6, 4];
    const leq = [[true, false, false, false, false, false, false, false], [false, true, false, false, false, false, false, false], [false, false, true, false, false, false, false, false], [false, false, false, true, false, false, false, false], [false, false, false, false, true, false, false, false], [false, false, false, false, false, true, false, false], [false, false, false, false, false, false, true, false], [false, false, false, false, false, false, false, true]];
    const prog = new LLPAntichain();
    const result = prog.LLPAntichain(chains, len_, leq);
    console.log(JSON.stringify(result));
}
