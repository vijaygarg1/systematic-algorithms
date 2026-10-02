// LLP-Delta-Stepping: bucket-based relaxation with light/heavy edge phases.
// This algorithm has complex control flow (repeat/until, set comprehensions)
// beyond the standard forbidden/advance pattern.
// The forbidden predicates are:
// forbidden_L(v): exists (u,v) in E_light : G[u] + w[u][v] < G[v]
// forbidden_H(v): exists (u,v) in E_heavy : G[u] + w[u][v] < G[v]

import java.util.*;

public class LLPDeltaStepping {
  public void LLPDeltaStepping(int[][] pre, int[][] w, boolean[][] isLight, int[] G, int delta) {
    G[s] = 0;
    boolean changed = true;
    while (changed) {
      changed = false;
      int j = 0;
      while ((j < G.length)) {
        if (relaxLight(j, pre, w, isLight, G, delta)) {
          changed = true;
        }
        j = (j + 1);
      }
      j = 0;
      while ((j < G.length)) {
        if (relaxHeavy(j, pre, w, isLight, G, delta)) {
          changed = true;
        }
        j = (j + 1);
      }
    }
  }

  public boolean relaxLight(int v, int[][] pre, int[][] w, boolean[][] isLight, int[] G, int delta) {
    boolean relaxed = false;
    int k = 0;
    while ((k < pre[v].length)) {
      int u = pre[v][k];
      if ((isLight[u][v] && ((G[u] + w[u][v]) < G[v]))) {
        G[v] = (G[u] + w[u][v]);
        relaxed = true;
      }
      k = (k + 1);
    }
    return relaxed;
  }

  public boolean relaxHeavy(int v, int[][] pre, int[][] w, boolean[][] isLight, int[] G, int delta) {
    boolean relaxed = false;
    int k = 0;
    while ((k < pre[v].length)) {
      int u = pre[v][k];
      if (((!isLight[u][v]) && ((G[u] + w[u][v]) < G[v]))) {
        G[v] = (G[u] + w[u][v]);
        relaxed = true;
      }
      k = (k + 1);
    }
    return relaxed;
  }

  public static void main(String[] args) {
    int[][] pre = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[][] w = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    boolean[][] isLight = new boolean[][] {{true, false, false}, {false, true, false}, {false, false, true}};
    int[] G = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int delta = 0;
    LLPDeltaStepping prog = new LLPDeltaStepping();
    prog.LLPDeltaStepping(pre, w, isLight, G, delta);
    System.out.println(Arrays.deepToString(pre));
    System.out.println(Arrays.deepToString(w));
    System.out.println(Arrays.deepToString(isLight));
    System.out.println(Arrays.toString(G));
  }
}