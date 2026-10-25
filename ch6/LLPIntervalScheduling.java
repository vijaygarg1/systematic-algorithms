// LLP-IntervalScheduling, as bxx-greedy.tex's Algorithm
// LLP-IntervalScheduling states it. Jobs sit in a doubly linked list;
// prev and next are the previous and next node.
//
// Forbidden (j in [1..n]): !G[j] and f[prev[j]] <= s[j]
// Advance                : G[j] := true
// &&
// Forbidden (j in [1..n]): (j>1) and (next[prev[j]] = j)
// and G[prev[j]] and s[j] < f[prev[j]]
// Advance                : next[prev[j]] := next[j]     // delete from
// prev[next[j]] := prev[j]     // linked list
//
// next[prev[j]] = j is the "j is still in the list" test. Without it the
// second rule never falsifies its own guard -- deleting j leaves prev[j]
// untouched, so G[prev[j]] and s[j] < f[prev[j]] both stay true and j is
// forbidden forever. (Verified: the version without it does not
// terminate.) It is also what makes the book's own claim true, that
// every job is touched at most twice.
//
// Rule 1 needs no index bound: G[0] is true from the start, so !G[j] is
// false at j = 0 and the guard short-circuits before reaching f[prev[0]].
//
// The advance guards prev[next[j]] because next[n-1] is n, one past the
// last slot: the book's list has a tail sentinel there, this one does not.

import java.util.*;

public class LLPIntervalScheduling {
  int n;
  int[] s;
  int[] f;
  boolean[] G;
  int[] prev;
  int[] next;
  int j;

  private boolean _forbidden0(int j) {
    if (G[j]) return false;
    if (!((f[prev[j]] <= s[j]))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = true;
  }

  private boolean _forbidden1(int j) {
    if (!((j > 0))) return false;
    if (!((next[prev[j]] == j))) return false;
    if (!(G[prev[j]])) return false;
    if (!((s[j] < f[prev[j]]))) return false;
    return true;
  }

  private void _advance1() {
    next[prev[j]] = next[j];
    if ((next[j] < n)) {
      prev[next[j]] = prev[j];
    }
  }

  public boolean[] LLPIntervalScheduling(int[] s, int[] f) {
    this.s = s;
    this.f = f;
    this.n = s.length;
    this.G = new boolean[n];
    this.prev = new int[n];
    this.next = new int[n];
    for (int i = 0; i < n; i++) {
      prev[i] = (i - 1);
    }
    for (int i = 0; i < n; i++) {
      next[i] = (i + 1);
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