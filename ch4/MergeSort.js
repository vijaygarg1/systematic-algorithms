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
    this.n = this.B.length;
    let m = this.B.length;
    let n = this.C.length;
    let D = new Array((m + n)).fill(0);
    let i = 0;
    let j = 0;
    let k = 0;
    while (((i < m) && (j < n))) {
        if ((this.B[i] <= this.C[j])) {
            D[k] = this.B[i];
            i = (i + 1);
        } else {
            D[k] = this.C[j];
            j = (j + 1);
        }
        k = (k + 1);
    }
    while ((i < m)) {
        D[k] = this.B[i];
        i = (i + 1);
        k = (k + 1);
    }
    while ((j < n)) {
        D[k] = this.C[j];
        j = (j + 1);
        k = (k + 1);
    }
    return D;
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
