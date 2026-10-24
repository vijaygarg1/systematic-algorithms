/*
 * Maximum-cardinality interval scheduling: greedy by earliest finish time.
 *
 * Auto-generated from Interval.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Interval {
  schedule(s, f) {
    this.s = s;
    this.f = f;
    this.n = this.s.length;
    this.G = new Array(this.n).fill(0);
    if ((this.n > 0)) {
        this.G[0] = 1;
        let last = 0;
        for (let i = 1; i <= ((this.n - 1)); i++) {
            if ((this.s[i] >= this.f[last])) {
                this.G[i] = 1;
                last = i;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = Interval;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const prog = new Interval();
    const result = prog.schedule(s, f);
    console.log(JSON.stringify(result));
}
