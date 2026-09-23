// HopBound: composition program enforcing h[j] <= k.  Returns null
// on infeasibility.

import java.util.*;

public class HopBound {
  public int[] run(int[] h, int k, int[] G) {
    for (int j = 0; j < h.length; j++) if (h[j] > k) return null;
    return G;
  }

  public static void main(String[] args) {
    int[] h = {0, 1, 2, 3};
    int[] G = {0, 4, 9, 15};
    int[] out = new HopBound().run(h, 2, G);
    System.out.println(out == null ? "infeasible" : Arrays.toString(out));
  }
}
