/*
 * LLP-Boruvka: Boruvka's minimum spanning tree with the component
 * contraction done by LLP pointer jumping instead of recursion.
 *
 * Each round every active component finds its cheapest outgoing edge,
 * points at the component on the other end, and then the guard below
 * jumps those pointers until each chain collapses to one representative:
 * forbidden when G[j] is not yet its own component's root, advance by
 * following one more link. Mutual choices are broken by keeping the lower
 * index as the root, which is what pointTo decides.
 *
 * A round that merges nothing means the graph is disconnected, which is
 * what progress stops on.
 *
 * Auto-generated from LLPBoruvka.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPBoruvka {
  LLPBoruvka(n, U, V, W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.T = new Array(this.U.length).fill(false);
    this.active = new Array(this.n).fill(false);
    this.G = new Array(this.n).fill(0);
    this.mweEdge = new Array(this.n).fill(0);
    this.mweW = new Array(this.n).fill(0.0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.G[i] = i;
        this.active[i] = true;
    }
    let progress = true;
    while (((this.countActive() > 1) && progress)) {
        let before = this.countActive();
        for (let i = 0; i <= ((this.n - 1)); i++) {
            this.mweEdge[i] = (0 - 1);
            this.mweW[i] = Infinity;
        }
        for (let e = 0; e <= ((this.U.length - 1)); e++) {
            let ru = this.find(this.U[e]);
            let rv = this.find(this.V[e]);
            if (((this.active[ru] && this.active[rv]) && (ru !== rv))) {
                this.relax(ru, e, this.W);
                this.relax(rv, e, this.W);
            }
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if (this.active[i]) {
                this.G[i] = this.pointTo(i);
            }
        }
        let _changed = true;
        while (_changed) {
            _changed = false;
            for (let j = 0; j <= ((this.n - 1)); j++) {
                if ((this.active[j] && (this.G[j] !== this.G[this.G[j]]))) {
                    this.G[j] = this.G[this.G[j]];
                    _changed = true;
                }
            }
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if ((this.active[i] && (this.mweEdge[i] !== (0 - 1)))) {
                this.T[this.mweEdge[i]] = true;
            }
        }
        for (let i = 0; i <= ((this.n - 1)); i++) {
            if ((this.active[i] && (this.G[i] !== i))) {
                this.active[i] = false;
            }
        }
        progress = (this.countActive() < before);
    }
    return this.T;
  }

  relax(comp, e, W) {
    if ((W[e] < this.mweW[comp])) {
        this.mweW[comp] = W[e];
        this.mweEdge[comp] = e;
    }
  }

  pointTo(i) {
    if ((this.mweEdge[i] === (0 - 1))) {
        return i;
    }
    let w = this.otherEndpoint(this.mweEdge[i], i);
    let wEdge = this.mweEdge[w];
    if ((((wEdge !== (0 - 1)) && (this.otherEndpoint(wEdge, w) === i)) && (i < w))) {
        return i;
    }
    return w;
  }

  find(v) {
    while ((this.G[v] !== v)) {
        v = this.G[v];
    }
    return v;
  }

  countActive() {
    return (() => { let _a = 0; for (let i = 0; i <= ((this.n - 1)); i++) { if (this.active[i]) _a += 1; } return _a; })();
  }

  otherEndpoint(e, v) {
    if ((this.find(this.U[e]) === v)) {
        return this.find(this.V[e]);
    }
    return this.find(this.U[e]);
  }
}

if (typeof module !== 'undefined') module.exports = LLPBoruvka;

if (typeof require !== 'undefined' && require.main === module) {
    const U = [0, 0, 1, 2];
    const V = [0, 0, 1, 1];
    const W = [0, 0, 0, 2];
    const prog = new LLPBoruvka();
    const result = prog.LLPBoruvka(U.length, U, V, W);
    console.log(JSON.stringify(result));
}
