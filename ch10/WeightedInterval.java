// Classical sequential weighted-interval-scheduling DP given p[].

import java.util.*;

public class WeightedInterval {
  public int[] schedule(int[] s, int[] f, int[] w, int[] p) {
    int n = s.length;
    int[] opt = new int[n];
    int[] G = new int[n];
    opt[0] = 0;
    int cur = 1;
    while ((cur < n)) {
      opt[cur] = opt[(cur - 1)];
      if (((w[cur] + opt[p[cur]]) >= opt[(cur - 1)])) {
        opt[cur] = (w[cur] + opt[p[cur]]);
        G[cur] = 1;
      } else {
        G[cur] = 0;
      }
      cur = (cur + 1);
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {1, 4, 7};
    int[] f = new int[] {2, 3, 5, 8};
    int[] w = new int[] {6, 9};
    int[] p = new int[] {6, 9};
    WeightedInterval prog = new WeightedInterval();
    int[] result = prog.schedule(s, f, w, p);
    System.out.println(Arrays.toString(result));
  }
}