/*
 * Count pairs (i, j) with i < j and A[i] + A[j] = target.
 *
 * Auto-generated from PairSum.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class PairSum {
  PairSum(A, target) {
    this.A = A;
    this.target = target;
    this.n = this.A.length;
    this.count = 0;
    for (let i = 0; i <= ((this.A.length - 1)); i++) {
        for (let j = (i + 1); j <= ((this.A.length - 1)); j++) {
            if (((this.A[i] + this.A[j]) === this.target)) {
                this.count = (this.count + 1);
            }
        }
    }
    return this.count;
  }
}

if (typeof module !== 'undefined') module.exports = PairSum;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const target = 0;
    const prog = new PairSum();
    const result = prog.PairSum(A, target);
    console.log(JSON.stringify(result));
}
