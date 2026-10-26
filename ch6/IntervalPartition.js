/*
 * IntervalPartition (bxx-greedy.tex, algo:interval-partition): for each
 * interval i (sorted by start time), reuse the earliest-free room via a
 * min-priority-queue Q of (finishTime, roomNumber) pairs -- extract-min
 * when Q is non-empty and its minimum finish time is <= s[i], else open
 * a new room -- matching the book's binary-heap algorithm exactly, not
 *
 * Auto-generated from IntervalPartition.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class IntervalPartition {
  partition(s, f) {
    this.s = s;
    this.f = f;
    this.n = this.s.length;
    this.G = new Array(this.n).fill(0);
    this.Q = [];
    this.numRooms = 0;
    for (let j = 0; j <= ((this.n - 1)); j++) {
        let r = (0 - 1);
        if (((!(this.Q.length === 0)) && (this.Q[0][0] <= this.s[j]))) {
            r = this.Q[0][1];
            this._heapPop(this.Q);
        } else {
            r = this.numRooms;
            this.numRooms = (this.numRooms + 1);
        }
        this.G[j] = (r + 1);
        this._heapPush(this.Q, this.f[j], r);
    }
    return this.G;
  }

  // Binary min-heap of [key, value] pairs, ordered by key and
  // then by value -- the order the Java PriorityQueue<int[]>
  // comparator gives. JavaScript has no heap in its standard
  // library, so the three operations the program uses are
  // carried with it.
  _heapLess(a, b) {
    return a[0] !== b[0] ? a[0] < b[0] : a[1] < b[1];
  }

  _heapPush(h, k, v) {
    h.push([k, v]);
    let i = h.length - 1;
    while (i > 0) {
      const p = Math.trunc((i - 1) / 2);
      if (!this._heapLess(h[i], h[p])) break;
      const t = h[i]; h[i] = h[p]; h[p] = t;
      i = p;
    }
  }

  _heapPop(h) {
    const top = h[0];
    const last = h.pop();
    if (h.length > 0) {
      h[0] = last;
      let i = 0;
      while (true) {
        const l = 2 * i + 1, r = l + 1;
        let m = i;
        if (l < h.length && this._heapLess(h[l], h[m])) m = l;
        if (r < h.length && this._heapLess(h[r], h[m])) m = r;
        if (m === i) break;
        const t = h[i]; h[i] = h[m]; h[m] = t;
        i = m;
      }
    }
    return top;
  }
}

if (typeof module !== 'undefined') module.exports = IntervalPartition;

if (typeof require !== 'undefined' && require.main === module) {
    const s = [0, 0, 1, 2];
    const f = [0, 0, 1, 1];
    const prog = new IntervalPartition();
    const result = prog.partition(s, f);
    console.log(JSON.stringify(result));
}
