/*
 * Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.
 *
 * Follows Algorithm ApproxSetCover in bxx-approximate.tex, including its
 * stated precondition: the instance is assumed feasible, i.e. every
 * element of the universe lies in some set, so that a cover exists. The
 * loop guard is the book's `R != empty`, not a weaker "some set still
 * covers something new" test -- on an infeasible instance it would not
 * terminate, exactly as the book's algorithm would not.
 *
 * The uncovered counts cnt[s] = |s ∩ R| are maintained instead of being
 * recomputed each round: covering an element decrements the count of every
 * set containing it, so the total number of updates over the whole run is
 * one per (set, element) incidence. Recomputing all m counts in each of the
 * up-to min(m,n) rounds costs a factor min(m,n) more.
 *
 * The instance is a pair of incidence lists: mem[s] holds the elements of
 * set s and inc[e] holds the sets containing element e. Neither is an
 * m x u matrix, so the whole instance is O(N) -- N being the number of
 * (set, element) incidences -- rather than Theta(m*u). Every loop below
 * walks one of these lists, so each pass costs O(N) and not O(m*u).
 *
 * Taking them as input rather than building them from a matrix is what
 * makes that real: an m x u matrix had to be read once to build the lists,
 * which is Theta(m*u) on its own and reached a gigabyte at n = 16000.
 *
 * Auto-generated from ApproxSetCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ApproxSetCover {
  ApproxSetCover(mem, inc) {
    this.mem = mem;
    this.inc = inc;
    this.m = this.mem.length;
    this.n = this.inc.length;
    this.C = new Array(this.m).fill(false);
    this.covered = new Array(this.n).fill(false);
    this.cnt = new Array(this.m).fill(0);
    this.remaining = this.n;
    for (let s = 0; s <= ((this.m - 1)); s++) {
        this.cnt[s] = this.mem[s].length;
    }
    while ((this.remaining > 0)) {
        let bestIdx = 0;
        let bestCover = (0 - 1);
        for (let s = 0; s <= ((this.m - 1)); s++) {
            if ((this.cnt[s] > bestCover)) {
                bestCover = this.cnt[s];
                bestIdx = s;
            }
        }
        this.C[bestIdx] = true;
        for (let k = 0; k <= ((this.mem[bestIdx].length - 1)); k++) {
            let e = this.mem[bestIdx][k];
            if ((!this.covered[e])) {
                this.covered[e] = true;
                this.remaining = (this.remaining - 1);
                let t = 0;
                while ((t < this.inc[e].length)) {
                    this.cnt[this.inc[e][t]] = (this.cnt[this.inc[e][t]] - 1);
                    t = (t + 1);
                }
            }
        }
    }
    return this.C;
  }
}

if (typeof module !== 'undefined') module.exports = ApproxSetCover;

if (typeof require !== 'undefined' && require.main === module) {
    const mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new ApproxSetCover();
    const result = prog.ApproxSetCover(mem, inc);
    console.log(JSON.stringify(result));
}
