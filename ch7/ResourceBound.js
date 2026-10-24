/*
 * ResourceBound: composition program enforcing a resource budget B on
 * the shortest-path relaxation chain.  rho[j] is the total resource
 * consumed along the chain that established G[j]; the constraint is
 * rho[j] <= B.  Composed onto LLP-Dijkstra via predicate conjunction:
 * [ LLP-Dijkstra-Res(s, w, r, G, rho) && ResourceBound(rho, B) ].
 *
 * Auto-generated from ResourceBound.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ResourceBound {
  ResourceBound(rho, B, G) {
    this.rho = rho;
    this.B = B;
    this.G = G;
    this.n = this.rho.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((this.rho[j] > this.B)) {
                return null;
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = ResourceBound;

if (typeof require !== 'undefined' && require.main === module) {
    const rho = [0, 0, 1, 2];
    const B = 0;
    const G = [0, 0, 1, 1];
    const prog = new ResourceBound();
    const result = prog.ResourceBound(rho, B, G);
    console.log(JSON.stringify(result));
}
