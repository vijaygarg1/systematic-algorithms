/*
 * Return any index of key in a sorted array A, or -1 if absent.
 *
 * Auto-generated from BinarySearch.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class BinarySearch {
  BinarySearch(A, key, low, high) {
    let n = A.length;
    if ((low > high)) {
        return (-1);
    }
    let mid = Math.trunc((low + high) / 2);
    if ((A[mid] === key)) {
        return mid;
    }
    if ((A[mid] > key)) {
        return this.BinarySearch(A, key, low, (mid - 1));
    }
    return this.BinarySearch(A, key, (mid + 1), high);
  }
}

if (typeof module !== 'undefined') module.exports = BinarySearch;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const key = 0;
    const prog = new BinarySearch();
    const result = prog.BinarySearch(A, key, 0, A.length);
    console.log(JSON.stringify(result));
}
