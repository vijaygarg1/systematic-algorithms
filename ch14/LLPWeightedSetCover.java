// LLP-WeightedSetCover: primal-dual f-approximation for weighted set
// cover, matching bxx-approximate.tex Algorithm LLP-WeightedSetCover
// (fig:llp-wsc). G[x] is the price on element x; a set s is tight when
// its incident prices saturate w(s); an element is slack when it lies
// in no tight set; slackMin(x) further requires x to be lex-minimal
// among every slack element sharing a set with it. Advancing raises
// x's price by the smallest remaining budget among the sets containing
// it, tightening at least one of them.
//
// The guard names its own range, x in [0..u-1], because x ranges over
// ELEMENTS while the bound the compiler would otherwise infer comes from
// the first 1D array parameter (w.length, the number of SETS) -- a
// different count whenever sets and elements aren't equinumerous.
//
// S, w and G are program fields, so the aux methods read them directly
// rather than taking them as parameters.
//
// Three cached quantities keep the predicate off the critical path. The set
// prices sp[] are maintained by the advance, so setTight is O(1) instead of
// an O(u) sum; slack[] records which elements lie in no tight set; and
// minSlackIn[s] is the smallest slack element of s, which turns "no slack
// element below x shares a set with x" into a scan of the sets containing x
// rather than a scan over all smaller elements, each re-deciding slackness.
// All three are rebuilt only after an advance has moved a price.
//
// PRECONDITION: every element must lie in at least one set, or no cover
// exists at all. The guard enforces it rather than assuming it, because
// an uncoverable element is slack forever -- it lies in no tight set, so
// nothing can ever make it non-slack -- and the fixpoint marks a sweep
// as progress whenever a rule fires, not when G actually moves. Without
// the covered(x) conjunct below, one such element makes this program
// raise its price forever and never terminate. With it, an uncoverable
// element simply keeps price 0 and the sets returned cover everything
// that can be covered.

import java.util.*;

public class LLPWeightedSetCover {
  int n;
  int[][] mem;
  int[][] inc;
  double[] w;
  int m;
  int u;
  double[] G;
  double[] sp;
  boolean[] slack;
  int[] minSlackIn;
  boolean fresh;
  int x;

  private boolean _forbidden0(int x) {
    if (!(covered(x))) return false;
    if (!(slackMin(x))) return false;
    return true;
  }

  private void _advance0() {
    double step = minRemainingBudget(x);
    G[x] = (G[x] + step);
    for (int t = 0; t < inc[x].length; t++) {
      {
        sp[inc[x][t]] = (sp[inc[x][t]] + step);
      }
    }
    fresh = false;
  }

  public boolean[] LLPWeightedSetCover(int[][] mem, int[][] inc, double[] w) {
    this.mem = mem;
    this.inc = inc;
    this.w = w;
    this.n = w.length;
    this.m = w.length;
    this.u = inc.length;
    this.G = new double[u];
    this.sp = new double[m];
    this.slack = new boolean[u];
    this.minSlackIn = new int[m];
    this.fresh = false;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int x = 0; x < u; x++) {
          if (_forbidden0(x)) {
            this.x = x; _advance0();
            changed = true;
          }
        }
      }
    }
    boolean[] C = new boolean[m];
    for (int s = 0; s < m; s++) {
      C[s] = setTight(s);
    }
    return C;
  }

  public boolean covered(int x) {
    return (inc[x].length > 0);
  }

  public boolean setTight(int s) {
    return (sp[s] >= w[s]);
  }

  public void refresh() {
    if ((!fresh)) {
      for (int e = 0; e < u; e++) {
        slack[e] = isSlack(e);
      }
      for (int s = 0; s < m; s++) {
        minSlackIn[s] = firstSlackOf(s);
      }
      fresh = true;
    }
  }

  public int firstSlackOf(int s) {
    int k = 0;
    while ((k < mem[s].length)) {
      if (slack[mem[s][k]]) {
        return mem[s][k];
      }
      k = (k + 1);
    }
    return u;
  }

  public boolean isSlack(int e) {
    boolean t1 = false;
    for (int s : inc[e]) {
      if (setTight(s)) { t1 = true; break; }
    }
    return (!t1);
  }

  public double minRemainingBudget(int e) {
    double best = Double.POSITIVE_INFINITY;
    for (int s : inc[e]) {
      {
        double budget = (w[s] - sp[s]);
        if ((budget < best)) {
          best = budget;
        }
      }
    }
    return best;
  }

  public boolean slackMin(int x) {
    refresh();
    if ((!slack[x])) {
      return false;
    }
    int k = 0;
    while ((k < inc[x].length)) {
      if ((minSlackIn[inc[x][k]] < x)) {
        return false;
      }
      k = (k + 1);
    }
    return true;
  }

  public static void main(String[] args) {
    int[][] mem = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] inc = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    LLPWeightedSetCover prog = new LLPWeightedSetCover();
    boolean[] result = prog.LLPWeightedSetCover(mem, inc, w);
    System.out.println(Arrays.toString(result));
  }
}