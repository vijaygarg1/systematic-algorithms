/*
 * ReleaseTimes: composition program enforcing that job j cannot start
 * before its release time r[j].  Composed onto LLP-MinMaxLateness via
 * predicate conjunction:
 * [ LLP-MinMaxLateness(t, d, G) && ReleaseTimes(r, G) ].
 *
 * Auto-generated from ReleaseTimes.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ReleaseTimes {
  ReleaseTimes(r, G) {
    this.r = r;
    this.G = G;
    this.n = this.r.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((!(this.G[j] >= this.r[j]))) {
                this.G[j] = this.r[j];
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = ReleaseTimes;

if (typeof require !== 'undefined' && require.main === module) {
    const r = [0, 0, 1, 2];
    const G = [0, 0, 1, 1];
    const prog = new ReleaseTimes();
    const result = prog.ReleaseTimes(r, G);
    console.log(JSON.stringify(result));
}
