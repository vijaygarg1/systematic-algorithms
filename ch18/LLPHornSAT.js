/*
 * LLP Horn SAT, matching bxx-horn.tex Algorithm LLP-HornSAT. head[c] is
 * the consequent variable of clause c, or -1 if clause c is a pure
 * negative (goal) clause. The book's two rules are written here as two
 * forbidden/advance clauses joined by &&, so they share one fixpoint.
 *
 * Auto-generated from LLPHornSAT.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPHornSAT {
  LLPHornSAT(body, head, n) {
    this.body = body;
    this.head = head;
    this.n = n;
    this.G = new Array(this.n).fill(false);
    this.hcount = new Array(this.n).fill(0);
    this.byHead = new Array(this.n).fill(null);
    this.hfill = new Array(this.n).fill(0);
    for (let c = 0; c <= ((this.body.length - 1)); c++) {
        if ((this.head[c] >= 0)) {
            this.hcount[this.head[c]] = (this.hcount[this.head[c]] + 1);
        }
    }
    for (let v = 0; v <= ((this.n - 1)); v++) {
        this.byHead[v] = new Array(this.hcount[v]).fill(0);
    }
    for (let c = 0; c <= ((this.body.length - 1)); c++) {
        if ((this.head[c] >= 0)) {
            this.byHead[this.head[c]][this.hfill[this.head[c]]] = c;
            this.hfill[this.head[c]] = (this.hfill[this.head[c]] + 1);
        }
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if (this.definiteImplied(j)) {
                this.G[j] = true;
                _changed = true;
            }
        }
    }
    if (this.goalFired(this.body, this.head)) {
        return null;
    }
    return this.G;
  }

  definiteImplied(j) {
    return ((!this.G[j]) && (() => { for (let t = 0; t <= ((this.byHead[j].length - 1)); t++) { if (this.allTrue(this.byHead[j][t])) return true; } return false; })());
  }

  goalFired(body, head) {
    return (() => { for (let c = 0; c <= ((body.length - 1)); c++) { if (((head[c] === (0 - 1)) && this.allTrue(c))) return true; } return false; })();
  }

  allTrue(c) {
    return (() => { for (let k = 0; k <= ((this.body[c].length - 1)); k++) { if (!(this.G[this.body[c][k]])) return false; } return true; })();
  }
}

if (typeof module !== 'undefined') module.exports = LLPHornSAT;

if (typeof require !== 'undefined' && require.main === module) {
    const body = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const head = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LLPHornSAT();
    const result = prog.LLPHornSAT(body, head, head.length);
    console.log(JSON.stringify(result));
}
