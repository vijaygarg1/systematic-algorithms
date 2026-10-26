/*
 * LLP-Assignment (bxx-assignment.tex, algo:llp-assignment): forbidden when
 * item j belongs to an inclusion-minimal overdemanded set J -- found by
 * alternating-path reachability from an unmatched bidder, the standard
 * witness for a violated Hall condition. Advance raises EVERY item in J by
 * one shared slack jump, delta := min over bidders demanding into J of
 *
 * Auto-generated from LLPAssignment.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPAssignment {
  LLPAssignment(v) {
    this.v = v;
    this.n = this.v[0].length;
    this.m = this.v.length;
    this.G = new Array(this.n).fill(0);
    this.partner = new Array(this.n).fill(0);
    this.itemInJ = new Array(this.n).fill(false);
    this.bidderInB = new Array(this.m).fill(false);
    this.fresh = false;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this.overdemanded(j)) {
                this.raiseJ();
                _changed = true;
            }
        }
    }
    return this.G;
  }

  overdemanded(j) {
    this.refresh();
    return this.itemInJ[j];
  }

  refresh() {
    if (this.fresh) {
        return;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.itemInJ[i] = false;
        this.partner[i] = (0 - 1);
    }
    for (let b = 0; b <= ((this.m - 1)); b++) {
        this.bidderInB[b] = false;
    }
    for (let b = 0; b <= ((this.m - 1)); b++) {
        this.tryMatch(b, new Array(this.n).fill(false));
    }
    let u = this.firstUnmatched();
    if ((u < this.m)) {
        this.reach(u);
    }
    this.fresh = true;
  }

  raiseJ() {
    let delta = 2147483647;
    for (let b = 0; b <= ((this.m - 1)); b++) {
        if (this.bidderInB[b]) {
            let drop = (this.bestSurplus(b) - this.bestOutside(b));
            if ((drop < delta)) {
                delta = drop;
            }
        }
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        if (this.itemInJ[i]) {
            this.G[i] = (this.G[i] + delta);
        }
    }
    this.fresh = false;
  }

  firstUnmatched() {
    for (let b = 0; b <= ((this.m - 1)); b++) {
        if ((!(() => { for (let i = 0; i <= ((this.n - 1)); i++) { if ((this.partner[i] === b)) return true; } return false; })())) {
            return b;
        }
    }
    return this.m;
  }

  bestSurplus(b) {
    return (() => { let _a = -2147483647, _s = false; for (let i = 0; i <= ((this.n - 1)); i++) { const _v = (this.v[b][i] - this.G[i]); if (!_s || _v > _a) { _a = _v; _s = true; } } return _a; })();
  }

  bestOutside(b) {
    let best = (0 - 2147483647);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        if (((!this.itemInJ[i]) && ((this.v[b][i] - this.G[i]) > best))) {
            best = (this.v[b][i] - this.G[i]);
        }
    }
    return best;
  }

  reach(b) {
    if (this.bidderInB[b]) {
        return;
    }
    this.bidderInB[b] = true;
    let best = this.bestSurplus(b);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        if ((((this.v[b][i] - this.G[i]) === best) && (!this.itemInJ[i]))) {
            this.itemInJ[i] = true;
            if ((this.partner[i] !== (0 - 1))) {
                this.reach(this.partner[i]);
            }
        }
    }
  }

  tryMatch(b, seen) {
    let best = this.bestSurplus(b);
    let i = 0;
    while ((i < this.n)) {
        if ((((this.v[b][i] - this.G[i]) === best) && (!seen[i]))) {
            seen[i] = true;
            if (((this.partner[i] === (0 - 1)) || this.tryMatch(this.partner[i], seen))) {
                this.partner[i] = b;
                return true;
            }
        }
        i = (i + 1);
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPAssignment;

if (typeof require !== 'undefined' && require.main === module) {
    const v = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new LLPAssignment();
    const result = prog.LLPAssignment(v);
    console.log(JSON.stringify(result));
}
