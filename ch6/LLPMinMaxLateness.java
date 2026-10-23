// LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
// time G[j] to the prefix sum of earlier processing times.
//
// The targets are the prefix sums S[j] = t[0] + ... + t[j-1], and they are
// built once in a left-to-right scan rather than written inline as
// `sum i in [0..j-1] : t[i]`.  Written inline, that reduction is an O(j)
// loop the guard re-runs for every j -- and the advance runs it a second
// time -- so the program costs O(n^2) even though every target is already
// known from the one before it.  Computing them in a single scan is what
// makes the stated O(n) bound (Section 6.3, "a left-to-right scan") true
// of this program and not just of the algorithm it encodes.
//
// The deadlines d are the sort order of the input, not something the
// predicate reads: j's target depends only on the processing times ahead
// of it in that order.

import java.util.*;

public class LLPMinMaxLateness {
  int n;
  int[] t;
  int[] d;
  int[] S;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    if ((G[j] >= S[j])) return false;
    return true;
  }

  private void _advance0() {
    G[j] = S[j];
  }

  public int[] LLPMinMaxLateness(int[] t, int[] d) {
    this.t = t;
    this.d = d;
    this.n = t.length;
    this.S = new int[n];
    this.G = new int[n];
    for (int j = 1; j < n; j++) {
      S[j] = (S[(j - 1)] + t[(j - 1)]);
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] t = new int[] {0, 0, 1, 2};
    int[] d = new int[] {0, 0, 1, 1};
    LLPMinMaxLateness prog = new LLPMinMaxLateness();
    int[] result = prog.LLPMinMaxLateness(t, d);
    System.out.println(Arrays.toString(result));
  }
}