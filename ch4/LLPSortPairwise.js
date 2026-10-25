/*
 * LLP-Sort-Pairwise, exactly as bxx-sort.tex's Algorithm LLP-Sort-Pairwise
 * states it:
 *
 * Forbidden (j in [1..n-1]): exists k > j: A[j] > A[k]   // pick such k
 * Advance                  : swap(A[j], A[k])
 *
 * The book writes the witness as "exists k > j" and picks it in a comment;
 * LL binds it with `pick k`, and bounds it by the array, so the book's
 * k > j is [j+1..n-1] here. The book's [1..n-1] is [0..n-2] 0-based.
 *
 * Auto-generated from LLPSortPairwise.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPSortPairwise {
  LLPSortPairwise(A) {
    this.A = A;
    this.n = this.A.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 2)); j++) {
            if (this._pick_k(j)) {
                const _sw1 = this.A[j]; this.A[j] = this.A[this.k]; this.A[this.k] = _sw1;
                _changed = true;
            }
        }
    }
  }

  _pick_k(j) {
    for (let k = (j + 1); k <= ((this.n - 1)); k++) {
      if ((this.A[j] > this.A[k])) { this.k = k; return true; }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPSortPairwise;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LLPSortPairwise();
    prog.LLPSortPairwise(A);
    console.log(JSON.stringify(A));
}
