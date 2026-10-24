/*
 * Odd-Even Sort: alternating odd- and even-indexed compare-swap passes.
 *
 * Auto-generated from OddEvenSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class OddEvenSort {
  sort(A) {
    this.A = A;
    this.n = this.A.length;
    this.found = true;
    while (this.found) {
        this.found = false;
        let j = 1;
        while ((j < (this.A.length - 1))) {
            if ((this.A[j] > this.A[(j + 1)])) {
                this.found = true;
                const _sw1 = this.A[j]; this.A[j] = this.A[(j + 1)]; this.A[(j + 1)] = _sw1;
            }
            j = (j + 2);
        }
        j = 0;
        while ((j < (this.A.length - 1))) {
            if ((this.A[j] > this.A[(j + 1)])) {
                this.found = true;
                const _sw2 = this.A[j]; this.A[j] = this.A[(j + 1)]; this.A[(j + 1)] = _sw2;
            }
            j = (j + 2);
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = OddEvenSort;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new OddEvenSort();
    prog.sort(A);
    console.log(JSON.stringify(A));
}
