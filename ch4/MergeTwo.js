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
    this.m = this.B.length;
    this.n = this.C.length;
    this.D = new Array((this.m + this.n)).fill(0);
    this.i = 0;
    this.j = 0;
    this.k = 0;
    while (((this.i < this.m) && (this.j < this.n))) {
        if ((this.B[this.i] <= this.C[this.j])) {
            this.D[this.k] = this.B[this.i];
            this.i = (this.i + 1);
        } else {
            this.D[this.k] = this.C[this.j];
            this.j = (this.j + 1);
        }
        this.k = (this.k + 1);
    }
    while ((this.i < this.m)) {
        this.D[this.k] = this.B[this.i];
        this.i = (this.i + 1);
        this.k = (this.k + 1);
    }
    while ((this.j < this.n)) {
        this.D[this.k] = this.C[this.j];
        this.j = (this.j + 1);
        this.k = (this.k + 1);
    }
    return this.D;
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
