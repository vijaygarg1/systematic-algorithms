// Classical sequential weighted-interval-scheduling DP. Intervals are
// presorted once by finish time, with index 0 a 0-weight sentinel, so
// p[j] -- the last interval that finishes no later than j starts -- is
// derived here rather than supplied: it is the largest such index, or the
// sentinel 0 when no earlier interval fits.

import java.util.*;

public class WeightedIntervalScheduling {
  public int[] schedule(int[] s, int[] f, int[] w) {
    int n = s.length;
    int[] p = new int[n];
    int[] G = new int[n];
    int[] sel = new int[n];
    for (int j = 1; j < n; j++) {
      p[j] = lastCompatible(j, s, f);
    }
    G[0] = 0;
    for (int cur = 1; cur < n; cur++) {
      G[cur] = Math.max((w[cur] + G[p[cur]]), G[(cur - 1)]);
    }
    int cur = (n - 1);
    while ((cur > 0)) {
      if (((w[cur] + G[p[cur]]) >= G[(cur - 1)])) {
        sel[cur] = 1;
        cur = p[cur];
      } else {
        cur = (cur - 1);
      }
    }
    return sel;
  }

  public int lastCompatible(int j, int[] s, int[] f) {
    int lo = 1;
    int hi = (j - 1);
    int best = 0;
    while ((lo <= hi)) {
      int mid = ((lo + hi) / 2);
      if ((f[mid] <= s[j])) {
        best = mid;
        lo = (mid + 1);
      } else {
        hi = (mid - 1);
      }
    }
    return best;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    int[] w = new int[] {0, 0, 0, 2};
    WeightedIntervalScheduling prog = new WeightedIntervalScheduling();
    int[] result = prog.schedule(s, f, w);
    System.out.println(Arrays.toString(result));
  }
}