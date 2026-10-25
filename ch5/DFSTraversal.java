// DFS-Traversal, as bxx-graph.tex's Algorithm DFS-Traversal states it:
//
// var tick: int initially 1
// function DFS(j):
// visited[j] := 1
// discovered[j] := tick; tick := tick + 1
// for k in succ(j):
// if visited[k] = 0: parent[k] := j; DFS(k)
// finished[j] := tick; tick := tick + 1
// DFS(s)
//
// The book's DFS(j) closes over visited/parent/discovered/finished/tick;
// LL has no closures, so DFS takes them as parameters.
//
// tick is the book's `var tick: int initially 1`, a counter shared by every
// DFS call. A plain `int tick` would be local to the entry method and
// invisible to DFS (verified: the generated Java does not compile), so it
// travels as a one-slot array -- the standard way to pass a mutable scalar.
//
// The book declares four outputs (visited, parent, discovered, finished);
// LL returns one value, so discovered is returned and the rest stay
// internal, exactly as they are computed here.

import java.util.*;

public class DFSTraversal {
  public int[] DFSTraversal(int[][] succ, int s) {
    int n = succ.length;
    int[] visited = new int[n];
    int[] parent = new int[n];
    int[] discovered = new int[n];
    int[] finished = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = (-1);
    }
    int[] tick = new int[1];
    tick[0] = 1;
    DFS(s, succ, visited, parent, discovered, finished, tick);
    return discovered;
  }

  public void DFS(int j, int[][] succ, int[] visited, int[] parent, int[] discovered, int[] finished, int[] tick) {
    visited[j] = 1;
    discovered[j] = tick[0];
    tick[0] = (tick[0] + 1);
    for (int k : succ[j]) {
      if ((visited[k] == 0)) {
        parent[k] = j;
        DFS(k, succ, visited, parent, discovered, finished, tick);
      }
    }
    finished[j] = tick[0];
    tick[0] = (tick[0] + 1);
  }

  public static void main(String[] args) {
    // No runnable example: DFSTraversal's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}