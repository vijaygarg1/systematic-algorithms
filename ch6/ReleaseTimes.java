// Release-times constraint: job j cannot start before time r[j].

import java.util.*;

public class ReleaseTimes {
  int n;
  int[] r;
  int[] G;

  private boolean forbidden(int j) {
    if (!((G[j] < r[j]))) return false;
    return true;
  }

  private void advance(int j) {
    G[j] = r[j];
  }

  public void ReleaseTimes(int[] r, int[] G) {
    this.r = r;
    this.G = G;
    this.n = r.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            advance(j);
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] r = new int[] {1, 4, 7};
    int[] G = new int[] {2, 3, 5, 8};
    ReleaseTimes prog = new ReleaseTimes();
    prog.ReleaseTimes(r, G);
    System.out.println(Arrays.toString(r));
    System.out.println(Arrays.toString(G));
  }
}