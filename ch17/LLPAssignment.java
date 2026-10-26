// LLP-Assignment (bxx-assignment.tex, algo:llp-assignment): forbidden when
// item j belongs to an inclusion-minimal overdemanded set J -- found by
// alternating-path reachability from an unmatched bidder, the standard
// witness for a violated Hall condition. Advance raises EVERY item in J by
// one shared slack jump, delta := min over bidders demanding into J of

import java.util.*;

public class LLPAssignment {
  int n;
  int[][] v;
  int m;
  int[] G;
  int[] partner;
  boolean[] itemInJ;
  boolean[] bidderInB;
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
    this.partner = new int[n];
    this.itemInJ = new boolean[n];
    this.bidderInB = new boolean[m];
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
    if (fresh) {
      return;
    }
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
      tryMatch(b, new boolean[n]);
    }
    int u = firstUnmatched();
    if ((u < m)) {
      reach(u);
    }
    fresh = true;
  }

  public void raiseJ() {
    int delta = Integer.MAX_VALUE;
    for (int b = 0; b < m; b++) {
      {
        if (bidderInB[b]) {
          int drop = (bestSurplus(b) - bestOutside(b));
          if ((drop < delta)) {
            delta = drop;
          }
        }
      }
    }
    for (int i = 0; i < n; i++) {
      if (itemInJ[i]) {
        G[i] = (G[i] + delta);
      }
    }
    fresh = false;
  }

  public int firstUnmatched() {
    for (int b = 0; b < m; b++) {
      {
        if ((!_q1(b))) {
          return b;
        }
      }
    }
    return m;
  }

  public int bestSurplus(int b) {
    int t2 = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int _v = (v[b][i] - G[i]);
      if (_v > t2) t2 = _v;
    }
    return t2;
  }

  public int bestOutside(int b) {
    int best = (0 - Integer.MAX_VALUE);
    for (int i = 0; i < n; i++) {
      {
        if (((!itemInJ[i]) && ((v[b][i] - G[i]) > best))) {
          best = (v[b][i] - G[i]);
        }
      }
    }
    return best;
  }

  public void reach(int b) {
    if (bidderInB[b]) {
      return;
    }
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

  private boolean _q1(int b) {
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