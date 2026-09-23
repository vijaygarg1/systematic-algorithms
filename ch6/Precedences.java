// Precedences: composition program enforcing G[j] >= G[i] + t[i] for
// every i in pre(j).

import java.util.*;

public class Precedences {
  public int[] run(Set<Integer>[] pre, int[] t, int[] G) {
    int n = G.length;
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 0; j < n; j++) {
        int target = G[j];
        for (int i : pre[j]) {
          int v = G[i] + t[i];
          if (v > target) target = v;
        }
        if (target > G[j]) { G[j] = target; changed = true; }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    Set<Integer>[] pre = new Set[]{ Set.of(), Set.of(0), Set.of(0, 1) };
    int[] t = {3, 4, 2};
    int[] G = {0, 0, 0};
    System.out.println(Arrays.toString(new Precedences().run(pre, t, G)));
  }
}
