/*
 * VertexImplications: if v is in the cover and (v,w) is an implication,
 * then w must also be in the cover.
 * forbidden(w): exists v with (v,w) in I and G[v]=1 and G[w]=0.
 * advance: G[w] := 1.
 *
 * Auto-generated from VertexImplications.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class VertexImplications {
  VertexImplications(inI, G) {
    this.inI = inI;
    this.G = G;
    this.n = this.inI.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let w = 0; w <= ((this.n - 1)); w++) {
            if ((() => { for (const v of this.inI[w]) { if ((this.G[v] && (!this.G[w]))) return true; } return false; })()) {
                this.G[w] = true;
                _changed = true;
            }
        }
    }
  }
}

if (typeof module !== 'undefined') module.exports = VertexImplications;
