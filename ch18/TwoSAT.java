// 2-SAT via implication graph and SCC detection (Kosaraju's algorithm).

import java.util.*;

public class TwoSAT {
  public boolean[] TwoSAT(int[] clauseA, int[] clauseB) {
    int m = clauseA.length;
    int n = 0;
    int i = 0;
    while ((i < m)) {
      int a = Math.abs(clauseA[i]);
      int b = Math.abs(clauseB[i]);
      if ((a > n)) {
        n = a;
      }
      if ((b > n)) {
        n = b;
      }
      i = (i + 1);
    }
    int sz = ((2 * n) + 2);
    int[][] adjFwd = new int[sz][sz];
    int[] degFwd = new int[sz];
    int[][] adjRev = new int[sz][sz];
    int[] degRev = new int[sz];
    i = 0;
    while ((i < m)) {
      int a = clauseA[i];
      int b = clauseB[i];
      int notA = neg(a, n);
      int notB = neg(b, n);
      int u1 = litIndex(notA, n);
      int v1 = litIndex(b, n);
      adjFwd[u1][degFwd[u1]] = v1;
      degFwd[u1] = (degFwd[u1] + 1);
      adjRev[v1][degRev[v1]] = u1;
      degRev[v1] = (degRev[v1] + 1);
      int u2 = litIndex(notB, n);
      int v2 = litIndex(a, n);
      adjFwd[u2][degFwd[u2]] = v2;
      degFwd[u2] = (degFwd[u2] + 1);
      adjRev[v2][degRev[v2]] = u2;
      degRev[v2] = (degRev[v2] + 1);
      i = (i + 1);
    }
    int[] comp = new int[sz];
    for (int k = 0; k < sz; k++) {
      comp[k] = (0 - 1);
    }
    int[] order = new int[sz];
    int orderSize = 0;
    boolean[] visited = new boolean[sz];
    int v = 0;
    while ((v < sz)) {
      if ((!visited[v])) {
        orderSize = dfs1(v, adjFwd, degFwd, visited, order, orderSize);
      }
      v = (v + 1);
    }
    int numComp = 0;
    int idx = (orderSize - 1);
    while ((idx >= 0)) {
      int u = order[idx];
      if ((comp[u] < 0)) {
        dfs2(u, adjRev, degRev, comp, numComp);
        numComp = (numComp + 1);
      }
      idx = (idx - 1);
    }
    boolean[] result = new boolean[(n + 1)];
    int xi = 1;
    while ((xi <= n)) {
      int posIdx = litIndex(xi, n);
      int negIdx = litIndex(neg(xi, n), n);
      if ((comp[posIdx] > comp[negIdx])) {
        result[xi] = true;
      } else {
        result[xi] = false;
      }
      xi = (xi + 1);
    }
    return result;
  }

  public int dfs1(int u, int[][] adj, int[] deg, boolean[] visited, int[] order, int orderSize) {
    visited[u] = true;
    int i = 0;
    while ((i < deg[u])) {
      int w = adj[u][i];
      if ((!visited[w])) {
        orderSize = dfs1(w, adj, deg, visited, order, orderSize);
      }
      i = (i + 1);
    }
    order[orderSize] = u;
    return (orderSize + 1);
  }

  public void dfs2(int u, int[][] adj, int[] deg, int[] comp, int c) {
    comp[u] = c;
    int i = 0;
    while ((i < deg[u])) {
      int w = adj[u][i];
      if ((comp[w] < 0)) {
        dfs2(w, adj, deg, comp, c);
      }
      i = (i + 1);
    }
  }

  public int abs(int x) {
    if ((x < 0)) {
      return (0 - x);
    }
    return x;
  }

  public int neg(int lit, int n) {
    return (0 - lit);
  }

  public int litIndex(int lit, int n) {
    if ((lit > 0)) {
      return lit;
    }
    return (n + (0 - lit));
  }

  public static void main(String[] args) {
    int[] clauseA = new int[] {1, 4, 7};
    int[] clauseB = new int[] {2, 3, 5, 8};
    TwoSAT prog = new TwoSAT();
    boolean[] result = prog.TwoSAT(clauseA, clauseB);
    System.out.println(Arrays.toString(result));
  }
}