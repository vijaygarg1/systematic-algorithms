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
//
// The instance is a pair of incidence lists: mem[s] holds the elements of
// set s and inc[e] holds the sets containing element e. Neither is an
// m x u matrix, so the whole instance is O(N) -- N being the number of
// (set, element) incidences -- rather than Theta(m*u). Every pass below
// walks one of these lists, so each costs O(N) and not O(m*u).
//
// That matters most for best[], which is rebuilt after every advance, and
// there are as many advances as sets picked: rebuilding it by scanning a
// whole matrix each time made the algorithm O(m*u) per advance, a full
// factor of n more than the chapter's O(N*min(m,n)) bound.

import java.util.*;

public class LLPLexGreedySetCover {
  int n;
  int[][] mem;
  int[][] inc;
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
    int k = 0;
    while ((k < mem[j].length)) {
      int e = mem[j][k];
      if ((!covered[e])) {
        covered[e] = true;
        for (int t = 0; t < inc[e].length; t++) {
          {
            cnt[inc[e][t]] = (cnt[inc[e][t]] - 1);
          }
        }
      }
      k = (k + 1);
    }
    fresh = false;
  }

  public boolean[] LLPLexGreedySetCover(int[][] mem, int[][] inc) {
    this.mem = mem;
    this.inc = inc;
    this.n = mem.length;
    this.m = mem.length;
    this.u = inc.length;
    this.G = new boolean[n];
    this.covered = new boolean[u];
    this.cnt = new int[m];
    this.best = new int[u];
    this.fresh = false;
    for (int s = 0; s < m; s++) {
      cnt[s] = mem[s].length;
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
    int k = 0;
    while ((k < mem[j].length)) {
      int e = mem[j][k];
      if (((!covered[e]) && (best[e] != j))) {
        return false;
      }
      k = (k + 1);
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
    for (int k = 0; k < inc[e].length; k++) {
      {
        int s = inc[e][k];
        if ((!G[s])) {
          if ((win < 0)) {
            win = s;
          } else {
            if ((cnt[s] > cnt[win])) {
              win = s;
            }
          }
        }
      }
    }
    return win;
  }

  public static void main(String[] args) {
    int[][] mem = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] inc = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    LLPLexGreedySetCover prog = new LLPLexGreedySetCover();
    boolean[] result = prog.LLPLexGreedySetCover(mem, inc);
    System.out.println(Arrays.toString(result));
  }
}