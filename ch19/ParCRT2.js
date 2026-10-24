/*
 * Par-CRT2: descending parallel Chinese Remainder Theorem.
 * Searches for the largest solution below M.
 *
 * Auto-generated from ParCRT2.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ParCRT2 {
  ParCRT2(m, b, M) {
    this.m = m;
    this.b = b;
    this.M = M;
    this.n = this.m.length;
    this.G = new Array(this.m.length).fill(0);
    this.j = 0;
    while ((this.j < this.m.length)) {
        let r = ((this.M - 1) % this.m[this.j]);
        if ((r >= this.b[this.j])) {
            this.G[this.j] = (((this.M - 1) - r) + this.b[this.j]);
        } else {
            this.G[this.j] = ((((this.M - 1) - r) + this.b[this.j]) - this.m[this.j]);
        }
        this.j = (this.j + 1);
    }
    let changed = true;
    while (changed) {
        changed = false;
        this.j = 0;
        while ((this.j < this.m.length)) {
            let minVal = this.G[0];
            let i = 1;
            while ((i < this.m.length)) {
                if ((this.G[i] < minVal)) {
                    minVal = this.G[i];
                }
                i = (i + 1);
            }
            if ((this.G[this.j] > minVal)) {
                let diff = (this.G[this.j] - minVal);
                let steps = Math.trunc(((diff + this.m[this.j]) - 1) / this.m[this.j]);
                this.G[this.j] = (this.G[this.j] - (steps * this.m[this.j]));
                changed = true;
            }
            this.j = (this.j + 1);
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = ParCRT2;

if (typeof require !== 'undefined' && require.main === module) {
    const m = [3, 5, 7, 11];
    const b = [0, 0, 1, 2];
    const M = 0;
    const prog = new ParCRT2();
    const result = prog.ParCRT2(m, b, M);
    console.log(JSON.stringify(result));
}
