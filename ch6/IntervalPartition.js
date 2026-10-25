/*
 * Interval partitioning, as bxx-greedy.tex's Algorithm IntervalPartition
 * states it:
 *
 * var Q: min-priority queue of (finishTime, roomNumber), initially empty
 * numRooms: int, initially 0
 * for i := 1 to n:
 * if Q is not empty and Q.min().finishTime <= s[i]:
 * room[i] := Q.extract-min().roomNumber    // reuse earliest-free room
 * else:
 * numRooms := numRooms + 1                 // allocate a new room
 * room[i] := numRooms
 * Q.insert(f[i], room[i])                    // busy until f[i]
 * return room, numRooms
 *
 * Intervals arrive sorted by start time. The heap is keyed by finish
 * time, so its minimum is the room that frees up earliest.
 *
 * The loop is `for`, not `forall`: Q and numRooms both carry from one
 * iteration to the next.
 *
 * The book returns the pair (room, numRooms). LL returns one value, so
 * room is returned; numRooms is max(room) and so is recoverable from it.
 * Rooms are numbered from 1, as the book numbers them.
 *
 * Auto-generated from IntervalPartition.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class IntervalPartition {
  IntervalPartition(s, f) {
    this.s = s;
    this.f = f;
    this.n = this.s.length;
    this.room = new Array(this.n).fill(0);
    this.Q = [];
    this.numRooms = 0;
    for (let i = 0; i <= ((this.n - 1)); i++) {
        if (((!(this.Q.length === 0)) && (this.Q[0][0] <= this.s[i]))) {
            this.room[i] = this.Q[0][1];
            this._heapPop(this.Q);
        } else {
            this.numRooms = (this.numRooms + 1);
            this.room[i] = this.numRooms;
        }
        this._heapPush(this.Q, this.f[i], this.room[i]);
    }
    return this.room;
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
    const result = prog.IntervalPartition(s, f);
    console.log(JSON.stringify(result));
}
