// LLP-IntervalPartition (bxx-greedy.tex, fig:intervalPartition): assign
// each course j to the least free room not used by any overlapping
// earlier course in pre[j]; advance fixes j once all of its pre-set is
// fixed. The least-free-room computation uses a min-heap keyed by the
// occupied room numbers in pre[j] (extract-min repeatedly to find the
// smallest room number not present), matching the book's heap-based
// advance step, not a linear re-scan of pre[j] for every candidate room
// number. Uses the heap<int> builtin (-> java.util.PriorityQueue<int[]>)
// instead of hand-rolled heapPush/heapPop/heapify, matching the
// hand-written Python (heapq) and Rust (BinaryHeap) versions of this
// same program, which already lean on their standard library's heap.

import java.util.*;

public class LLPIntervalPartition {
  int n;
  int[][] pre;
  int[] G;
  boolean[] fixed;
  int j;

  private boolean forbidden(int j) {
    if (fixed[j]) return false;
    for (int i : pre[j]) if (!fixed[i]) return false;
    return true;
  }

  private void advance() {
    G[j] = leastFreeRoom(j);
    fixed[j] = true;
  }

  public int[] LLPIntervalPartition(int[][] pre) {
    this.pre = pre;
    this.n = pre.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = 1;
    this.fixed = new boolean[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public int leastFreeRoom(int j) {
    java.util.PriorityQueue<int[]> h = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    for (int i : pre[j]) {
      h.add(new int[]{G[i], G[i]});
    }
    int r = 1;
    while (((!h.isEmpty()) && (h.peek()[1] == r))) {
      h.poll();
      r = (r + 1);
    }
    return r;
  }

  public static void main(String[] args) {
    // No runnable example: LLPIntervalPartition's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}