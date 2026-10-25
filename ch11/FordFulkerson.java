// Classical Ford-Fulkerson maximum flow: repeatedly find an augmenting
// path in the residual graph by depth-first search and push the largest
// amount that fits along it, until no augmenting path remains.
//
// REPRESENTATION. The graph arrives as an edge list -- U[i], V[i], C[i]
// for edge i -- and is turned into the standard PAIRED-ARC structure:
// input edge i becomes arcs 2i (forward, capacity C[i]) and 2i+1
// (reverse, capacity 0), adj[u] holds the ARC INDICES leaving u, and
// head[e] is the arc's head. The reverse of arc e is
// rev(e) = e + 1 - 2 * (e % 2)
// (LL has no ^, so the usual e^1 is spelled out), which is what makes
// "cancel the reverse arc first" an O(1) step.
//
// This replaces a capacity/flow MATRIX. The book's bound is O(f* m): each
// augmenting-path search is O(m), because it walks the edges actually
// present. A matrix forces every search to scan all n candidate heads per
// vertex -- Theta(n^2) per search no matter how few edges there are --
// which makes the program O(f* n^2) and matches the book only on a dense
// graph. The book writes f(u,v) and c_f(u,v) throughout, never f[u,v]:
// a function of an edge, not a matrix lookup.
//
// FLOW CONVENTION. flow stays NON-NEGATIVE -- a flow function, not an
// antisymmetric skew matrix -- which is the book's own convention: its
// inner loop reads `x := min(delta, f(v,u))`, cancels that much reverse
// flow first, and only puts the remainder on (u,v). The matching residual
// capacity is therefore
// residual(e) = cap[e] - flow[e] + flow[rev(e)].
//
// The returned array is indexed by INPUT edge: result[i] is the flow on
// edge i, which is flow on arc 2i.

import java.util.*;

public class FordFulkerson {
  public int[] FordFulkerson(int n, int[] U, int[] V, int[] C, int s, int t) {
    int m = U.length;
    int[] head = new int[(2 * m)];
    int[] cap = new int[(2 * m)];
    int[] flow = new int[(2 * m)];
    for (int i = 0; i < m; i++) {
      {
        head[(2 * i)] = V[i];
        cap[(2 * i)] = C[i];
        head[((2 * i) + 1)] = U[i];
        cap[((2 * i) + 1)] = 0;
      }
    }
    int[] deg = new int[n];
    for (int i = 0; i < m; i++) {
      {
        deg[U[i]] = (deg[U[i]] + 1);
        deg[V[i]] = (deg[V[i]] + 1);
      }
    }
    int[][] adj = new int[n][];
    for (int u = 0; u < n; u++) {
      adj[u] = new int[deg[u]];
    }
    int[] fill = new int[n];
    for (int i = 0; i < m; i++) {
      {
        adj[U[i]][fill[U[i]]] = (2 * i);
        fill[U[i]] = (fill[U[i]] + 1);
        adj[V[i]][fill[V[i]]] = ((2 * i) + 1);
        fill[V[i]] = (fill[V[i]] + 1);
      }
    }
    int[] parentArc = new int[n];
    while ((findAugmentingPath(n, adj, head, cap, flow, s, t, parentArc) == 1)) {
      augment(head, cap, flow, s, t, parentArc, bottleneck(head, cap, flow, s, t, parentArc));
    }
    int[] result = new int[m];
    for (int i = 0; i < m; i++) {
      result[i] = flow[(2 * i)];
    }
    return result;
  }

  public int rev(int e) {
    return ((e + 1) - (2 * (e % 2)));
  }

  public int residual(int[] cap, int[] flow, int e) {
    return ((cap[e] - flow[e]) + flow[rev(e)]);
  }

  public int bottleneck(int[] head, int[] cap, int[] flow, int s, int v, int[] parentArc) {
    if ((v == s)) {
      return Integer.MAX_VALUE;
    }
    int e = parentArc[v];
    return Math.min(residual(cap, flow, e), bottleneck(head, cap, flow, s, head[rev(e)], parentArc));
  }

  public void augment(int[] head, int[] cap, int[] flow, int s, int v, int[] parentArc, int b) {
    if ((v == s)) {
      return;
    }
    int e = parentArc[v];
    int x = Math.min(b, flow[rev(e)]);
    if ((x > 0)) {
      flow[rev(e)] = (flow[rev(e)] - x);
    }
    if ((b > x)) {
      flow[e] = (flow[e] + (b - x));
    }
    augment(head, cap, flow, s, head[rev(e)], parentArc, b);
  }

  public int findAugmentingPath(int n, int[][] adj, int[] head, int[] cap, int[] flow, int s, int t, int[] parentArc) {
    boolean[] seen = new boolean[n];
    seen[s] = true;
    return dfs(adj, head, cap, flow, s, t, parentArc, seen);
  }

  public int dfs(int[][] adj, int[] head, int[] cap, int[] flow, int u, int t, int[] parentArc, boolean[] seen) {
    if ((u == t)) {
      return 1;
    }
    for (int q = 0; q < adj[u].length; q++) {
      {
        int e = adj[u][q];
        int v = head[e];
        if (((!seen[v]) && (residual(cap, flow, e) > 0))) {
          seen[v] = true;
          parentArc[v] = e;
          if ((dfs(adj, head, cap, flow, v, t, parentArc, seen) == 1)) {
            return 1;
          }
        }
      }
    }
    return 0;
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] C = new int[] {0, 0, 0, 2};
    FordFulkerson prog = new FordFulkerson();
    int[] result = prog.FordFulkerson(U.length, U, V, C, 0, 1);
    System.out.println(Arrays.toString(result));
  }
}