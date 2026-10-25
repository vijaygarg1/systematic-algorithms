/*
 * LLP-Sort-Adjacent, exactly as bxx-sort.tex's Algorithm LLP-Sort-Adjacent
 * states it:
 *
 * Forbidden (j in [1..n-1]): A[j] > A[j+1]
 * Advance                  : swap(A[j], A[j+1])   // increment G[j]
 *
 * G[j] counts the inversions at j; forbidden means G[j] misses one. The
 * book's [1..n-1] is every index that has a successor; 0-based that is
 * [0..n-2], so the range itself rules out j = n-1 and the predicate needs
 * no bound of its own.
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
        for (let j = 0; j <= ((this.n - 2)); j++) {
            if ((this.A[j] > this.A[(j + 1)])) {
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
