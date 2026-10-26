/*
 * LLP-Sort1 (transposition form): forbidden = inversion, advance = swap.
 *
 * Auto-generated from LLPSortAdjacent.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPSortAdjacent {
  LLPSortAdjacent(A) {
    this.A = A;
    this.n = this.A.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((j < (this.n - 1)) && (this.A[j] > this.A[(j + 1)]))) {
                const _sw1 = this.A[j]; this.A[j] = this.A[(j + 1)]; this.A[(j + 1)] = _sw1;
                _changed = true;
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = LLPSortAdjacent;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LLPSortAdjacent();
    prog.LLPSortAdjacent(A);
    console.log(JSON.stringify(A));
}
