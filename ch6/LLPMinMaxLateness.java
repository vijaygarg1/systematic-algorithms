// LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start time G[j] to the prefix sum of earlier processing times.

import java.util.*;

public class LLPMinMaxLateness {
  int n;
  int[] t;
  int[] d;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    int t1 = 0;
    for (int i = 0; i < j; i++) {
      t1 += t[i];
    }
    return (!(G[j] >= t1));
  }

  private void advance() {
    int t2 = 0;
    for (int i = 0; i < j; i++) {
      t2 += t[i];
    }
    G[j] = t2;
  }

  public int[] LLPMinMaxLateness(int[] t, int[] d) {
    this.t = t;
    this.d = d;
    this.n = t.length;
    this.G = new int[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
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