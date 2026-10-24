/*
 * LLP-WeightedSetCover: primal-dual f-approximation for weighted set
 * cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
 * (fig:llp-wsc). G[x] is the price on element x; a set s is tight when
 * its incident prices saturate w(s); an element is slack when it lies
 * in no tight set; slackMin(x) further requires x to be lex-minimal
 * among every slack element sharing a set with it. Advancing raises
 * x's price by the smallest remaining budget among the sets containing
 * it, tightening at least one of them.
 *
 * The guard names its own range, x in [0..u-1], because x ranges over
 * ELEMENTS while the bound the compiler would otherwise infer comes from
 * the first 1D array parameter (w.length, the number of SETS) -- a
 * different count whenever sets and elements aren't equinumerous.
 *
 * S, w and G are program fields, so the aux methods read them directly
 * rather than taking them as parameters.
 *
 * Three cached quantities keep the predicate off the critical path. The set
 * prices sp[] are maintained by the advance, so setTight is O(1) instead of
 * an O(u) sum; slack[] records which elements lie in no tight set; and
 * minSlackIn[s] is the smallest slack element of s, which turns "no slack
 * element below x shares a set with x" into a scan of the sets containing x
 * rather than a scan over all smaller elements, each re-deciding slackness.
 * All three are rebuilt only after an advance has moved a price.
 *
 * PRECONDITION: every element must lie in at least one set, or no cover
 * exists at all. The guard enforces it rather than assuming it, because
 * an uncoverable element is slack forever -- it lies in no tight set, so
 * nothing can ever make it non-slack -- and the fixpoint marks a sweep
 * as progress whenever a rule fires, not when G actually moves. Without
 * the covered(x) conjunct below, one such element makes this program
 * raise its price forever and never terminate. With it, an uncoverable
 * element simply keeps price 0 and the sets returned cover everything
 * that can be covered.
 *
 * Auto-generated from LLPWeightedSetCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPWeightedSetCover {
  LLPWeightedSetCover(mem, inc, w) {
    this.mem = mem;
    this.inc = inc;
    this.w = w;
    this.n = this.mem.length;
    this.m = this.w.length;
    this.u = this.inc.length;
    this.G = new Array(this.u).fill(0.0);
    this.sp = new Array(this.m).fill(0.0);
    this.slack = new Array(this.u).fill(false);
    this.minSlackIn = new Array(this.m).fill(0);
    this.fresh = false;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let x = 0; x <= ((this.u - 1)); x++) {
            if ((this.covered(x) && this.slackMin(x))) {
                let step = this.minRemainingBudget(x);
                this.G[x] = (this.G[x] + step);
                let t = 0;
                while ((t < this.inc[x].length)) {
                    this.sp[this.inc[x][t]] = (this.sp[this.inc[x][t]] + step);
                    t = (t + 1);
                }
                this.fresh = false;
                _changed = true;
            }
        }
    }
    let C = new Array(this.m).fill(false);
    for (let s = 0; s <= ((this.m - 1)); s++) {
        C[s] = this.setTight(s);
    }
    return C;
  }

  covered(x) {
    return (this.inc[x].length > 0);
  }

  setTight(s) {
    return (this.sp[s] >= this.w[s]);
  }

  refresh() {
    if ((!this.fresh)) {
        for (let e = 0; e <= ((this.u - 1)); e++) {
            this.slack[e] = this.isSlack(e);
        }
        for (let s = 0; s <= ((this.m - 1)); s++) {
            this.minSlackIn[s] = this.firstSlackOf(s);
        }
        this.fresh = true;
    }
  }

  firstSlackOf(s) {
    let k = 0;
    while ((k < this.mem[s].length)) {
        if (this.slack[this.mem[s][k]]) {
            return this.mem[s][k];
        }
        k = (k + 1);
    }
    return this.u;
  }

  isSlack(e) {
    return (!(() => { for (const s of this.inc[e]) { if (this.setTight(s)) return true; } return false; })());
  }

  minRemainingBudget(e) {
    let best = Infinity;
    for (const s of this.inc[e]) {
        let budget = (this.w[s] - this.sp[s]);
        if ((budget < best)) {
            best = budget;
        }
    }
    return best;
  }

  slackMin(x) {
    this.refresh();
    if ((!this.slack[x])) {
        return false;
    }
    let k = 0;
    while ((k < this.inc[x].length)) {
        if ((this.minSlackIn[this.inc[x][k]] < x)) {
            return false;
        }
        k = (k + 1);
    }
    return true;
  }
}

if (typeof module !== 'undefined') module.exports = LLPWeightedSetCover;

if (typeof require !== 'undefined' && require.main === module) {
    const mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const w = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const prog = new LLPWeightedSetCover();
    const result = prog.LLPWeightedSetCover(mem, inc, w);
    console.log(JSON.stringify(result));
}
