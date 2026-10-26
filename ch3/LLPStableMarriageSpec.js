/*
 * LLP-StableMarriage-Spec: specification form of Gale-Shapley (bxx-stableMatching.tex).
 * G[j] = man j's current proposal index, 1 = top choice; I is its initial value, 1<=I[i]<=n.
 * Guard restored 2026-10-26: without it a bad I runs G[j] to n+1 and reads past mpref's row.
 *
 * Auto-generated from LLPStableMarriageSpec.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPStableMarriageSpec {
  LLPStableMarriageSpec(mpref, rank, I) {
    this.mpref = mpref;
    this.rank = rank;
    this.I = I;
    this.n = this.mpref.length - 1;
    this.G = this.I.slice();
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 1; j <= (this.n); j++) {
            let z = this.mpref[j][this.G[j]];
            if ((() => { for (let i = 1; i <= (this.n); i++) { if ((() => { for (let k = 1; k <= (this.G[i]); k++) { if (((this.mpref[i][k] === z) && (this.rank[z][i] < this.rank[z][j]))) return true; } return false; })()) return true; } return false; })()) {
                if ((this.G[j] === this.n)) {
                    return null;
                }
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
    const mpref = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 2, 3, 4, 5, 6, 7], [0, 2, 3, 4, 5, 6, 7, 1], [0, 3, 4, 5, 6, 7, 1, 2], [0, 4, 5, 6, 7, 1, 2, 3], [0, 5, 6, 7, 1, 2, 3, 4], [0, 6, 7, 1, 2, 3, 4, 5], [0, 7, 1, 2, 3, 4, 5, 6]];
    const rank = [[0, 0, 0, 0, 0, 0, 0, 0], [0, 1, 7, 6, 5, 4, 3, 2], [0, 2, 1, 7, 6, 5, 4, 3], [0, 3, 2, 1, 7, 6, 5, 4], [0, 4, 3, 2, 1, 7, 6, 5], [0, 5, 4, 3, 2, 1, 7, 6], [0, 6, 5, 4, 3, 2, 1, 7], [0, 7, 6, 5, 4, 3, 2, 1]];
    const I = [0, 1, 1, 1, 1, 1, 1, 1];
    const prog = new LLPStableMarriageSpec();
    const result = prog.LLPStableMarriageSpec(mpref, rank, I);
    console.log(JSON.stringify(result));
}
