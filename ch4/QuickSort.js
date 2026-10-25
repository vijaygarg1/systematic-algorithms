/*
 * QuickSort, exactly as bxx-sort.tex's Algorithm QuickSort states it:
 *
 * if (low < high) {
 * pivot  := choosePivot()
 * (p, q) := Partition(A, pivot, low, high)
 * QuickSort(A, low, p)
 * QuickSort(A, q, high)
 * }
 *
 * Partition is the sibling ThreeWayPartition program, which returns the
 * pair (p, q) the book names: A[low..p) < pivot, A[p..q) = pivot, and
 * A[q..high) > pivot. LL has no tuple, so the pair comes back as a
 * two-element array and is unpacked into p and q.
 *
 * high is EXCLUSIVE here -- one past the last slot -- which is what the
 * book's own text requires: it hands `high` straight to Partition, whose
 * `k := high; while (q < k)` never reads A[high], and it recurses on
 * (q, high). The guard `low < high` is the non-empty test for that range.
 *
 * Termination: the pivot is an element of the range, so the equal block
 * [p..q) is never empty and both sub-ranges are strictly smaller.
 *
 * choosePivot takes lo/hi rather than low/high only to keep the generated
 * main's bound correct: llc.py reads an aux method that also declares
 * `high` as a signal that the program's high is inclusive, which it is not.
 *
 * Auto-generated from QuickSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ThreeWayPartition {
  ThreeWayPartition(A, pivot, low, high) {
    this.A = A;
    this.pivot = pivot;
    this.low = low;
    this.high = high;
    this.n = this.A.length;
    this.p = this.low;
    this.q = this.low;
    this.k = this.high;
    while ((this.q < this.k)) {
        if ((this.A[this.q] < this.pivot)) {
            const _sw1 = this.A[this.p]; this.A[this.p] = this.A[this.q]; this.A[this.q] = _sw1;
            this.p = (this.p + 1);
            this.q = (this.q + 1);
        } else {
            if ((this.A[this.q] > this.pivot)) {
                this.k = (this.k - 1);
                const _sw2 = this.A[this.q]; this.A[this.q] = this.A[this.k]; this.A[this.k] = _sw2;
            } else {
                this.q = (this.q + 1);
            }
        }
    }
    let result = new Array(2).fill(0);
    result[0] = this.p;
    result[1] = this.q;
    return result;
  }
}

class QuickSort {
  QuickSort(A, low, high) {
    let n = A.length;
    if ((low < high)) {
        let pivot = this.choosePivot(A, low, high);
        let pq = new ThreeWayPartition().ThreeWayPartition(A, pivot, low, high);
        let p = pq[0];
        let q = pq[1];
        this.QuickSort(A, low, p);
        this.QuickSort(A, q, high);
    }
  }

  choosePivot(A, lo, hi) {
    return A[lo];
  }
}

if (typeof module !== 'undefined') module.exports = QuickSort;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new QuickSort();
    prog.QuickSort(A, 0, A.length);
    console.log(JSON.stringify(A));
}
