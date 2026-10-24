/*
 * Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping.
 *
 * Auto-generated from ChainCoverFromMatching.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ChainCoverFromMatching {
  ChainCoverFromMatching(matchPartner) {
    this.matchPartner = matchPartner;
    this.n = this.matchPartner.length;
    this.C = new Array(this.n).fill(0);
    this.i = 0;
    while ((this.i < this.n)) {
        this.C[this.i] = this.i;
        this.i = (this.i + 1);
    }
    let u = 0;
    while ((u < this.n)) {
        let v = this.matchPartner[u];
        if ((v !== (0 - 1))) {
            this.C[u] = v;
        }
        u = (u + 1);
    }
    this.parentPointerJumping(this.C);
    return this.C;
  }

  parentPointerJumping(C) {
    let n = C.length;
    let changed = true;
    while (changed) {
        changed = false;
        let i = 0;
        while ((i < n)) {
            let p = C[i];
            if ((C[p] !== p)) {
                C[i] = C[p];
                changed = true;
            }
            i = (i + 1);
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = ChainCoverFromMatching;

if (typeof require !== 'undefined' && require.main === module) {
    const matchPartner = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new ChainCoverFromMatching();
    const result = prog.ChainCoverFromMatching(matchPartner);
    console.log(JSON.stringify(result));
}
