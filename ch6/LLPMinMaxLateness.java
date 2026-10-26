// LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
// time G[j] to the prefix sum of earlier processing times.

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