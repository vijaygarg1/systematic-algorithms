// Min-max-lateness scheduling: greedy by earliest deadline.

import java.util.*;

public class MinMaxLateness {
  public int[] schedule(int[] t, int[] d) {
    int n = t.length;
    int[] G = new int[n];
    int last = 0;
    for (int i = 0; i < n; i++) {
      {
        G[i] = last;
        last = (last + t[i]);
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] t = new int[] {0, 0, 1, 2};
    int[] d = new int[] {0, 0, 1, 1};
    MinMaxLateness prog = new MinMaxLateness();
    int[] result = prog.schedule(t, d);
    System.out.println(Arrays.toString(result));
  }
}