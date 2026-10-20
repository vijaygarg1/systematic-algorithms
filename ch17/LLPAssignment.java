// LLP-Assignment (bxx-assignment.tex, algo:llp-assignment): forbidden when
// item j belongs to an inclusion-minimal overdemanded set J (found via
// alternating-path reachability from an unmatched bidder, the standard witness
// for a violated Hall's condition); advance raises EVERY item in J by one
// shared slack-jump delta := min over bidders demanding into J of [best
// surplus - best surplus using an item outside J] -- the book's
// "G[j] := G[j]+delta for every j in J", not a per-item unit raise.
//
// J depends only on G, so it is computed ONCE per value of G and held in the
// fields itemInJ/bidderInB: the forbidden test refreshes it when stale and
// then just reads itemInJ[j], and the advance consumes the same arrays and
// marks them stale. The previous version recomputed the whole matching and
// reachability inside every forbidden(j) call -- n times per sweep -- and
// once more inside the advance.

import java.util.*;

public class LLPAssignment {
  int n;
  int[][] v;
  int m;
  int[] G;
  boolean[] itemInJ;
  boolean[] bidderInB;
  int[] partner;
  boolean fresh;
  int j;

  private boolean forbidden(int j) {
    if (!(overdemanded(j))) return false;
    return true;
  }

  private void advance() {
    raiseJ();
  }

  public int[] LLPAssignment(int[][] v) {
    this.v = v;
    this.n = v[0].length;
    this.m = v.length;
    this.G = new int[n];
    this.itemInJ = new boolean[n];
    this.bidderInB = new boolean[m];
    this.partner = new int[n];
    this.fresh = false;
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

  public boolean overdemanded(int j) {
    refresh();
    return itemInJ[j];
  }

  public void refresh() {
    if ((!fresh)) {
      for (int i = 0; i < n; i++) {
        {
          itemInJ[i] = false;
          partner[i] = (0 - 1);
        }
      }
      for (int b = 0; b < m; b++) {
        bidderInB[b] = false;
      }
      for (int b = 0; b < m; b++) {
        {
          boolean[] seen = new boolean[n];
          tryMatch(b, seen);
        }
      }
      int u = firstUnmatched();
      if ((u >= 0)) {
        reach(u);
      }
      fresh = true;
    }
  }

  public void raiseJ() {
    int t1 = Integer.MAX_VALUE;
    for (int b = 0; b < m; b++) {
      if (!(bidderInB[b])) continue;
      int _v = (bestSurplus(b) - bestOutside(b));
      if (_v < t1) t1 = _v;
    }
    int delta = t1;
    for (int i = 0; i < n; i++) {
      if (itemInJ[i]) {
        G[i] = (G[i] + delta);
      }
    }
    fresh = false;
  }

  public int firstUnmatched() {
    int b = 0;
    while ((b < m)) {
      if ((!_q2(b))) {
        return b;
      }
      b = (b + 1);
    }
    return (0 - 1);
  }

  public int bestSurplus(int b) {
    int t3 = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int _v = (v[b][i] - G[i]);
      if (_v > t3) t3 = _v;
    }
    return t3;
  }

  public int bestOutside(int b) {
    int t4 = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      if (!((!itemInJ[i]))) continue;
      int _v = (v[b][i] - G[i]);
      if (_v > t4) t4 = _v;
    }
    return t4;
  }

  public void reach(int b) {
    if ((!bidderInB[b])) {
      bidderInB[b] = true;
      int best = bestSurplus(b);
      for (int i = 0; i < n; i++) {
        if ((((v[b][i] - G[i]) == best) && (!itemInJ[i]))) {
          itemInJ[i] = true;
          if ((partner[i] != (0 - 1))) {
            reach(partner[i]);
          }
        }
      }
    }
  }

  public boolean tryMatch(int b, boolean[] seen) {
    int best = bestSurplus(b);
    int i = 0;
    while ((i < n)) {
      if ((((v[b][i] - G[i]) == best) && (!seen[i]))) {
        seen[i] = true;
        if (((partner[i] == (0 - 1)) || tryMatch(partner[i], seen))) {
          partner[i] = b;
          return true;
        }
      }
      i = (i + 1);
    }
    return false;
  }

  private boolean _q2(int b) {
    for (int i = 0; i < n; i++) {
      if ((partner[i] == b)) return true;
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] v = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPAssignment prog = new LLPAssignment();
    int[] result = prog.LLPAssignment(v);
    System.out.println(Arrays.toString(result));
  }
}