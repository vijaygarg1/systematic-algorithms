/*
 * Conjunctive predicate detection (bxx-complexity.tex Alg LLP-ConjunctiveDetection).
 * Forbidden when P_j's selected state is INCOMPATIBLE with another: some i has
 * vc[i][G[i]][j] > vc[j][G[j]][j]. Restored 2026-10-26 -- `>= G[j]` compared an event
 * count against a filtered-trace index and rejected equality, losing real solutions.
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
            if (this.incompatible(j, this.G, this.vc)) {
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

  incompatible(j, G, vc) {
    let n = G.length;
    for (let i = 0; i <= ((n - 1)); i++) {
        if (((i !== j) && (vc[i][G[i]][j] > vc[j][G[j]][j]))) {
            return true;
        }
    }
    return false;
  }
}

if (typeof module !== 'undefined') module.exports = LLPConjunctiveDetection;
