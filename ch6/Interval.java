// Interval scheduling, as bxx-greedy.tex's Algorithm Interval states it:
//
// G[1] := 1                       // first interval always selected
// last := 1                       // index of last selected interval
// for i := 2 to n:
// if s[i] >= f[last]:
// G[i] := 1                   // interval i is compatible
// last := i
//
// Intervals arrive sorted by finish time, so the earliest-finishing
// compatible interval is always the right greedy choice.
//
// The loop is `for`, not `forall`: last carries from one iteration to the
// next, so the iterations are not independent.
//
// The book indexes from 1 and selects G[1] unconditionally, so it assumes
// n >= 1; this translation keeps that precondition.

import java.util.*;

public class Interval {
  public int[] Interval(int[] s, int[] f) {
    int n = s.length;
    int[] G = new int[n];
    G[0] = 1;
    int last = 0;
    for (int i = 1; i < n; i++) {
      {
        if ((s[i] >= f[last])) {
          G[i] = 1;
          last = i;
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    Interval prog = new Interval();
    int[] result = prog.Interval(s, f);
    System.out.println(Arrays.toString(result));
  }
}