/*
 * MergeTwo: merge two sorted arrays into one, matching bxx-sort.tex
 * Algorithm MergeTwo (fig:mergetwo) line for line -- including the two
 * leftover-copy loops, which the book also prints as a single line each.
 *
 * Auto-generated from MergeTwo.llp by lang/llcjssrc.py.
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

if (typeof module !== 'undefined') module.exports = MergeTwo;

if (typeof require !== 'undefined' && require.main === module) {
    const B = [0, 0, 1, 2];
    const C = [0, 0, 1, 1];
    const prog = new MergeTwo();
    const result = prog.merge(B, C);
    console.log(JSON.stringify(result));
}
