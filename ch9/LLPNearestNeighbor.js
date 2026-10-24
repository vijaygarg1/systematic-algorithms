/*
 * LLP-NearestNeighbor: find nearest neighbor distance for each point.
 * px[j], py[j] are x,y coordinates of point j.
 *
 * Auto-generated from LLPNearestNeighbor.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPNearestNeighbor {
  LLPNearestNeighbor(px, py) {
    this.px = px;
    this.py = py;
    this.n = this.px.length;
    this.G = new Array(this.n).fill(Infinity);
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= (this.n - 1); j++) {
            if ((!(this.G[j] <= this.nearestDist(this.px, this.py, j)))) {
                this.G[j] = this.nearestDist(this.px, this.py, j);
                _changed = true;
            }
        }
    }
  }

  nearestDist(px, py, j) {
    let best = Infinity;
    for (let k = 0; k <= ((px.length - 1)); k++) {
        if ((k !== j)) {
            let d = this.dist(px, py, j, k);
            if ((d < best)) {
                best = d;
            }
        }
    }
    return best;
  }

  dist(px, py, j, k) {
    let dx = (px[j] - px[k]);
    let dy = (py[j] - py[k]);
    return ((dx * dx) + (dy * dy));
  }
}

if (typeof module !== 'undefined') module.exports = LLPNearestNeighbor;

if (typeof require !== 'undefined' && require.main === module) {
    const px = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const py = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0];
    const prog = new LLPNearestNeighbor();
    prog.LLPNearestNeighbor(px, py);
    console.log(JSON.stringify(px));
    console.log(JSON.stringify(py));
}
