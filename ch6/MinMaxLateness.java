// Minimizing maximum lateness, as bxx-greedy.tex's Algorithm
// MinMaxLateness states it:
//
// last := 0; lateness := 0
// for i := 1 to n:
// finish := last + t[i]
// G[i] := last
// last := finish
// if finish > d[i]: lateness := max(lateness, finish - d[i])
// return G, lateness
//
// Jobs arrive sorted by deadline, which is the greedy choice that
// minimizes the maximum lateness.
//
// The loop is `for`, not `forall`: last and lateness both carry from one
// iteration to the next.
//
// The book returns the pair (G, lateness). LL returns one value, so G is
// returned and lateness is computed alongside it, exactly as the book
// computes it.

import java.util.*;

public class MinMaxLateness {
  public int[] MinMaxLateness(int[] t, int[] d) {
    int n = t.length;
    int[] G = new int[n];
    int last = 0;
    int lateness = 0;
    for (int i = 0; i < n; i++) {
      {
        int finish = (last + t[i]);
        G[i] = last;
        last = finish;
        if ((finish > d[i])) {
          lateness = Math.max(lateness, (finish - d[i]));
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] t = new int[] {0, 0, 1, 2};
    int[] d = new int[] {0, 0, 1, 1};
    MinMaxLateness prog = new MinMaxLateness();
    int[] result = prog.MinMaxLateness(t, d);
    System.out.println(Arrays.toString(result));
  }
}