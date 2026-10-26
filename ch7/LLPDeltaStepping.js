/*
 * LLP-Delta-Stepping: bucket-based relaxation with light/heavy edge phases.
 * This algorithm has complex control flow (repeat/until, set comprehensions)
 * beyond the standard forbidden/advance pattern.
 * The forbidden predicates are:
 * forbidden_L(v): exists (u,v) in E_light : G[u] + w[u][v] < G[v]
 *
 * Auto-generated from LLPDeltaStepping.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPDeltaStepping {
  LLPDeltaStepping(pre, w, isLight, G, delta, s) {
    this.pre = pre;
    this.w = w;
    this.isLight = isLight;
    this.G = G;
    this.delta = delta;
    this.s = s;
    this.n = this.pre.length;
    this.G[this.s] = 0;
    let changed = true;
    while (changed) {
        changed = false;
        let j = 0;
        while ((j < this.G.length)) {
            if (this.relaxLight(j, this.pre, this.w, this.isLight, this.G, this.delta)) {
                changed = true;
            }
            j = (j + 1);
        }
        j = 0;
        while ((j < this.G.length)) {
            if (this.relaxHeavy(j, this.pre, this.w, this.isLight, this.G, this.delta)) {
                changed = true;
            }
            j = (j + 1);
        }
    }
  }

  relaxLight(v, pre, w, isLight, G, delta) {
    let relaxed = false;
    let k = 0;
    while ((k < pre[v].length)) {
        let u = pre[v][k];
        if ((isLight[u][v] && ((G[u] + w[u][v]) < G[v]))) {
            G[v] = (G[u] + w[u][v]);
            relaxed = true;
        }
        k = (k + 1);
    }
    return relaxed;
  }

  relaxHeavy(v, pre, w, isLight, G, delta) {
    let relaxed = false;
    let k = 0;
    while ((k < pre[v].length)) {
        let u = pre[v][k];
        if (((!isLight[u][v]) && ((G[u] + w[u][v]) < G[v]))) {
            G[v] = (G[u] + w[u][v]);
            relaxed = true;
        }
        k = (k + 1);
    }
    return relaxed;
  }
}

if (typeof module !== 'undefined') module.exports = LLPDeltaStepping;

if (typeof require !== 'undefined' && require.main === module) {
    const pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]];
    const w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const isLight = [[true, false, false, false, false, false, false, false], [false, true, false, false, false, false, false, false], [false, false, true, false, false, false, false, false], [false, false, false, true, false, false, false, false], [false, false, false, false, true, false, false, false], [false, false, false, false, false, true, false, false], [false, false, false, false, false, false, true, false], [false, false, false, false, false, false, false, true]];
    const G = [3, 1, 6, 1, 6, 3, 6, 4];
    const delta = 0;
    const prog = new LLPDeltaStepping();
    prog.LLPDeltaStepping(pre, w, isLight, G, delta, 0);
    console.log(JSON.stringify(pre));
    console.log(JSON.stringify(w));
    console.log(JSON.stringify(isLight));
    console.log(JSON.stringify(G));
}
