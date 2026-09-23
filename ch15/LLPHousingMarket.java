// LLP housing market: forbidden when agent j is not in the submatching
// but wishes for a house that is in the submatching; advance increments proposal.

import java.util.*;

public class LLPHousingMarket {
  int n;
  int[][] pref;
  int n;
  int[] G;

  private boolean _forbidden0(int j) {
    if (inSubmatching(j, G, pref)) return false;
    if (!(wishInSubmatching(j, G, pref))) return false;
    return true;
  }

  private void _advance0(int j) {
    G[j] = (G[j] + 1);
  }

  public int[] LLPHousingMarket(int[][] pref) {
    this.pref = pref;
    this.n = pref.length;
    this.G = new int[n];
    for (int i = 0; i < n; i++) this.G[i] = new int[n];
    for (int k = 0; k < n; k++) {
      G[k] = 0;
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            _advance0(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean inSubmatching(int j, int[] G, int[][] pref) {
    int n = G.length;
    int target = pref[j][G[j]];
    int i = 0;
    while ((i < n)) {
      if (((i != j) && (pref[i][G[i]] == target))) {
        return false;
      }
      i = (i + 1);
    }
    return true;
  }

  public boolean wishInSubmatching(int j, int[] G, int[][] pref) {
    int n = G.length;
    int wish = pref[j][G[j]];
    int i = 0;
    while ((i < n)) {
      if (((pref[i][G[i]] == wish) && inSubmatching(i, G, pref))) {
        return true;
      }
      i = (i + 1);
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