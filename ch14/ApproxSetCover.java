// Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.

import java.util.*;

public class ApproxSetCover {
  public boolean[] ApproxSetCover(int[][] mem, int[][] inc) {
    int m = mem.length;
    int n = inc.length;
    boolean[] C = new boolean[m];
    boolean[] covered = new boolean[n];
    int[] cnt = new int[m];
    int remaining = n;
    for (int s = 0; s < m; s++) {
      cnt[s] = mem[s].length;
    }
    while ((remaining > 0)) {
      int bestIdx = 0;
      int bestCover = (0 - 1);
      int s = 0;
      while ((s < m)) {
        if ((cnt[s] > bestCover)) {
          bestCover = cnt[s];
          bestIdx = s;
        }
        s = (s + 1);
      }
      C[bestIdx] = true;
      int k = 0;
      while ((k < mem[bestIdx].length)) {
        int e = mem[bestIdx][k];
        if ((!covered[e])) {
          covered[e] = true;
          remaining = (remaining - 1);
          int t = 0;
          while ((t < inc[e].length)) {
            cnt[inc[e][t]] = (cnt[inc[e][t]] - 1);
            t = (t + 1);
          }
        }
        k = (k + 1);
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] mem = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] inc = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    ApproxSetCover prog = new ApproxSetCover();
    boolean[] result = prog.ApproxSetCover(mem, inc);
    System.out.println(Arrays.toString(result));
  }
}