/*
 * LLP-CountInversions: count inversions to the left of each index.
 *
 * Auto-generated from LLPCountInversions.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPCountInversions {
  LLPCountInversions(A) {
    this.A = A;
    this.n = this.A.length;
    this.G = new Array(this.n).fill(0);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((!(this.G[j] >= this.countLeft(this.A, j)))) {
                this.G[j] = this.countLeft(this.A, j);
                _changed = true;
            }
        }
    }
  }

  countLeft(A, j) {
    let count = 0;
    for (let i = 0; i <= ((j - 1)); i++) {
        if ((A[i] > A[j])) {
            count = (count + 1);
        }
    }
    return count;
  }
}

if (typeof module !== 'undefined') module.exports = LLPCountInversions;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LLPCountInversions();
    prog.LLPCountInversions(A);
    console.log(JSON.stringify(A));
}
