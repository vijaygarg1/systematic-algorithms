/*
 * LLP-Sort2: forbidden when some k > j has A[j] > A[k]; swap them.
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
        for (let j = 0; j <= (this.n - 1); j++) {
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
