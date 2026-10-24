/*
 * Σ_{i,j < n} i*j computed by a doubly-nested loop.
 *
 * Auto-generated from NestedSum.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class NestedSum {
  NestedSum(n) {
    this.n = n;
    this.total = 0;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        for (let j = 0; j <= ((this.n - 1)); j++) {
            this.total = (this.total + (i * j));
        }
    }
    return this.total;
  }
}

if (typeof module !== 'undefined') module.exports = NestedSum;

if (typeof require !== 'undefined' && require.main === module) {
    const n = 3;
    const prog = new NestedSum();
    const result = prog.NestedSum(n);
    console.log(JSON.stringify(result));
}
