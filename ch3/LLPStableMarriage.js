/*
 * LLP-StableMarriage: Gale-Shapley as a lattice-linear program, exactly
 * as bxx-stableMatching.tex's Algorithm LLP-StableMarriage states it.
 *
 * Always    (i): z = mpref[i][G[i]]
 * Forbidden (i): (G[i] = 0) or ((partner[z] != null) and
 * (rank[z][partner[z]] < rank[z][i]))
 * Advance      : G[i] := G[i] + 1;
 * if ((partner[z] = null) or (rank[z][i] < rank[z][partner[z]]))
 * then partner[z] := i
 *
 * G[i] is the book's POSITION in man i's preference list, counted from 1:
 * G[i] = 0 means he has proposed to nobody, G[i] = k that he is standing
 * on his k-th choice. LLPStableMarriageSpec.llp, which the book derives
 * this program from, uses the same G, so the two return the same vector.
 *
 * Two places where the book's 1-indexed arrays do not carry over
 * verbatim, and nothing else differs:
 *
 * The book's mpref is array[1..n][1..n], so its z = mpref[i][G[i]] is
 * the woman i is standing on, and G[i] = 0 harmlessly reads the unused
 * slot 0. Here mpref is 0-indexed, so the same woman is at G[i]-1 and
 * G[i] = 0 would index -1 -- hence max(G[i], 1) - 1, which is slot 0
 * when he has not yet proposed. The guard's first disjunct makes him
 * forbidden in that case regardless of what z holds.
 *
 * The book's Always is a definition, re-read wherever it appears, so
 * the advance's z is the NEXT choice -- read after G[i] is
 * incremented. LL's `always` binds once on entry to each of forbidden
 * and advance, so the advance re-reads it explicitly.
 *
 * partner[z] (-1 for null) is the best man who has proposed to z so far:
 * redundant state, recoverable from G in O(n), kept only so the guard is
 * O(1) rather than the Spec's rescan of every man's proposal history.
 *
 * Auto-generated from LLPStableMarriage.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPStableMarriage {
  LLPStableMarriage(mpref, rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = this.mpref.length;
    this.G = new Array(this.n).fill(0);
    this.partner = new Array(this.n).fill(0);
    for (let w = 0; w <= ((this.n - 1)); w++) {
        this.partner[w] = (-1);
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let i = 0; i <= (this.n - 1); i++) {
            let z = this.mpref[i][(Math.max(this.G[i], 1) - 1)];
            if (((this.G[i] === 0) || ((this.partner[z] !== (-1)) && (this.rank[z][this.partner[z]] < this.rank[z][i])))) {
                this.G[i] = (this.G[i] + 1);
                z = this.mpref[i][(this.G[i] - 1)];
                if (((this.partner[z] === (-1)) || (this.rank[z][i] < this.rank[z][this.partner[z]]))) {
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
    const mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new LLPStableMarriage();
    const result = prog.LLPStableMarriage(mpref, rank);
    console.log(JSON.stringify(result));
}
