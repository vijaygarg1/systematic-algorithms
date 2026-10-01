// LLP housing market, matching bxx-housing.tex Algorithm
// LLP-Housing-Market-Algorithm. Houses are identified with agent
// indices (agent j initially owns house j). wish(i) is the house agent
// i currently proposes for. S(G), the largest submatching, is the set
// of agents lying on a cycle of the wish functional graph (i -> wish(i))
// -- exactly the agents who can all trade among themselves. forbidden:
// agent j is not itself in S(G) but wishes for a house held by an agent
// who is; advance moves j to its next preference.
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
  int j;

  private boolean _forbidden0(int j) {
    if (inSubmatching(j, G, pref)) return false;
    if (!(inSubmatching(wish(j, G, pref), G, pref))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = (G[j] + 1);
  }

  public int[] LLPHousingMarket(int[][] pref) {
    this.pref = pref;
    this.n = pref.length;
    this.G = new int[n];
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
    int n = G.length;
    int cur = wish(j, G, pref);
    int steps = 1;
    while ((steps <= n)) {
      if ((cur == j)) {
        return true;
      }
      cur = wish(cur, G, pref);
      steps = (steps + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] pref = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    LLPHousingMarket prog = new LLPHousingMarket();
    int[] result = prog.LLPHousingMarket(pref);
    System.out.println(Arrays.toString(result));
  }
}