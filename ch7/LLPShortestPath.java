// LLP-ShortestPath: Dijkstra-style LLP with priority-queue scheduling.
// always: parent[j][i] = (i in pre[j]) && (G[j] >= G[i] + w[i][j])
// always: fixed[j] = (j == s) || (exists i : parent[j][i] && fixed[i])

import java.util.*;

public class LLPShortestPath {
  int n;
  int[][] pre;
  int[][] w;
  int[] G;
  boolean[] fixed;

  private boolean _forbidden0(int j) {
    if (fixed[j]) return false;
    return true;
  }

  private void _advance0(int j) {
    G[j] = minCrossCut(j, pre, w, G, fixed);
    fixed[j] = true;
  }

  public void LLPShortestPath(int[][] pre, int[][] w) {
    this.pre = pre;
    this.w = w;
    this.G = new int[n];
    this.fixed = new boolean[n];
    fixed[s] = true;
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
  }

  public int minCrossCut(int j, int[][] pre, int[][] w, int[] G, boolean[] fixed) {
    int best = Integer.MAX_VALUE;
    int k = 0;
    while ((k < pre[j].length)) {
      int i = pre[j][k];
      if (fixed[i]) {
        int d = (G[i] + w[i][j]);
        if ((d < best)) {
          best = d;
        }
      }
      k = (k + 1);
    }
    return best;
  }

  public static void main(String[] args) {
    int[][] pre = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[][] w = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    LLPShortestPath prog = new LLPShortestPath();
    prog.LLPShortestPath(pre, w);
    System.out.println(Arrays.deepToString(pre));
    System.out.println(Arrays.deepToString(w));
  }
}