/*
 * Counting inversions by divide and conquer, matching bxx-divideConquer.tex
 * Algorithm CountingInversions (algo:counting-inversions): inversions in the
 * left half, plus those in the right half, plus those spanning the two,
 * which a merge counts for free -- when A[j] from the right half is taken
 * first, it is inverted with every element still left in the left half.
 *
 * Laid out as the book prints it: one declaration line for the four
 * counters, and a single line for each leftover-copy loop.
 *
 * Auto-generated from CountingInversions.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class CountingInversions {
  count(A, low, high) {
    let n = A.length;
    if ((low >= high)) {
        return 0;
    }
    let mid = Math.trunc((low + high) / 2);
    let invLeft = this.count(A, low, mid);
    let invRight = this.count(A, (mid + 1), high);
    let invMerge = this.mergeAndCount(A, low, mid, high);
    return ((invLeft + invRight) + invMerge);
  }

  mergeAndCount(A, low, mid, high) {
    let B = new Array(((high - low) + 1)).fill(0);
    for (let k = low; k <= (high); k++) {
        B[(k - low)] = A[k];
    }
    let i = low;
    let j = (mid + 1);
    let k = low;
    let inv = 0;
    while (((i <= mid) && (j <= high))) {
        if ((B[(i - low)] <= B[(j - low)])) {
            A[k] = B[(i - low)];
            i = (i + 1);
        } else {
            A[k] = B[(j - low)];
            j = (j + 1);
            inv = (inv + ((mid - i) + 1));
        }
        k = (k + 1);
    }
    while ((i <= mid)) {
        A[k] = B[(i - low)];
        i = (i + 1);
        k = (k + 1);
    }
    while ((j <= high)) {
        A[k] = B[(j - low)];
        j = (j + 1);
        k = (k + 1);
    }
    return inv;
  }
}

if (typeof module !== 'undefined') module.exports = CountingInversions;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new CountingInversions();
    const result = prog.count(A, 0, A.length - 1);
    console.log(JSON.stringify(result));
}
