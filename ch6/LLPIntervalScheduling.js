/*
 * LLP-IntervalScheduling, as bxx-greedy.tex's Algorithm
 * LLP-IntervalScheduling states it. Jobs sit in a doubly linked list;
 * prev and next are the previous and next node.
 *
 * Forbidden (j in [1..n]): !G[j] and f[prev[j]] <= s[j]
 * Advance                : G[j] := true
 * &&
 * Forbidden (j in [1..n]): (j>1) and (next[prev[j]] = j)
 * and G[prev[j]] and s[j] < f[prev[j]]
 * Advance                : next[prev[j]] := next[j]     // delete from
 * prev[next[j]] := prev[j]     // linked list
 *
 * next[prev[j]] = j is the "j is still in the list" test. Without it the
 * second rule never falsifies its own guard -- deleting j leaves prev[j]
 * untouched, so G[prev[j]] and s[j] < f[prev[j]] both stay true and j is
 * forbidden forever. (Verified: the version without it does not
 * terminate.) It is also what makes the book's own claim true, that
 * every job is touched at most twice.
 *
 * Rule 1 needs no index bound: G[0] is true from the start, so !G[j] is
 * false at j = 0 and the guard short-circuits before reaching f[prev[0]].
 *
 * The advance guards prev[next[j]] because next[n-1] is n, one past the
 * last slot: the book's list has a tail sentinel there, this one does not.
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
    this.prev = new Array(this.n).fill(0);
    this.next_ = new Array(this.n).fill(0);
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.prev[i] = (i - 1);
    }
    for (let i = 0; i <= ((this.n - 1)); i++) {
        this.next_[i] = (i + 1);
    }
    this.G[0] = true;
    let _changed = true;
    while (_changed) {
        _changed = false;
        for (let j = 0; j <= ((this.n - 1)); j++) {
            if (((!this.G[j]) && (this.f[this.prev[j]] <= this.s[j]))) {
                this.G[j] = true;
                _changed = true;
            }
            if (((((j > 0) && (this.next_[this.prev[j]] === j)) && this.G[this.prev[j]]) && (this.s[j] < this.f[this.prev[j]]))) {
                this.next_[this.prev[j]] = this.next_[j];
                if ((this.next_[j] < this.n)) {
                    this.prev[this.next_[j]] = this.prev[j];
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
