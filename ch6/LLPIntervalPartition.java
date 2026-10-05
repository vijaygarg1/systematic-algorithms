// LLP-IntervalPartition (bxx-greedy.tex, fig:intervalPartition): assign
// each course j to the least free room not used by any overlapping
// earlier course in pre[j]; advance fixes j once all of its pre-set is
// fixed. The least-free-room computation uses a min-heap keyed by the
// occupied room numbers in pre[j] (extract-min repeatedly to find the
// smallest room number not present), matching the book's heap-based
// advance step, not a linear re-scan of pre[j] for every candidate room
// number.

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
    int k = pre[j].length;
    int[] heap = new int[k];
    int heapSize = 0;
    for (int i : pre[j]) {
      heapSize = heapPush(heap, heapSize, G[i]);
    }
    int r = 1;
    while (((heapSize > 0) && (heap[0] == r))) {
      heapSize = heapPop(heap, heapSize);
      r = (r + 1);
    }
    return r;
  }

  public int heapPush(int[] h, int size, int key) {
    int i = size;
    h[i] = key;
    size = (size + 1);
    while (((i > 0) && (h[((i - 1) / 2)] > h[i]))) {
      int p = ((i - 1) / 2);
      { int tmp = h[i]; h[i] = h[p]; h[p] = tmp; }
      i = p;
    }
    return size;
  }

  public int heapPop(int[] h, int size) {
    size = (size - 1);
    h[0] = h[size];
    heapify(h, 0, size);
    return size;
  }

  public void heapify(int[] h, int i, int size) {
    int smallest = i;
    int left = ((2 * i) + 1);
    int right = ((2 * i) + 2);
    if (((left < size) && (h[left] < h[smallest]))) {
      smallest = left;
    }
    if (((right < size) && (h[right] < h[smallest]))) {
      smallest = right;
    }
    if ((smallest != i)) {
      { int tmp = h[i]; h[i] = h[smallest]; h[smallest] = tmp; }
      heapify(h, smallest, size);
    }
  }

  public static void main(String[] args) {
    // No runnable example: LLPIntervalPartition's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}