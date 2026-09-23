// ReleaseTimes: composition program enforcing G[j] >= r[j].

import java.util.*;

public class ReleaseTimes {
  public int[] run(int[] r, int[] G) {
    int n = G.length;
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 0; j < n; j++) {
        if (G[j] < r[j]) { G[j] = r[j]; changed = true; }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] r = {0, 3, 2, 7};
    int[] G = {0, 0, 0, 0};
    System.out.println(Arrays.toString(new ReleaseTimes().run(r, G)));
  }
}
