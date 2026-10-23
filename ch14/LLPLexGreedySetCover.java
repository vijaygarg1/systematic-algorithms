// LLP parallel H_n-approximation for Set Cover: pick every set that
// maximises coverage among its neighbours and is lex-minimal among ties.
// A neighbour of j is another unpicked set sharing an uncovered element
// with j; j is forbidden (i.e. should be picked) when it still covers
// something and no neighbour outranks it.
//
// The predicate is tested without comparing set pairs. For each uncovered
// element keep best[e], the unpicked set covering the most uncovered
// elements that contains e (lowest index on ties); then j outranks every
// neighbour exactly when best[e] = j for every uncovered e in s_j, since
// the neighbours of j are precisely the unpicked sets sharing such an e.
// The coverage counts cnt[j] = |s_j ∩ R| and the covered[] flags are
// maintained by the advance, so coverage is read in O(1) instead of being
// recomputed -- it used to cost an O(m) scan per element, inside an O(u)
// scan per set, inside an O(m) scan over neighbours.

import java.util.*;

public class LLPLexGreedySetCover {
  int n;
  int[][] S;
  int m;
  int u;
  boolean[] G;
  boolean[] covered;
  int[] cnt;
  int[] best;
  boolean fresh;
  int j;

  private boolean _forbidden0(int j) {
    if (!(isLexMaxCov(j))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
    int e = 0;
    while ((e < u)) {
      if (((S[j][e] == 1) && (!covered[e]))) {
        covered[e] = true;
        int t = 0;
        while ((t < m)) {
          if ((S[t][e] == 1)) {
            cnt[t] = (cnt[t] - 1);
          }
          t = (t + 1);
        }
      }
      e = (e + 1);
    }
    fresh = false;
  }

  public boolean[] LLPLexGreedySetCover(int[][] S) {
    this.S = S;
    this.n = S.length;
    this.m = S.length;
    this.u = S[0].length;
    this.G = new boolean[n];
    this.covered = new boolean[u];
    this.cnt = new int[m];
    this.best = new int[u];
    this.fresh = false;
    for (int s = 0; s < m; s++) {
      int t1 = 0;
      for (int e = 0; e < u; e++) {
        if ((S[s][e] == 1)) t1++;
      }
      cnt[s] = t1;
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean isLexMaxCov(int j) {
    if (G[j]) {
      return false;
    }
    refresh();
    if ((cnt[j] <= 0)) {
      return false;
    }
    int e = 0;
    while ((e < u)) {
      if ((((S[j][e] == 1) && (!covered[e])) && (best[e] != j))) {
        return false;
      }
      e = (e + 1);
    }
    return true;
  }

  public void refresh() {
    if ((!fresh)) {
      for (int e = 0; e < u; e++) {
        best[e] = bestAt(e);
      }
      fresh = true;
    }
  }

  public int bestAt(int e) {
    int win = (0 - 1);
    int s = 0;
    while ((s < m)) {
      if (((S[s][e] == 1) && (!G[s]))) {
        if ((win < 0)) {
          win = s;
        } else {
          if ((cnt[s] > cnt[win])) {
            win = s;
          }
        }
      }
      s = (s + 1);
    }
    return win;
  }

  public static void main(String[] args) {
    int[][] S = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPLexGreedySetCover prog = new LLPLexGreedySetCover();
    boolean[] result = prog.LLPLexGreedySetCover(S);
    System.out.println(Arrays.toString(result));
  }
}