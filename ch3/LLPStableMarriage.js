/*
 * LLP-StableMarriage: Gale-Shapley as a lattice-linear program, written
 * exactly as bxx-stableMatching.tex's Algorithm LLP-StableMarriage:
 *
 * Auto-generated from LLPStableMarriage.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPStableMarriage {
  LLPStableMarriage(mpref, rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = this.mpref.length - 1;
    this.G = new Array(this.n + 1).fill(0);
    this.partner = new Array(this.n + 1).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let i = 1; i <= (this.n); i++) {
            let z = this.mpref[i][this.G[i]];
            if (((this.G[i] === 0) || ((this.partner[z] !== 0) && (this.rank[z][this.partner[z]] < this.rank[z][i])))) {
                this.G[i] = (this.G[i] + 1);
                z = this.mpref[i][this.G[i]];
                if (((this.partner[z] === 0) || (this.rank[z][i] < this.rank[z][this.partner[z]]))) {
                    this.partner[z] = i;
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPStableMarriage;

if (typeof require !== 'undefined' && require.main === module) {
    const mpref = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 2, 3, 4, 5, 6, 7], [0, 2, 3, 4, 5, 6, 7, 1], [0, 3, 4, 5, 6, 7, 1, 2], [0, 4, 5, 6, 7, 1, 2, 3], [0, 5, 6, 7, 1, 2, 3, 4], [0, 6, 7, 1, 2, 3, 4, 5], [0, 7, 1, 2, 3, 4, 5, 6]];
    const rank = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 7, 6, 5, 4, 3, 2], [0, 2, 1, 7, 6, 5, 4, 3], [0, 3, 2, 1, 7, 6, 5, 4], [0, 4, 3, 2, 1, 7, 6, 5], [0, 5, 4, 3, 2, 1, 7, 6], [0, 6, 5, 4, 3, 2, 1, 7], [0, 7, 6, 5, 4, 3, 2, 1]];
    const prog = new LLPStableMarriage();
    const result = prog.LLPStableMarriage(mpref, rank);
    console.log(JSON.stringify(result));
}
