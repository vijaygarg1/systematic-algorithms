/*
 * Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.
 *
 * Auto-generated from ApproxSetCover.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ApproxSetCover {
  ApproxSetCover(mem, inc) {
    this.mem = mem;
    this.inc = inc;
    this.m = this.mem.length;
    this.n = this.inc.length;
    this.C = new Array(this.m).fill(false);
    this.covered = new Array(this.n).fill(false);
    this.cnt = new Array(this.m).fill(0);
    this.remaining = this.n;
    for (let s = 0; s <= ((this.m - 1)); s++) {
        this.cnt[s] = this.mem[s].length;
    }
    while ((this.remaining > 0)) {
        let bestIdx = 0;
        let bestCover = (0 - 1);
        let s = 0;
        while ((s < this.m)) {
            if ((this.cnt[s] > bestCover)) {
                bestCover = this.cnt[s];
                bestIdx = s;
            }
            s = (s + 1);
        }
        this.C[bestIdx] = true;
        let k = 0;
        while ((k < this.mem[bestIdx].length)) {
            let e = this.mem[bestIdx][k];
            if ((!this.covered[e])) {
                this.covered[e] = true;
                this.remaining = (this.remaining - 1);
                let t = 0;
                while ((t < this.inc[e].length)) {
                    this.cnt[this.inc[e][t]] = (this.cnt[this.inc[e][t]] - 1);
                    t = (t + 1);
                }
            }
            k = (k + 1);
        }
    }
    return this.C;
  }
}

if (typeof module !== 'undefined') module.exports = ApproxSetCover;

if (typeof require !== 'undefined' && require.main === module) {
    const mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new ApproxSetCover();
    const result = prog.ApproxSetCover(mem, inc);
    console.log(JSON.stringify(result));
}
