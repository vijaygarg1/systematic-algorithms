/*
 * LLP driver for the least mincut satisfying a lattice-linear side predicate.
 *
 * Auto-generated from LLPMincut.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPMincut {
  LLPMincut(G) {
    this.G = G;
    this.n = this.G.length;
    this.done = false;
    while ((!this.done)) {
        let changed = true;
        while (changed) {
            changed = false;
            let j = 0;
            while ((j < this.n)) {
                if (this.forbiddenForB(j, this.G)) {
                    if (this.G[j]) {
                        return null;
                    }
                    this.G[j] = true;
                    changed = true;
                }
                j = (j + 1);
            }
        }
        if (this.Bcheck(this.G)) {
            this.done = true;
        } else {
            let next_ = this.nextMincut(this.G);
            if ((next_ === null)) {
                return null;
            }
            this.G = next_;
        }
    }
    return this.G;
  }

  Bcheck(G) {
    return true;
  }

  forbiddenForB(j, G) {
    return false;
  }

  nextMincut(G) {
    return G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPMincut;

if (typeof require !== 'undefined' && require.main === module) {
    const G = [false, false, false, false, false, false, false, false];
    const prog = new LLPMincut();
    const result = prog.LLPMincut(G);
    console.log(JSON.stringify(result));
}
