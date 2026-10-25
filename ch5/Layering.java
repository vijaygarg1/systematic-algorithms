// Layering, as bxx-graph.tex's Algorithm Layering states it:
//
// var Q: queue of int                       // initially empty
// indeg[j] := |pre(j)| for all j
// forall j in V: if indeg[j] = 0: G[j] := 0; Q.enqueue(j)
// while not Q.empty():
// j := Q.dequeue()
// forall k in succ(j):
// indeg[k] := indeg[k] - 1
// if indeg[k] = 0: G[k] := 1 + max_{i in pre(k)} G[i]; Q.enqueue(k)
// return G
//
// Kahn's algorithm: a vertex gets its layer once every predecessor has
// one. The book's `queue of int` is a list<int> -- add is enqueue,
// removeFirst is dequeue.

import java.util.*;

public class Layering {
  public int[] Layering(int[][] pre, int[][] succ) {
    int n = pre.length;
    int[] G = new int[n];
    int[] indeg = new int[n];
    for (int j = 0; j < n; j++) {
      indeg[j] = pre[j].length;
    }
    LinkedList<Integer> Q = new LinkedList<>();
    for (int j = 0; j < n; j++) {
      if ((indeg[j] == 0)) {
        G[j] = 0;
        Q.add(j);
      }
    }
    while ((!Q.isEmpty())) {
      int j = Q.removeFirst();
      for (int k : succ[j]) {
        {
          indeg[k] = (indeg[k] - 1);
          if ((indeg[k] == 0)) {
            int t1 = Integer.MIN_VALUE;
            for (int i : pre[k]) {
              int _v = G[i];
              if (_v > t1) t1 = _v;
            }
            G[k] = (1 + t1);
            Q.add(k);
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    // No runnable example: Layering's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}