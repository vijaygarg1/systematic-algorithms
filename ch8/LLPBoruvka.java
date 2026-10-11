// LLP-Boruvka (bxx-mst-llp-boruvka.tex, algo:Boruvka-llp): the book's
// recursive Boruvka(V,E) -- each level selects every active vertex's
// minimum-weight edge (breaking a mutual-mwe tie toward the
// smaller-numbered vertex), pointer-jumps to rooted stars via the LLP
// kernel, records every active vertex's own mwe edge in T, keeps only
// the roots active, and recurses until one component remains. The
// book's tail recursion is written here as a `while` loop (the two are
// semantically identical -- the recursive call is the last action at
// each level, with T already accumulated) so the LLP kernel can stay a
// genuine `forbidden`/`advance` guard, re-run to a fresh fixpoint once
// per level by the surrounding loop. G and active[] persist across
// levels rather than being relabeled into a smaller V'/E' each time (no
// dynamic graph type in this language); find(v) walks G to a fixpoint
// instead, resolving any edge -- even one between two long-since-
// absorbed vertices -- to its current top-level components, the same
// effect as the book's contraction without rebuilding the graph. The
// book's own precondition is a connected graph with no self-loops
// (bxx-mst-llp-boruvka.tex: "we assume that the input ... is a connected
// graph without any self-loops"), so it never needs to handle a vertex
// with no outgoing edge at all; `progress` guards against exactly that
// case (a disconnected input, or a self-loop/isolated vertex in a
// synthesized test instance) the same way classical Boruvka.llp's own
// `progress` guard does, so the loop below cannot spin forever waiting
// for a spanning tree that cannot exist.

import java.util.*;

public class LLPBoruvka {
  int n;
  int[] U;
  int[] V;
  int[] W;
  boolean[] T;
  int[] G;
  boolean[] active;
  int j;

  private boolean _forbidden0(int j) {
    if (!(active[j])) return false;
    if (!((G[j] != G[G[j]]))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = G[G[j]];
  }

  public boolean[] mst(int n, int[] U, int[] V, int[] W) {
    this.n = n;
    this.U = U;
    this.V = V;
    this.W = W;
    this.T = new boolean[U.length];
    this.G = new int[n];
    this.active = new boolean[n];
    for (int i = 0; i < n; i++) {
      {
        G[i] = i;
        active[i] = true;
      }
    }
    boolean progress = true;
    while (((countActive(n, active) > 1) && progress)) {
      int before = countActive(n, active);
      int m = U.length;
      int[] mweEdge = new int[n];
      double[] mweW = new double[n];
      for (int i = 0; i < n; i++) {
        mweEdge[i] = (-1);
      }
      for (int i = 0; i < n; i++) {
        mweW[i] = Integer.MAX_VALUE;
      }
      int e = 0;
      while ((e < m)) {
        int ru = find(U[e], G);
        int rv = find(V[e], G);
        if (((active[ru] && active[rv]) && (ru != rv))) {
          if ((W[e] < mweW[ru])) {
            mweW[ru] = W[e];
            mweEdge[ru] = e;
          }
          if ((W[e] < mweW[rv])) {
            mweW[rv] = W[e];
            mweEdge[rv] = e;
          }
        }
        e = (e + 1);
      }
      for (int i = 0; i < n; i++) {
        if (active[i]) {
          if ((mweEdge[i] == (0 - 1))) {
            G[i] = i;
          } else {
            int w = otherEndpoint(mweEdge[i], U, V, i);
            int wEdge = mweEdge[w];
            if ((((wEdge != (0 - 1)) && (otherEndpoint(wEdge, U, V, w) == i)) && (i < w))) {
              G[i] = i;
            } else {
              G[i] = w;
            }
          }
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
      progress = (countActive(n, active) < before);
    }
    return T;
  }

  public int find(int v, int[] G) {
    while ((G[v] != v)) {
      v = G[v];
    }
    return v;
  }

  public int countActive(int n, boolean[] active) {
    int c = 0;
    int i = 0;
    while ((i < n)) {
      if (active[i]) {
        c = (c + 1);
      }
      i = (i + 1);
    }
    return c;
  }

  public int otherEndpoint(int e, int[] U, int[] V, int v) {
    if ((U[e] == v)) {
      return V[e];
    }
    return U[e];
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    LLPBoruvka prog = new LLPBoruvka();
    boolean[] result = prog.mst(U.length, U, V, W);
    System.out.println(Arrays.toString(result));
  }
}