/*
 * Bubble sort: repeated adjacent compare-and-swap until a clean pass.
 *
 * Auto-generated from BubbleSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BubbleSort {
  BubbleSort(A) {
    this.A = A;
    this.n = this.A.length;
    this.found = true;
    while (this.found) {
        this.found = false;
        for (let j = 0; j <= ((this.n - 2)); j++) {
            if ((this.A[j] > this.A[(j + 1)])) {
                this.found = true;
                const _sw1 = this.A[j]; this.A[j] = this.A[(j + 1)]; this.A[(j + 1)] = _sw1;
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = BubbleSort;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new BubbleSort();
    prog.BubbleSort(A);
    console.log(JSON.stringify(A));
}
