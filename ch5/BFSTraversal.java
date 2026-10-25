// BFS-Traversal, exactly as bxx-graph.tex's Algorithm BFS-Traversal
// states it:
//
// var S: queue of int               // discovered but not yet explored
// S.enqueue(s); dist[s] := 0
// while not S.empty():
// j := S.dequeue()
// forall k in succ(j):
// if dist[k] > dist[j] + 1:
// dist[k] := dist[j] + 1; parent[k] := j; S.enqueue(k)
// return dist
//
// The book's `queue of int` is a list<int> here: add is enqueue and
// removeFirst is dequeue, so the list is used strictly FIFO.
//
// parent is the BFS tree the book declares as a second output: parent[k]
// is the vertex k was first reached from, and stays -1 for the source and
// for anything unreachable. Only dist is returned, as the book returns.

import java.util.*;

public class BFSTraversal {
  public int[] BFSTraversal(int[][] succ, int s) {
    int n = succ.length;
    int[] dist = new int[n];
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) {
      dist[i] = Integer.MAX_VALUE;
    }
    for (int i = 0; i < n; i++) {
      parent[i] = (-1);
    }
    LinkedList<Integer> S = new LinkedList<>();
    S.add(s);
    dist[s] = 0;
    while ((!S.isEmpty())) {
      int j = S.removeFirst();
      for (int k : succ[j]) {
        if ((dist[k] > (dist[j] + 1))) {
          dist[k] = (dist[j] + 1);
          parent[k] = j;
          S.add(k);
        }
      }
    }
    return dist;
  }

  public static void main(String[] args) {
    // No runnable example: BFSTraversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}