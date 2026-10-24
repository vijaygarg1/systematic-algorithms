/*
 * Return the first index i with A[i] = key, or -1 if absent.
 *
 * Auto-generated from LinearSearch.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LinearSearch {
  LinearSearch(A, key) {
    this.A = A;
    this.key = key;
    this.n = this.A.length;
    for (let i = 0; i <= ((this.A.length - 1)); i++) {
        if ((this.A[i] === this.key)) {
            return i;
        }
    }
    return (-1);
  }
}

if (typeof module !== 'undefined') module.exports = LinearSearch;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const key = 0;
    const prog = new LinearSearch();
    const result = prog.LinearSearch(A, key);
    console.log(JSON.stringify(result));
}
