/*
 * Gale's Top Trading Cycle (TTC) algorithm for the housing market,
 * matching bxx-housing.tex Algorithm TTC exactly: each stage (1) every
 * unfixed agent advances its wish pointer past houses already owned by
 * fixed agents, building the top-choice graph; (2) ALL node-disjoint
 * cycles in that graph are found at once (every unfixed node has
 * out-degree 1, so each agent either lies on a cycle or on a tail
 * leading into one); (3) every agent on ANY cycle this stage trades
 * and is fixed -- not a single pointer-chasing walk that resolves one
 * cycle at a time and rebuilds the whole graph for each one.
 *
 * Steps 1 and 3 are `forall`s, since each agent is handled independently
 * of the others; only the walk of step 2 is inherently sequential, and
 * even there the two sweeps over the recorded path are `forall`s.
 *
 * Auto-generated from TTC.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class TTC {
  TTC(pref) {
    this.pref = pref;
    this.n = this.pref.length;
    this.house = new Array(this.n).fill(0);
    this.fixed = new Array(this.n).fill(false);
    this.G = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.fixed[i] = false;
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i] = 0;
    }
    let numFixed = 0;
    while ((numFixed < this.n)) {
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if ((!this.fixed[i])) {
                while (this.fixed[this.pref[i][this.G[i]]]) {
                    this.G[i] = (this.G[i] + 1);
                }
            }
        }
        let state = new Array(this.n).fill(0);
        let inCycle = new Array(this.n).fill(false);
        let path = new Array(this.n).fill(0);
        let a = 0;
        while ((a < this.n)) {
            if (((!this.fixed[a]) && (state[a] === 0))) {
                let pathLen = 0;
                let cur = a;
                while ((state[cur] === 0)) {
                    state[cur] = 1;
                    path[pathLen] = cur;
                    pathLen = (pathLen + 1);
                    cur = this.pref[cur][this.G[cur]];
                }
                if ((state[cur] === 1)) {
                    let start = 0;
                    while ((path[start] !== cur)) {
                        start = (start + 1);
                    }
                    for (let p = start; p <= ((pathLen - 1)); p++) {
                        inCycle[path[p]] = true;
                    }
                }
                for (let p = 0; p <= ((pathLen - 1)); p++) {
                    state[path[p]] = 2;
                }
            }
            a = (a + 1);
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if (inCycle[i]) {
                this.house[i] = this.pref[i][this.G[i]];
                this.fixed[i] = true;
                numFixed = (numFixed + 1);
            }
        }
    }
    return this.house;
  }
}

if (typeof module !== 'undefined') module.exports = TTC;

if (typeof require !== 'undefined' && require.main === module) {
    const pref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const prog = new TTC();
    const result = prog.TTC(pref);
    console.log(JSON.stringify(result));
}
