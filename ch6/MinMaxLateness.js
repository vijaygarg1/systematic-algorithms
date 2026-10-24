/*
 * Min-max-lateness scheduling: greedy by earliest deadline.
 *
 * Auto-generated from MinMaxLateness.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class MinMaxLateness {
  schedule(t, d) {
    this.t = t;
    this.d = d;
    this.n = this.t.length;
    this.G = new Array(this.n).fill(0);
    this.last = 0;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i] = this.last;
        this.last = (this.last + this.t[i]);
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = MinMaxLateness;

if (typeof require !== 'undefined' && require.main === module) {
    const t = [0, 0, 1, 2];
    const d = [0, 0, 1, 1];
    const prog = new MinMaxLateness();
    const result = prog.schedule(t, d);
    console.log(JSON.stringify(result));
}
