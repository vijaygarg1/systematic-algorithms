// Mandatory-Intervals: interval scheduling when subset S must appear in the answer.
// Returns null ("infeasible") exactly when two mandatory intervals overlap.
// Intervals arrive indexed in finish-time order.
// NOT a conjunct on LLP-IntervalScheduling: that composition rejects feasible
// instances (interval 0=[0,2) optional, 1=[1,3) mandatory). Three phases instead.

import java.util.*;

public class MandatoryIntervals {
  public boolean[] MandatoryIntervals(int[] s, int[] f, boolean[] S) {
    int n = s.length;
    boolean[] sel = new boolean[((n - 1)) + 1];
    for (int j = 0; j < n; j++) {
      {
        if (S[j]) {
          for (int k = 0; k < n; k++) {
            {
              if (((S[k] && (k != j)) && overlap(j, k, s, f))) {
                return null;
              }
            }
          }
          sel[j] = true;
        }
      }
    }
    boolean[] cand = new boolean[((n - 1)) + 1];
    for (int j = 0; j < n; j++) {
      {
        if ((!S[j])) {
          boolean ok = true;
          for (int k = 0; k < n; k++) {
            {
              if ((S[k] && overlap(j, k, s, f))) {
                ok = false;
              }
            }
          }
          cand[j] = ok;
        }
      }
    }
    boolean chosen = false;
    int lastFinish = 0;
    for (int j = 0; j < n; j++) {
      {
        if (cand[j]) {
          if (((!chosen) || (s[j] >= lastFinish))) {
            sel[j] = true;
            lastFinish = f[j];
            chosen = true;
          }
        }
      }
    }
    return sel;
  }

  public boolean overlap(int j, int k, int[] s, int[] f) {
    return ((s[j] < f[k]) && (s[k] < f[j]));
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    boolean[] S = new boolean[] {false, false, false, false};
    MandatoryIntervals prog = new MandatoryIntervals();
    boolean[] result = prog.MandatoryIntervals(s, f, S);
    System.out.println(Arrays.toString(result));
  }
}