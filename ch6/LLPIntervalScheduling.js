/*
 * LLP-IntervalScheduling (bxx-greedy.tex, fig:activity): jobs sorted by
 * finish time, held in a doubly linked list (prevArr/nextArr). Job 0 is
 * always fixed initially. The book's two rules are written here as two
 * forbidden/advance clauses joined by `&&`, so they share one fixpoint:
 * every job is tested against both rules on each sweep. Rule 1 selects a
 * job whose list-predecessor has already finished by s[j]; rule 2 deletes
 * a job that its (selected) predecessor still overlaps, in O(1), via
 * next[prev[j]] := next[j]; prev[next[j]] := prev[j]. deleted[] records
 * the list removal that the book expresses by unlinking the node.
 *
 * Auto-generated from LLPIntervalScheduling.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class LLPIntervalScheduling {
  LLPIntervalScheduling(s, f) {
    this.s = s;
    this.f = f;
    this.n = this.s.length;
    this.G = new Array(this.n).fill(false);
    this.deleted = new Array(this.n).fill(false);
    this.prevArr = new Array(this.n).fill(0);
    this.nextArr = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.prevArr[i] = (i - 1);
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.nextArr[i] = (i + 1);
    }
    this.G[0] = true;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((((j > 0) && (!this.deleted[j])) && (!this.G[j])) && (this.f[this.prevArr[j]] <= this.s[j]))) {
                this.G[j] = true;
                _changed = true;
            }
            if (((((j > 0) && (!this.deleted[j])) && this.G[this.prevArr[j]]) && (this.s[j] < this.f[this.prevArr[j]]))) {
                this.deleted[j] = true;
                let p = this.prevArr[j];
                let nx = this.nextArr[j];
                this.nextArr[p] = nx;
                if ((nx < this.n)) {
                    this.prevArr[nx] = p;
                }
                _changed = true;
            }
        }
    }
    return this.G;
  }
}

if (typeof module !== 'undefined') module.exports = LLPIntervalScheduling;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const prog = new LLPIntervalScheduling();
    const result = prog.LLPIntervalScheduling(s, f);
    console.log(JSON.stringify(result));
}
