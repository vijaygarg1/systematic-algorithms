/*
 * LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
 * time G[j] to the prefix sum of earlier processing times.
 *
 * Auto-generated from LLPMinMaxLateness.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPMinMaxLateness {
  LLPMinMaxLateness(t, d) {
    this.t = t;
    this.d = d;
    this.n = this.t.length;
    this.S = new Array(this.n).fill(0);
    this.G = new Array(this.n).fill(0);
    for (let j = 1; j <= ((this.n - 1)); j++) {
        this.S[j] = (this.S[(j - 1)] + this.t[(j - 1)]);
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if ((!(this.G[j] >= this.S[j]))) {
                this.G[j] = this.S[j];
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPMinMaxLateness;

if (typeof require !== 'undefined' && require.main === module) {
    const t = [0, 0, 1, 2];
    const d = [0, 0, 1, 1];
    const prog = new LLPMinMaxLateness();
    const result = prog.LLPMinMaxLateness(t, d);
    console.log(JSON.stringify(result));
}
