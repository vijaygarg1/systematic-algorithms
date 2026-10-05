// LLP-IntervalScheduling (bxx-greedy.tex, fig:activity): jobs sorted by
// finish time, held in a doubly linked list (prevArr/nextArr). Job 0 is
// always fixed initially. A job j (not yet fixed or deleted) is
// forbidden when either (a) its current list-predecessor has already
// finished by s[j] -- advance: fix G[j] := true -- or (b) its
// predecessor is fixed but still overlaps j -- advance: delete j from
// the list in O(1) via the pointer update next[prev[j]] := next[j];
// prev[next[j]] := prev[j]. This matches the book's O(1)-per-job
// linked-list algorithm exactly, not a brute-force scan over every
// earlier job.

import java.util.*;

public class LLPIntervalScheduling {
  int n;
  int[] s;
  int[] f;
  boolean[] G;
  boolean[] deleted;
  int[] prevArr;
  int[] nextArr;
  int j;

  private boolean _forbidden0(int j) {
    if (!((j > 0))) return false;
    if (G[j]) return false;
    if (deleted[j]) return false;
    if (!(((f[prevArr[j]] <= s[j]) || (G[prevArr[j]] && (s[j] < f[prevArr[j]]))))) return false;
    return true;
  }

  private void _advance0() {
    int p = prevArr[j];
    if ((f[p] <= s[j])) {
      G[j] = true;
    } else {
      deleted[j] = true;
      int nx = nextArr[j];
      nextArr[p] = nx;
      if ((nx < n)) {
        prevArr[nx] = p;
      }
    }
  }

  public boolean[] LLPIntervalScheduling(int[] s, int[] f) {
    this.s = s;
    this.f = f;
    this.n = s.length;
    this.n = s.length;
    this.G = new boolean[n];
    this.deleted = new boolean[n];
    this.prevArr = new int[n];
    this.nextArr = new int[n];
    for (int i = 0; i < n; i++) {
      prevArr[i] = (i - 1);
    }
    for (int i = 0; i < n; i++) {
      nextArr[i] = (i + 1);
    }
    G[0] = true;
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
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    LLPIntervalScheduling prog = new LLPIntervalScheduling();
    boolean[] result = prog.LLPIntervalScheduling(s, f);
    System.out.println(Arrays.toString(result));
  }
}