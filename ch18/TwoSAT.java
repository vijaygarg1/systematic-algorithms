// Classical 2-SAT via Kosaraju's strongly-connected components.

import java.util.*;

public class TwoSAT {
  public boolean[] TwoSAT(int[] clauseA, int[] clauseB) {
    int m = clauseA.length;
    int t1 = Integer.MIN_VALUE;
    for (int i = 0; i < m; i++) {
      int _v = Math.max(Math.abs(clauseA[i]), Math.abs(clauseB[i]));
      if (_v > t1) t1 = _v;
    }
    int n = Math.max(0, t1);
    int sz = ((2 * n) + 2);
    int cap = ((2 * m) + 1);
    int[][] adjFwd = new int[sz][cap];
    int[][] adjRev = new int[sz][cap];
    int[] degFwd = new int[sz];
    int[] degRev = new int[sz];
    for (int i = 0; i < m; i++) {
      {
        addImplication(litIndex((0 - clauseA[i]), n), litIndex(clauseB[i], n), adjFwd, degFwd, adjRev, degRev);
        addImplication(litIndex((0 - clauseB[i]), n), litIndex(clauseA[i], n), adjFwd, degFwd, adjRev, degRev);
      }
    }
    int[] order = new int[sz];
    int orderSize = 0;
    int v = 0;
    boolean[] visited = new boolean[sz];
    while ((v < sz)) {
      if ((!visited[v])) {
        orderSize = dfs1(v, adjFwd, degFwd, visited, order, orderSize);
      }
      v = (v + 1);
    }
    int[] comp = new int[sz];
    for (int k = 0; k < sz; k++) {
      comp[k] = (0 - 1);
    }
    int numComp = 0;
    int idx = (orderSize - 1);
    while ((idx >= 0)) {
      if ((comp[order[idx]] < 0)) {
        dfs2(order[idx], adjRev, degRev, comp, numComp);
        numComp = (numComp + 1);
      }
      idx = (idx - 1);
    }
    boolean[] result = new boolean[(n + 1)];
    for (int x = 1; x <= n; x++) {
      result[x] = (comp[x] > comp[(n + x)]);
    }
    return result;
  }

  public void addImplication(int from, int to, int[][] adjFwd, int[] degFwd, int[][] adjRev, int[] degRev) {
    adjFwd[from][degFwd[from]] = to;
    degFwd[from] = (degFwd[from] + 1);
    adjRev[to][degRev[to]] = from;
    degRev[to] = (degRev[to] + 1);
  }

  public int dfs1(int u, int[][] adj, int[] deg, boolean[] visited, int[] order, int orderSize) {
    visited[u] = true;
    for (int i = 0; i < deg[u]; i++) {
      {
        int w = adj[u][i];
        if ((!visited[w])) {
          orderSize = dfs1(w, adj, deg, visited, order, orderSize);
        }
      }
    }
    order[orderSize] = u;
    return (orderSize + 1);
  }

  public void dfs2(int u, int[][] adj, int[] deg, int[] comp, int c) {
    comp[u] = c;
    for (int i = 0; i < deg[u]; i++) {
      {
        int w = adj[u][i];
        if ((comp[w] < 0)) {
          dfs2(w, adj, deg, comp, c);
        }
      }
    }
  }

  public int litIndex(int lit, int n) {
    if ((lit > 0)) {
      return lit;
    }
    return (n + (0 - lit));
  }

  public static void main(String[] args) {
    int[] clauseA = new int[] {0, 0, 1, 2};
    int[] clauseB = new int[] {0, 0, 1, 1};
    TwoSAT prog = new TwoSAT();
    boolean[] result = prog.TwoSAT(clauseA, clauseB);
    System.out.println(Arrays.toString(result));
  }
}