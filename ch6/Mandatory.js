/*
 * Mandatory-subset constraint: certain intervals must be selected.
 * S[j] is true iff interval j is mandatory.
 *
 * Auto-generated from Mandatory.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Mandatory {
  Mandatory(s, f, S, G) {
    this.s = s;
    this.f = f;
    this.S = S;
    this.G = G;
    this.n = this.s.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((this.S[j] && (!this.G[j]))) {
                if (this.overlaps(j, this.G, this.s, this.f)) {
                    return null;
                } else {
                    this.G[j] = true;
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }

  overlaps(j, G, s, f) {
    let result = false;
    let k = 0;
    while ((k < G.length)) {
        if ((((G[k] && (k !== j)) && (s[k] < f[j])) && (s[j] < f[k]))) {
            result = true;
        }
        k = (k + 1);
    }
    return result;
  }
}

if (typeof module !== 'undefined') module.exports = Mandatory;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const S = [false, false, false, false];
    const G = [false, false, false, false];
    const prog = new Mandatory();
    const result = prog.Mandatory(s, f, S, G);
    console.log(JSON.stringify(result));
}
