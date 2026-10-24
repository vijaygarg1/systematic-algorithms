/*
 * HopBound: composition program enforcing a hop budget on the
 * shortest-path relaxation chain.  h[j] is the number of edges along
 * the chain that established G[j]; the constraint is h[j] <= k.
 * Composed onto LLP-Dijkstra via predicate conjunction:
 * [ LLP-Dijkstra-Hops(s, w, G, h) && HopBound(h, k) ].
 *
 * Auto-generated from HopBound.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class HopBound {
  HopBound(h, k, G) {
    this.h = h;
    this.k = k;
    this.G = G;
    this.n = this.h.length;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((this.h[j] > this.k)) {
                return null;
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = HopBound;

if (typeof require !== 'undefined' && require.main === module) {
    const h = [0, 0, 1, 2];
    const k = 0;
    const G = [0, 0, 1, 1];
    const prog = new HopBound();
    const result = prog.HopBound(h, k, G);
    console.log(JSON.stringify(result));
}
