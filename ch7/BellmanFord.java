// Classical Bellman-Ford, as bxx-shortestPath.tex's Algorithm BellmanFord
// states it:
//
// forall v in V: d[v] := infinity; p[v] := null
// d[s] := 0
// for k = 1 to n-1:
// forall edge (u,v) in E:
// if d[u] + w[u,v] < d[v]: d[v] := d[u] + w[u,v];  p[v] := u
// forall edge (u,v) in E:
// if d[u] + w[u,v] < d[v]: return "Negative cycle detected"
// return d[], p[]
//
// Edges arrive as three parallel arrays: edge e runs U[e] -> V[e] with
// weight W[e]. That is the book's "forall edge (u,v) in E" written out.
//
// p[v] = -1 stands for the book's null: -1 is not a vertex, since vertices
// are 0..n-1. The book declares p as a second output; LL returns one value,
// so d is returned and p is computed alongside it.
//
// A negative cycle makes the program return null, LL's "no solution".
//
// d[u] != infinity guards the relaxation: the book's d[u] + w[u,v] is
// infinity + w = infinity when u is unreached, which is never < d[v]. In
// finite ints infinity + w overflows to a large negative instead, so the
// test has to be made explicitly.
//
// The k loop and the edge loops are `for`, not `forall`: each pass reads
// the distances the previous pass wrote.

import java.util.*;

public class BellmanFord {
  public int[] BellmanFord(int n, int[] U, int[] V, int[] W, int s) {
    int[] d = new int[n];
    int[] p = new int[n];
    for (int i = 0; i < n; i++) {
      d[i] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < n; i++) {
      p[i] = (-1);
    }
    d[s] = 0;
    for (int k = 1; k < n; k++) {
      {
        for (int e = 0; e < U.length; e++) {
          {
            int u = U[e];
            int v = V[e];
            if (((d[u] != Integer.MAX_VALUE) && ((d[u] + W[e]) < d[v]))) {
              d[v] = (d[u] + W[e]);
              p[v] = u;
            }
          }
        }
      }
    }
    for (int e = 0; e < U.length; e++) {
      {
        int u = U[e];
        int v = V[e];
        if (((d[u] != Integer.MAX_VALUE) && ((d[u] + W[e]) < d[v]))) {
          return null;
        }
      }
    }
    return d;
  }

  public static void main(String[] args) {
    int[] U = new int[] {0, 0, 1, 2};
    int[] V = new int[] {0, 0, 1, 1};
    int[] W = new int[] {0, 0, 0, 2};
    BellmanFord prog = new BellmanFord();
    int[] result = prog.BellmanFord(U.length, U, V, W, 0);
    System.out.println(Arrays.toString(result));
  }
}