// LLP-Boruvka: Boruvka's minimum spanning tree with the component
// contraction done by LLP pointer jumping instead of recursion.
//
// Each round every active component finds its cheapest outgoing edge,
// points at the component on the other end, and then the guard below
// jumps those pointers until each chain collapses to one representative:
// forbidden when G[j] is not yet its own component's root, advance by
// following one more link. Mutual choices are broken by keeping the lower
// index as the root, which is what pointTo decides.
//
// A round that merges nothing means the graph is disconnected, which is
// what progress stops on.

import java.util.*;

public class LLPBoruvka {
  int n;
  int[] U;
  int[] V;
  int[] W;
  boolean[] T;
  boolean[] active;
  int[] G;
  int[] mweEdge;
  double[] mweW;
  int j;

  private boolean _forbidden0(int j) {
    if (!(active[j])) return false;
    if (!((G[j] != G[G[j]]))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = G[G[j]];
  }

  public boolean[] LLPBoruvka(int n, int[] U, int[] V, int[] W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.T = new boolean[U.length];
    this.active = new boolean[n];
    this.G = new int[n];
    this.mweEdge = new int[n];
    this.mweW = new double[n];
    for (int i = 0; i < n; i++) {
      {
        G[i] = i;
        active[i] = true;
      }
    }
    boolean progress = true;
    while (((countActive() > 1) && progress)) {
      int before = countActive();
      for (int i = 0; i < n; i++) {
        {
          mweEdge[i] = (0 - 1);
          mweW[i] = Double.POSITIVE_INFINITY;
        }
      }
      for (int e = 0; e < U.length; e++) {
        {
          int ru = find(U[e]);
          int rv = find(V[e]);
          if (((active[ru] && active[rv]) && (ru != rv))) {
            relax(ru, e, W);
            relax(rv, e, W);
          }
        }
      }
      for (int i = 0; i < n; i++) {
        if (active[i]) {
          G[i] = pointTo(i);
        }
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
      for (int i = 0; i < n; i++) {
        if ((active[i] && (mweEdge[i] != (0 - 1)))) {
          T[mweEdge[i]] = true;
        }
      }
      for (int i = 0; i < n; i++) {
        if ((active[i] && (G[i] != i))) {
          active[i] = false;
        }
      }
      progress = (countActive() < before);
    }
    return T;
  }

  public void relax(int comp, int e, int[] W) {
    if ((W[e] < mweW[comp])) {
      mweW[comp] = W[e];
      mweEdge[comp] = e;
    }
  }

  public int pointTo(int i) {
    if ((mweEdge[i] == (0 - 1))) {
      return i;
    }
    int w = otherEndpoint(mweEdge[i], i);
    int wEdge = mweEdge[w];
    if ((((wEdge != (0 - 1)) && (otherEndpoint(wEdge, w) == i)) && (i < w))) {
      return i;
    }
    return w;
  }

  public int find(int v) {
    while ((G[v] != v)) {
      v = G[v];
    }
    return v;
  }

  public int countActive() {
    int t1 = 0;
    for (int i = 0; i < n; i++) {
      if (active[i]) t1++;
    }
    return t1;
  }

  public int otherEndpoint(int e, int v) {
    if ((find(U[e]) == v)) {
      return find(V[e]);
    }
    return find(U[e]);
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    LLPBoruvka prog = new LLPBoruvka();
    boolean[] result = prog.LLPBoruvka(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}