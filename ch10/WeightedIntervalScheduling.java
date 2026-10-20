// Classical sequential weighted-interval-scheduling DP given p[].

import java.util.*;

public class WeightedIntervalScheduling {
  public int[] schedule(int[] s, int[] f, int[] w, int[] p) {
    int n = s.length;
    int[] opt = new int[n];
    int[] G = new int[n];
    opt[0] = 0;
    for (int cur = 1; cur < n; cur++) {
      opt[cur] = Math.max((w[cur] + opt[p[cur]]), opt[(cur - 1)]);
    }
    int cur = (n - 1);
    while ((cur > 0)) {
      if (((w[cur] + opt[p[cur]]) >= opt[(cur - 1)])) {
        G[cur] = 1;
        cur = p[cur];
      } else {
        cur = (cur - 1);
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    int[] w = new int[] {0, 0, 0, 2};
    int[] p = new int[] {0, 0, 2, 1};
    WeightedIntervalScheduling prog = new WeightedIntervalScheduling();
    int[] result = prog.schedule(s, f, w, p);
    System.out.println(Arrays.toString(result));
  }
}