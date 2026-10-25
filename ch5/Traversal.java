// Traversal, as bxx-graph.tex's Algorithm Traversal states it:
//
// var S: set of int                 // discovered but not yet explored
// S.add(s); visited[s] := 1
// while not S.empty():
// j := S.removeAny(visited)
// forall k in succ(j):
// if visited[k] = 0: visited[k] := 1; S.add(k)
// return visited
//
// The book's S is a SET and removeAny may return any member -- that is the
// point of this algorithm: BFS-Traversal and DFS-Traversal are the same
// code with the order of removal pinned down. LL has no scalar set type
// with a "remove any" operation, so S is a list<int> here and removeFirst
// makes one concrete choice. Any other choice visits the same vertices;
// only the order differs, and the result (the reachable set) is the same.

import java.util.*;

public class Traversal {
  public int[] Traversal(int[][] succ, int s) {
    int n = succ.length;
    int[] visited = new int[n];
    LinkedList<Integer> S = new LinkedList<>();
    S.add(s);
    visited[s] = 1;
    while ((!S.isEmpty())) {
      int j = S.removeFirst();
      for (int k : succ[j]) {
        if ((visited[k] == 0)) {
          visited[k] = 1;
          S.add(k);
        }
      }
    }
    return visited;
  }

  public static void main(String[] args) {
    // No runnable example: Traversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}