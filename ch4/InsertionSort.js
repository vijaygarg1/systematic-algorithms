/*
 * Insertion sort: walk each new element down to its place.
 *
 * Auto-generated from InsertionSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class InsertionSort {
  sort(A) {
    this.A = A;
    this.n = this.A.length;
    for (let i = 1; i <= ((this.A.length - 1)); i++) {
        let j = (i - 1);
        let done = false;
        while (((j >= 0) && (!done))) {
            if ((this.A[j] <= this.A[(j + 1)])) {
                done = true;
            } else {
                const _sw1 = this.A[j]; this.A[j] = this.A[(j + 1)]; this.A[(j + 1)] = _sw1;
                j = (j - 1);
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = InsertionSort;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new InsertionSort();
    prog.sort(A);
    console.log(JSON.stringify(A));
}
