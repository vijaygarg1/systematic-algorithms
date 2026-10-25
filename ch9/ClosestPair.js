/*
 * Classical divide-and-conquer closest pair of points.
 *
 * Px and Py hold the coordinates, indexed by point number, with the points
 * numbered in increasing x order. Ys lists this subproblem's point numbers
 * in increasing y order, which is what lets the strip scan stop after a
 * constant number of neighbours. G is the running answer: G[0] the best
 * SQUARED distance (squared, so no sqrt is ever needed), G[1] and G[2] the
 * two points achieving it.
 *
 * The recursive worker is named ClosestPairOn rather than overloading
 * ClosestPair on arity. Java tolerates the overload, but Python does not:
 * the second `def ClosestPair` simply replaces the first, so the generated
 * ClosestPair.py called the 6-argument body with 5 arguments and died with
 * "missing 1 required positional argument: 'G'". The entry point keeps the
 * book's algorithm name; ClosestPairOn is the same function over an
 * explicit index range lo..hi with the running answer G threaded through.
 *
 * Auto-generated from ClosestPair.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ClosestPair {
  ClosestPair(lo, hi, Px, Py, Ys) {
    this.lo = lo;
    this.hi = hi;
    this.Px = Px;
    this.Py = Py;
    this.Ys = Ys;
    this.n = this.Px.length;
    this.G = new Array(3).fill(0.0);
    this.G[0] = Infinity;
    this.G[1] = (0 - 1);
    this.G[2] = (0 - 1);
    this.ClosestPairOn(this.lo, this.hi, this.Px, this.Py, this.Ys, this.G);
    return this.G;
  }

  ClosestPairOn(lo, hi, Px, Py, Ys, G) {
    if ((hi <= lo)) {
        return;
    }
    if (((hi - lo) <= 2)) {
        for (let i = lo; i <= ((hi - 1)); i++) {
            let j = (i + 1);
            while ((j <= hi)) {
                this.check(i, j, Px, Py, G);
                j = (j + 1);
            }
        }
        return;
    }
    let mid = Math.trunc((lo + hi) / 2);
    let midX = Px[mid];
    let n = ((hi - lo) + 1);
    let Ls = new Array(n).fill(0);
    let Rs = new Array(n).fill(0);
    let nl = 0;
    let nr = 0;
    let k = 0;
    while ((k < n)) {
        let p = Ys[k];
        if ((p <= mid)) {
            Ls[nl] = p;
            nl = (nl + 1);
        } else {
            Rs[nr] = p;
            nr = (nr + 1);
        }
        k = (k + 1);
    }
    this.ClosestPairOn(lo, mid, Px, Py, Ls, G);
    this.ClosestPairOn((mid + 1), hi, Px, Py, Rs, G);
    let strip = new Array(n).fill(0);
    let m = 0;
    k = 0;
    while ((k < n)) {
        let p = Ys[k];
        let dx = (Px[p] - midX);
        if (((dx * dx) < G[0])) {
            strip[m] = p;
            m = (m + 1);
        }
        k = (k + 1);
    }
    for (let a = 0; a <= ((m - 1)); a++) {
        let b = (a + 1);
        let limit = (a + 15);
        while (((b < m) && (b <= limit))) {
            this.check(strip[a], strip[b], Px, Py, G);
            b = (b + 1);
        }
    }
  }

  check(i, j, Px, Py, G) {
    let dx = (Px[i] - Px[j]);
    let dy = (Py[i] - Py[j]);
    let d = ((dx * dx) + (dy * dy));
    if ((d < G[0])) {
        G[0] = d;
        G[1] = i;
        G[2] = j;
    }
  }
}

if (typeof module !== 'undefined') module.exports = ClosestPair;

if (typeof require !== 'undefined' && require.main === module) {
    const Px = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const Py = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const Ys = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new ClosestPair();
    const result = prog.ClosestPair(0, Ys.length - 1, Px, Py, Ys);
    console.log(JSON.stringify(result));
}
