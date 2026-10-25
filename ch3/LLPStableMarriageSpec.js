/*
 * LLP-StableMarriage-Spec: the specification form of Gale-Shapley, exactly
 * as bxx-stableMatching.tex's Algorithm LLP-StableMarriage-Spec states it.
 *
 * Always       : z = mpref[j][G[j]]
 * Forbidden (j): exists i in [1..n] : exists k in [1..G[i]] :
 * (z = mpref[i][k]) and (rank[z][i] < rank[z][j])
 * Advance      : G[j] := G[j] + 1
 *
 * setBase(1) and the [0..n] declarations give the book's array[1..n]:
 * every array is n+1 long and slot 0 is never used, so the indices are
 * 1..n and mpref[j][G[j]] is the book's expression with no offset.
 *
 * G[j] is the book's position in man j's preference list, counted from 1,
 * and I is its initial value -- the book's standard initialization is
 * [1, 1, ..., 1], every man on his first choice. LLPStableMarriage.llp,
 * which the book derives from this program, uses the same G, so the two
 * return the same vector on the same instance.
 *
 * The exists-exists scan is what makes this a specification rather than
 * an algorithm: an arbitrary initial G gives no way to tell from G alone
 * which woman currently holds the best proposal, so the guard re-examines
 * every man's whole proposal history. LLPStableMarriage.llp caches that
 * in partner[z] and tests it in O(1).
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
            if ((() => { for (let i = 1; i <= (this.n); i++) { if ((() => { for (let k = 1; k <= (this.G[i]); k++) { if (((z === this.mpref[i][k]) && (this.rank[z][i] < this.rank[z][j]))) return true; } return false; })()) return true; } return false; })()) {
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
