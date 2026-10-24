/*
 * LLP parallel H_n-approximation for Set Cover: pick every set that
 * maximises coverage among its neighbours and is lex-minimal among ties.
 * A neighbour of j is another unpicked set sharing an uncovered element
 * with j; j is forbidden (i.e. should be picked) when it still covers
 * something and no neighbour outranks it.
 *
 * The predicate is tested without comparing set pairs. For each uncovered
 * element keep best[e], the unpicked set covering the most uncovered
 * elements that contains e (lowest index on ties); then j outranks every
 * neighbour exactly when best[e] = j for every uncovered e in s_j, since
 * the neighbours of j are precisely the unpicked sets sharing such an e.
 * The coverage counts cnt[j] = |s_j ∩ R| and the covered[] flags are
 * maintained by the advance, so coverage is read in O(1) instead of being
 * recomputed -- it used to cost an O(m) scan per element, inside an O(u)
 * scan per set, inside an O(m) scan over neighbours.
 *
 * The instance is a pair of incidence lists: mem[s] holds the elements of
 * set s and inc[e] holds the sets containing element e. Neither is an
 * m x u matrix, so the whole instance is O(N) -- N being the number of
 * (set, element) incidences -- rather than Theta(m*u). Every pass below
 * walks one of these lists, so each costs O(N) and not O(m*u).
 *
 * That matters most for best[], which is rebuilt after every advance, and
 * there are as many advances as sets picked: rebuilding it by scanning a
 * whole matrix each time made the algorithm O(m*u) per advance, a full
 * factor of n more than the chapter's O(N*min(m,n)) bound.
 *
 * Auto-generated from LLPLexGreedySetCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPLexGreedySetCover {
  LLPLexGreedySetCover(mem, inc) {
    this.mem = mem;
    this.inc = inc;
    this.n = this.mem.length;
    this.m = this.mem.length;
    this.u = this.inc.length;
    this.G = new Array(this.n).fill(false);
    this.covered = new Array(this.u).fill(false);
    this.cnt = new Array(this.m).fill(0);
    this.best = new Array(this.u).fill(0);
    this.fresh = false;
    for (let s = 0; s <= ((this.m - 1)); s++) {
        this.cnt[s] = this.mem[s].length;
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this.isLexMaxCov(j)) {
                this.G[j] = true;
                let k = 0;
                while ((k < this.mem[j].length)) {
                    let e = this.mem[j][k];
                    if ((!this.covered[e])) {
                        this.covered[e] = true;
                        let t = 0;
                        while ((t < this.inc[e].length)) {
                            this.cnt[this.inc[e][t]] = (this.cnt[this.inc[e][t]] - 1);
                            t = (t + 1);
                        }
                    }
                    k = (k + 1);
                }
                this.fresh = false;
                _changed = true;
            }
        }
    }
    return this.G;
  }

  isLexMaxCov(j) {
    if (this.G[j]) {
        return false;
    }
    this.refresh();
    if ((this.cnt[j] <= 0)) {
        return false;
    }
    let k = 0;
    while ((k < this.mem[j].length)) {
        let e = this.mem[j][k];
        if (((!this.covered[e]) && (this.best[e] !== j))) {
            return false;
        }
        k = (k + 1);
    }
    return true;
  }

  refresh() {
    if ((!this.fresh)) {
        for (let e = 0; e <= ((this.u - 1)); e++) {
            this.best[e] = this.bestAt(e);
        }
        this.fresh = true;
    }
  }

  bestAt(e) {
    let win = (0 - 1);
    let k = 0;
    while ((k < this.inc[e].length)) {
        let s = this.inc[e][k];
        if ((!this.G[s])) {
            if ((win < 0)) {
                win = s;
            } else {
                if ((this.cnt[s] > this.cnt[win])) {
                    win = s;
                }
            }
        }
        k = (k + 1);
    }
    return win;
  }
}

if (typeof module !== 'undefined') module.exports = LLPLexGreedySetCover;

if (typeof require !== 'undefined' && require.main === module) {
    const mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new LLPLexGreedySetCover();
    const result = prog.LLPLexGreedySetCover(mem, inc);
    console.log(JSON.stringify(result));
}
