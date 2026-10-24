/*
 * Conjunctive predicate detection: forbidden when G[j] -> G[i] (happened-before);
 * advance increments G[j] to the next local state.
 *
 * Auto-generated from LLPConjunctiveDetection.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPConjunctiveDetection {
  LLPConjunctiveDetection(vc, T) {
    this.vc = vc;
    this.T = T;
    this.n = this.vc.length;
    this.G = new Array(this.n).fill(0);
    for (let k = 0; k <= ((this.n - 1)); k++) {
        this.G[k] = 1;
    }
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (this.happenedBefore(j, this.G, this.vc)) {
                if ((this.G[j] >= this.T[j])) {
                    return null;
                } else {
                    this.G[j] = (this.G[j] + 1);
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }

  happenedBefore(j, G, vc) {
    let n = G.length;
    for (let i = 0; i <= ((n - 1)); i++) {
        if (((i !== j) && (vc[i][G[i]][j] >= G[j]))) {
            return true;
        }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPConjunctiveDetection;
