// LLP housing market, matching bxx-housing.tex Algorithm
// LLP-Housing-Market-Algorithm. Houses are identified with agent
// indices (agent j initially owns house j). wish(i) is the house agent
// i currently proposes for. S(G), the largest submatching, is the set
// of agents lying on a cycle of the wish functional graph (i -> wish(i))

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