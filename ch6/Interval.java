// Maximum-cardinality interval scheduling: greedy by earliest finish time.

import java.util.*;

public class Interval {
  public int[] schedule(int[] s, int[] f) {
    int n = s.length;
    int[] G = new int[n];
    if ((n > 0)) {
      G[0] = 1;
      int last = 0;
      int i = 1;
      while ((i < n)) {
        if ((s[i] >= f[last])) {
          G[i] = 1;
          last = i;
        }
        i = (i + 1);
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    Interval prog = new Interval();
    int[] result = prog.schedule(s, f);
    System.out.println(Arrays.toString(result));
  }
}