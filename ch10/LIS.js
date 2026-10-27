/*
 * Classical O(n²) LIS DP.
 *
 * Auto-generated from LIS.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LIS {
  solve(A) {
    this.A = A;
    this.n = this.A.length;
    this.G = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i] = 1;
    }
    for (let i = 1; i <= ((this.n - 1)); i++) {
        for (let j = 0; j <= ((i - 1)); j++) {
            if ((this.A[j] < this.A[i])) {
                this.G[i] = Math.max(this.G[i], (this.G[j] + 1));
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LIS;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new LIS();
    const result = prog.solve(A);
    console.log(JSON.stringify(result));
}
