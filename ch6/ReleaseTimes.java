// ReleaseTimes: composition program enforcing that job j cannot start
// before its release time r[j].  Composed onto LLP-MinMaxLateness via
// predicate conjunction:
// [ LLP-MinMaxLateness(t, d, G) && ReleaseTimes(r, G) ].

import java.util.*;

public class ReleaseTimes {
  int n;
  int[] r;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] < r[j]))) return false;
    return true;
  }

  private void advance() {
    G[j] = r[j];
  }

  public int[] ReleaseTimes(int[] r, int[] G) {
    this.r = r;
    this.G = G;
    this.n = r.length;
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
    int[] r = new int[] {1, 4, 7};
    int[] G = new int[] {2, 3, 5, 8};
    ReleaseTimes prog = new ReleaseTimes();
    int[] result = prog.ReleaseTimes(r, G);
    System.out.println(Arrays.toString(result));
  }
}