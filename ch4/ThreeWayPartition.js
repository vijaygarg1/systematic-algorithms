/*
 * Dutch-flag partition: split A[low..high) into <, =, > pivot regions.
 * Matches bxx-sort.tex Algorithm ThreeWayPartition (fig:partition) line
 * for line. The book returns the pair (p, q); LL has no tuple return, so
 * the two boundary indices come back in a two-slot array instead -- the
 * only difference between the two.
 *
 * Auto-generated from ThreeWayPartition.llp by lang/llcjssrc.py.
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

if (typeof module !== 'undefined') module.exports = ThreeWayPartition;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const pivot = 5;
    const prog = new ThreeWayPartition();
    const result = prog.ThreeWayPartition(A, pivot, 0, A.length);
    console.log(JSON.stringify(result));
}
