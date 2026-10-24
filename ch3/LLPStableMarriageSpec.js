/*
 * LLP-StableMarriage-Spec: a specification of Gale-Shapley, not yet an
 * efficient algorithm (bxx-stableMatching.tex, Algorithm
 * LLP-StableMarriage-Spec). Its forbidden condition rescans every man's
 * whole proposal history to find out who currently holds a given
 * woman -- O(n) per guard, O(n^4) total -- because an arbitrary initial
 * G gives no cheaper way to tell. It exists only as a correctness
 * stepping stone toward LLPStableMarriage.llp, which caches that
 * information instead; do not measure or recommend this version on its
 * own (see v3performance.txt).
 *
 * Auto-generated from LLPStableMarriageSpec.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPStableMarriageSpec {
  LLPStableMarriageSpec(mpref, rank, I) {
    this.mpref = mpref;
    this.rank = rank;
    this.I = I;
    this.n = this.mpref.length;
    this.G = this.I.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((() => { for (let i = 0; i <= ((this.n - 1)); i++) { if ((() => { for (let k = 0; k <= (this.G[i]); k++) { if (((this.mpref[j][this.G[j]] === this.mpref[i][k]) && (this.rank[this.mpref[j][this.G[j]]][i] < this.rank[this.mpref[j][this.G[j]]][j]))) return true; } return false; })()) return true; } return false; })()) {
                this.G[j] = (this.G[j] + 1);
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPStableMarriageSpec;

if (typeof require !== 'undefined' && require.main === module) {
    const mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const I = [0, 0, 0, 0, 0, 0, 0, 0];
    const prog = new LLPStableMarriageSpec();
    const result = prog.LLPStableMarriageSpec(mpref, rank, I);
    console.log(JSON.stringify(result));
}
