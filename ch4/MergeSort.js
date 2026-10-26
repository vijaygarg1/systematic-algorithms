/*
 * Sequential merge sort, in the book's own shape: each call RETURNS a
 * newly sorted array rather than sorting a range of G in place, and the
 * merge step is the sibling MergeTwo program -- `MergeTwo(B, C)`,
 * compiling to `new MergeTwo().merge(B, C)` -- exactly as Algorithm
 * MergeSort in bxx-sort.tex writes `Return MergeTwo(B, C)`. That makes
 *
 * Auto-generated from MergeSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MergeTwo {
  merge(B, C) {
    this.B = B;
    this.C = C;
    this.m = this.B.length;
    this.n = this.C.length;
    this.D = new Array((this.m + this.n)).fill(0);
    this.i = 0;
    this.j = 0;
    this.k = 0;
    while (((this.i < this.m) && (this.j < this.n))) {
        if ((this.B[this.i] <= this.C[this.j])) {
            this.D[this.k] = this.B[this.i];
            this.i = (this.i + 1);
        } else {
            this.D[this.k] = this.C[this.j];
            this.j = (this.j + 1);
        }
        this.k = (this.k + 1);
    }
    while ((this.i < this.m)) {
        this.D[this.k] = this.B[this.i];
        this.i = (this.i + 1);
        this.k = (this.k + 1);
    }
    while ((this.j < this.n)) {
        this.D[this.k] = this.C[this.j];
        this.j = (this.j + 1);
        this.k = (this.k + 1);
    }
    return this.D;
  }
}

class MergeSort {
  sort(A, lo, hi) {
    let n = A.length;
    if ((lo < hi)) {
        let mid = Math.trunc((lo + hi) / 2);
        let B = this.sort(A, lo, mid);
        let C = this.sort(A, (mid + 1), hi);
        return new MergeTwo().merge(B, C);
    }
    let single = new Array(1).fill(0);
    single[0] = A[lo];
    return single;
  }
}

if (typeof module !== 'undefined') module.exports = MergeSort;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new MergeSort();
    const result = prog.sort(A, 0, A.length - 1);
    console.log(JSON.stringify(result));
}
