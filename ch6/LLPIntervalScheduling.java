// LLP-IntervalScheduling (bxx-greedy.tex, fig:activity): jobs sorted by
// finish time, held in a doubly linked list (prevArr/nextArr). Job 0 is
// always fixed initially. The book's two rules are written here as two
// forbidden/advance clauses joined by `&&`, so they share one fixpoint:
// every job is tested against both rules on each sweep. Rule 1 selects a
// job whose list-predecessor has already finished by s[j]; rule 2 deletes
// a job that its (selected) predecessor still overlaps, in O(1), via
// next[prev[j]] := next[j]; prev[next[j]] := prev[j]. deleted[] records
// the list removal that the book expresses by unlinking the node.

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
    if (deleted[j]) return false;
    if (G[j]) return false;
    if (!((f[prevArr[j]] <= s[j]))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
  }

  private boolean _forbidden1(int j) {
    if (!((j > 0))) return false;
    if (deleted[j]) return false;
    if (!(G[prevArr[j]])) return false;
    if (!((s[j] < f[prevArr[j]]))) return false;
    return true;
  }

  private void _advance1() {
    deleted[j] = true;
    int p = prevArr[j];
    int nx = nextArr[j];
    nextArr[p] = nx;
    if ((nx < n)) {
      prevArr[nx] = p;
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
          if (_forbidden1(j)) {
            this.j = j; _advance1();
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