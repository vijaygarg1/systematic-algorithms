/*
 * Companion-only: book shows LLP-FloydWarshall only as LaTeX pseudocode,
 * no .llp counterpart to sync from -- see COMPANION_ONLY_STEMS in
 * sync-code-from-lang.py or a sync run will delete this file.
 * k stages the intermediate vertex 0..n-1; each stage's forbidden/advance
 * (i,j) fixpoint must sit directly in the k-stage body, not nested inside
 *
 * Auto-generated from LLPFloydWarshall.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPFloydWarshall {
  LLPFloydWarshall(A) {
    this.A = A;
    this.k = 0;
    this.n = this.A.length;
    this.G = Array.from({length: this.n}, () => new Array(this.n).fill(0.0));
    for (let i = 0; i <= ((this.n - 1)); i++) {
        for (let j = 0; j <= ((this.n - 1)); j++) {
            this.G[i][j] = this.A[i][j];
        }
    }
    while ((this.k < this.n)) {
        let _changed = true;
        while (_changed) {
            _changed = false;
            for (let i = 0; i <= (this.n - 1); i++) {
                for (let j = 0; j <= ((this.n - 1)); j++) {
                    if ((!(this.G[i][j] <= (this.G[i][this.k] + this.G[this.k][j])))) {
                        this.G[i][j] = (this.G[i][this.k] + this.G[this.k][j]);
                        _changed = true;
                    }
                }
            }
        }
        this.k = (this.k + 1);
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPFloydWarshall;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]];
    const prog = new LLPFloydWarshall();
    const result = prog.LLPFloydWarshall(A);
    console.log(JSON.stringify(result));
}
