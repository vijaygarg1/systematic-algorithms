/*
 * MergeSort as an example of recursion, matching bxx-introduction.tex
 * Algorithm MergeSort-Intro (algo:mergesort): split, sort each half, merge.
 * Merge copies the range into a scratch array B and then walks the two
 * halves back into A; its two leftover-copy loops are one line each, as
 * the book prints them. B is sized to the sub-range, not to all of A:
 *
 * Auto-generated from MergeSortIntro.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MergeSortIntro {
  MergeSortIntro(A, low, high) {
    let n = A.length;
    if ((low < high)) {
        let mid = Math.trunc((low + high) / 2);
        this.MergeSortIntro(A, low, mid);
        this.MergeSortIntro(A, (mid + 1), high);
        this.Merge(A, low, mid, high);
    }
  }

  Merge(A, low, mid, high) {
    let B = new Array(((high - low) + 1)).fill(0);
    for (let k = low; k <= (high); k++) {
        B[(k - low)] = A[k];
    }
    let i = low;
    let j = (mid + 1);
    let k = low;
    while (((i <= mid) && (j <= high))) {
        if ((B[(i - low)] <= B[(j - low)])) {
            A[k] = B[(i - low)];
            i = (i + 1);
        } else {
            A[k] = B[(j - low)];
            j = (j + 1);
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
  }
}

if (typeof module !== 'undefined') module.exports = MergeSortIntro;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new MergeSortIntro();
    prog.MergeSortIntro(A, 0, A.length - 1);
    console.log(JSON.stringify(A));
}
