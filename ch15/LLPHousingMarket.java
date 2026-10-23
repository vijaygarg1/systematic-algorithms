// LLP housing market, matching bxx-housing.tex Algorithm
// LLP-Housing-Market-Algorithm. Houses are identified with agent
// indices (agent j initially owns house j). wish(i) is the house agent
// i currently proposes for. S(G), the largest submatching, is the set
// of agents lying on a cycle of the wish functional graph (i -> wish(i))
// -- exactly the agents who can all trade among themselves. forbidden:
// agent j is not itself in S(G) but wishes for a house held by an agent
// who is; advance moves j to its next preference.
//
// S(G) is built for the whole market at once, which is what makes the
// round cost O(n) and the algorithm O(n^2), as the chapter states: every
// agent wishes for exactly one house, so the wish graph is functional
// and its cycles are precisely what survives repeatedly discarding the
// agents nobody wishes for. Asking the question one agent at a time
// instead -- following wishes from j until they return to j -- answers
// it in O(n) per agent and so costs O(n^2) per round, a full factor of n
// too much. inS[] is rebuilt only after an advance has moved a wish.
//
// Earlier version of this file (kept for reference, not used): treated
// an agent as "in the submatching" whenever no other agent currently
// wished for the same house -- i.e. current-wish uniqueness -- rather
// than genuine cycle membership in the wish graph. This is a different,
// weaker condition: on the book's own running example (G=[0,0,0,0],
// wishes 0->1, 1->0, 2->0, 3->1, 0-indexed), the old inSubmatching(0,...)
// returned false (since agent 3 also wishes for house 1... texts differ
// per index, see the book's own trace) where the book states agents
// {1,2} (1-indexed) are in S(G) via the mutual wish-cycle 0<->1.
//
// class LLPHousingMarket {
// int[] LLPHousingMarket(int[][] pref) {
// int n = pref.length;
// int[] G = new int[n];
// forall k in [0..n-1] : G[k] = 0;
// forbidden (j) : !inSubmatching(j, G, pref) && wishInSubmatching(j, G, pref) =>
// advance : G[j] = G[j] + 1;
// return G;
// }
//
// boolean inSubmatching(int j, int[] G, int[][] pref) {
// int n = G.length;
// int target = pref[j][G[j]];
// int i = 0;
// while (i < n) {
// if (i != j && pref[i][G[i]] == target) {
// return false;
// };
// i = i + 1;
// };
// return true;
// }
//
// boolean wishInSubmatching(int j, int[] G, int[][] pref) {
// int n = G.length;
// int wish = pref[j][G[j]];
// int i = 0;
// while (i < n) {
// if (pref[i][G[i]] == wish && inSubmatching(i, G, pref)) {
// return true;
// };
// i = i + 1;
// };
// return false;
// }
// }

import java.util.*;

public class LLPHousingMarket {
  int n;
  int[][] pref;
  int[] G;
  boolean[] inS;
  int[] indeg;
  int[] q;
  boolean fresh;
  int j;

  private boolean _forbidden0(int j) {
    if (inSubmatching(j, G, pref)) return false;
    if (!(inSubmatching(wish(j, G, pref), G, pref))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = (G[j] + 1);
    fresh = false;
  }

  public int[] LLPHousingMarket(int[][] pref) {
    this.pref = pref;
    this.n = pref.length;
    this.G = new int[n];
    this.inS = new boolean[n];
    this.indeg = new int[n];
    this.q = new int[n];
    this.fresh = false;
    for (int k = 0; k < n; k++) {
      G[k] = 0;
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

  public int wish(int i, int[] G, int[][] pref) {
    return pref[i][G[i]];
  }

  public boolean inSubmatching(int j, int[] G, int[][] pref) {
    refresh(G, pref);
    return inS[j];
  }

  public void refresh(int[] G, int[][] pref) {
    if (fresh) {
      return;
    }
    for (int i = 0; i < n; i++) {
      {
        inS[i] = true;
        indeg[i] = 0;
      }
    }
    for (int i = 0; i < n; i++) {
      indeg[wish(i, G, pref)] = (indeg[wish(i, G, pref)] + 1);
    }
    int qt = 0;
    for (int i = 0; i < n; i++) {
      {
        if ((indeg[i] == 0)) {
          q[qt] = i;
          qt = (qt + 1);
        }
      }
    }
    int qh = 0;
    while ((qh < qt)) {
      int x = q[qh];
      qh = (qh + 1);
      inS[x] = false;
      int y = wish(x, G, pref);
      indeg[y] = (indeg[y] - 1);
      if ((indeg[y] == 0)) {
        q[qt] = y;
        qt = (qt + 1);
      }
    }
    fresh = true;
  }

  public static void main(String[] args) {
    int[][] pref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPHousingMarket prog = new LLPHousingMarket();
    int[] result = prog.LLPHousingMarket(pref);
    System.out.println(Arrays.toString(result));
  }
}