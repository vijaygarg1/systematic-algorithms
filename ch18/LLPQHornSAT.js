/*
 * LLP-QHornSAT: solve q-horn formula by composing HornSAT on X
 * variables with 2-SAT on Y variables.
 * This is a composite algorithm, not a single forbidden/advance pair.
 *
 * Auto-generated from LLPQHornSAT.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPQHornSAT {
  LLPQHornSAT(body1, head1, clauses2, n1, n2) {
    this.body1 = body1;
    this.head1 = head1;
    this.clauses2 = clauses2;
    this.n1 = n1;
    this.n2 = n2;
    this.n = this.body1.length;
    this.G = new Array(this.n1).fill(false);
    this.H = new Array(this.n2).fill(false);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if (this.hornImplied(j)) {
                this.G[j] = true;
                _changed = true;
            }
        }
    }
    return this.G;
  }

  hornImplied(j) {
    return ((!this.G[j]) && (() => { for (let c = 0; c <= ((this.body1.length - 1)); c++) { if (!((this.head1[c] === j))) continue; if (this.allTrue(c)) return true; } return false; })());
  }

  allTrue(c) {
    return (() => { for (let k = 0; k <= ((this.body1[c].length - 1)); k++) { if (!(this.G[this.body1[c][k]])) return false; } return true; })();
  }
}

if (typeof module !== 'undefined') module.exports = LLPQHornSAT;

if (typeof require !== 'undefined' && require.main === module) {
    const body1 = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const head1 = [3, 1, 6, 1, 6, 3, 6, 4];
    const clauses2 = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const n1 = 8;
    const n2 = 8;
    const prog = new LLPQHornSAT();
    const result = prog.LLPQHornSAT(body1, head1, clauses2, n1, n2);
    console.log(JSON.stringify(result));
}
