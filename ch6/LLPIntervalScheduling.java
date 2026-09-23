// LLP-IntervalScheduling: G[j] = true when job j is selected; forbidden when j is unselected and compatible with every already-selected earlier job.

import java.util.*;

public class LLPIntervalScheduling {
  int n;
  int[] s;
  int[] f;
  boolean[] G;

  private boolean forbidden(int j) {
    if (G[j]) return false;
    for (int i = 0; i < j; i++) if (!((!G[i]) || (f[i] <= s[j]))) return false;
    return true;
  }

  private void advance(int j) {
    G[j] = true;
  }

  public boolean[] LLPIntervalScheduling(int[] s, int[] f) {
    this.s = s;
    this.f = f;
    this.n = s.length;
    this.G = new boolean[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {1, 4, 7};
    int[] f = new int[] {2, 3, 5, 8};
    LLPIntervalScheduling prog = new LLPIntervalScheduling();
    boolean[] result = prog.LLPIntervalScheduling(s, f);
    System.out.println(Arrays.toString(result));
  }
}