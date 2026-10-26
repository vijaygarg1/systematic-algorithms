/*
 * Classical Gale-Shapley man-optimal stable-matching loop.
 *
 * Auto-generated from GaleShapley.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class GaleShapley {
  match(mpref, rank) {
    this.mpref = mpref;
    this.rank = rank;
    this.n = (this.mpref.length - 1);
    this.G = new Array((this.n + 1)).fill(0);
    this.partner = new Array((this.n + 1)).fill(0);
    this.freeMen = new Array((this.n + 1)).fill(0);
    this.top = 0;
    for (let i = 1; i <= (this.n); i++) {
        this.freeMen[this.top] = i;
        this.top = (this.top + 1);
    }
    while ((this.top > 0)) {
        this.top = (this.top - 1);
        let i = this.freeMen[this.top];
        this.G[i] = (this.G[i] + 1);
        let z = this.mpref[i][this.G[i]];
        if ((this.partner[z] === 0)) {
            this.partner[z] = i;
        } else {
            if ((this.rank[z][i] < this.rank[z][this.partner[z]])) {
                this.freeMen[this.top] = this.partner[z];
                this.top = (this.top + 1);
                this.partner[z] = i;
            } else {
                this.freeMen[this.top] = i;
                this.top = (this.top + 1);
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = GaleShapley;

if (typeof require !== 'undefined' && require.main === module) {
    const mpref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const rank = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new GaleShapley();
    const result = prog.match(mpref, rank);
    console.log(JSON.stringify(result));
}
