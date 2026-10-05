// LLP-Delta-Stepping: bucket-based relaxation with light/heavy edge phases.
// This algorithm has complex control flow (repeat/until, set comprehensions)
// beyond the standard forbidden/advance pattern.
// The forbidden predicates are:
// forbidden_L(v): exists (u,v) in E_light : G[u] + w[u][v] < G[v]
// forbidden_H(v): exists (u,v) in E_heavy : G[u] + w[u][v] < G[v]
//
// Source vertex `s`: missing from the parameter list entirely in an
// earlier version of this file (a compile error -- `G[s] = 0;` referenced
// an undefined variable), caught by validate-site.py's Java-compile check.
// Added as an explicit parameter, matching the sibling algorithm in this
// same chapter/family, LLPShortestPath, which takes the identical
// `int s` for the same role (its own initial relaxed vertex).

import java.util.*;

public class LLPDeltaStepping {
  public void LLPDeltaStepping(int[][] pre, int[][] w, boolean[][] isLight, int[] G, int delta, int s) {
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
    int[][] pre = new int[][] {{}, {0}, {0, 1}, {0, 1, 2}, {0, 1, 2, 3}, {0, 1, 2, 3, 4}, {0, 1, 2, 3, 4, 5}, {0, 1, 2, 3, 4, 5, 6}};
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    boolean[][] isLight = new boolean[][] {{true, false, false, false, false, false, false, false}, {false, true, false, false, false, false, false, false}, {false, false, true, false, false, false, false, false}, {false, false, false, true, false, false, false, false}, {false, false, false, false, true, false, false, false}, {false, false, false, false, false, true, false, false}, {false, false, false, false, false, false, true, false}, {false, false, false, false, false, false, false, true}};
    int[] G = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    int delta = 0;
    LLPDeltaStepping prog = new LLPDeltaStepping();
    prog.LLPDeltaStepping(pre, w, isLight, G, delta, 0);
    System.out.println(Arrays.deepToString(pre));
    System.out.println(Arrays.deepToString(w));
    System.out.println(Arrays.deepToString(isLight));
    System.out.println(Arrays.toString(G));
  }
}