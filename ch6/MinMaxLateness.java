// Min-max-lateness scheduling: greedy by earliest deadline.

import java.util.*;

public class MinMaxLateness {
  public int[] schedule(int[] t, int[] d) {
    int n = t.length;
    int[] G = new int[n];
    int last = 0;
    int i = 0;
    while ((i < n)) {
      G[i] = last;
      last = (last + t[i]);
      i = (i + 1);
    }
    return G;
  }

  public static void main(String[] args) {
    int[] t = new int[] {1, 4, 7};
    int[] d = new int[] {2, 3, 5, 8};
    MinMaxLateness prog = new MinMaxLateness();
    int[] result = prog.schedule(t, d);
    System.out.println(Arrays.toString(result));
  }
}