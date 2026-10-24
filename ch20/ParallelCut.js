/*
 * ParallelCut: find the first consistent cut satisfying a conjunctive predicate.
 * Five-step parallel algorithm using state rejection and transitive closure.
 *
 * Auto-generated from ParallelCut.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class ParallelCut {
  ParallelCut(vc, n, m) {
    this.vc = vc;
    this.n = n;
    this.m = m;
    this.F = new Array(this.n).fill(0);
    this.i = 0;
    while ((this.i < this.n)) {
        let j = 0;
        while ((j < this.n)) {
            if (((this.i !== j) && this.hb(this.vc, this.i, 0, j, 0, this.n))) {
                this.F[this.i] = 1;
            }
            j = (j + 1);
        }
        this.i = (this.i + 1);
    }
    let sz = (this.n * this.m);
    let R = Array.from({length: sz}, () => new Array(sz).fill(false));
    let idx = 0;
    while ((idx < sz)) {
        R[idx][idx] = true;
        idx = (idx + 1);
    }
    this.i = 0;
    while ((this.i < this.n)) {
        let j = 0;
        while ((j < (this.m - 1))) {
            let ip = 0;
            while ((ip < this.n)) {
                if ((ip !== this.i)) {
                    let jp = 0;
                    while ((jp < this.m)) {
                        if (this.hb(this.vc, ip, jp, this.i, (j + 1), this.n)) {
                            R[((this.i * this.m) + j)][((ip * this.m) + jp)] = true;
                        }
                        jp = (jp + 1);
                    }
                }
                ip = (ip + 1);
            }
            j = (j + 1);
        }
        this.i = (this.i + 1);
    }
    this.transitiveClosure(R, sz);
    let valid = Array.from({length: this.n}, () => new Array(this.m).fill(false));
    this.i = 0;
    while ((this.i < this.n)) {
        let j = 0;
        while ((j < this.m)) {
            valid[this.i][j] = true;
            j = (j + 1);
        }
        this.i = (this.i + 1);
    }
    this.i = 0;
    while ((this.i < this.n)) {
        if ((this.F[this.i] === 1)) {
            let ip = 0;
            while ((ip < this.n)) {
                let jp = 0;
                while ((jp < this.m)) {
                    if (R[((this.i * this.m) + 0)][((ip * this.m) + jp)]) {
                        valid[ip][jp] = false;
                    }
                    jp = (jp + 1);
                }
                ip = (ip + 1);
            }
        }
        this.i = (this.i + 1);
    }
    let cut = new Array(this.n).fill(0);
    this.i = 0;
    while ((this.i < this.n)) {
        cut[this.i] = (0 - 1);
        let j = 0;
        while ((j < this.m)) {
            if (valid[this.i][j]) {
                if (((j === 0) || (!valid[this.i][(j - 1)]))) {
                    cut[this.i] = j;
                    j = this.m;
                }
            }
            j = (j + 1);
        }
        this.i = (this.i + 1);
    }
    return cut;
  }

  hb(vc, pi, si, pj, sj, n) {
    let idx1 = ((pi * n) + si);
    let idx2 = ((pj * n) + sj);
    return ((vc[idx1][pj] >= sj) && (!((pi === pj) && (si === sj))));
  }

  transitiveClosure(R, sz) {
    let k = 0;
    while ((k < sz)) {
        let i = 0;
        while ((i < sz)) {
            let j = 0;
            while ((j < sz)) {
                if ((R[i][k] && R[k][j])) {
                    R[i][j] = true;
                }
                j = (j + 1);
            }
            i = (i + 1);
        }
        k = (k + 1);
    }
  }
}

if (typeof module !== 'undefined') module.exports = ParallelCut;

if (typeof require !== 'undefined' && require.main === module) {
    const vc = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const n = 0;
    const m = 0;
    const prog = new ParallelCut();
    const result = prog.ParallelCut(vc, n, m);
    console.log(JSON.stringify(result));
}
