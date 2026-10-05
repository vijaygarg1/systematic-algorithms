// LLP driver for the least mincut satisfying a lattice-linear side predicate.

import java.util.*;

public class LLPMincut {
  public boolean[] LLPMincut(boolean[] G) {
    int n = G.length;
    boolean done = false;
    while ((!done)) {
      boolean changed = true;
      while (changed) {
        changed = false;
        int j = 0;
        while ((j < n)) {
          if (forbiddenForB(j, G)) {
            if (G[j]) {
              return null;
            }
            G[j] = true;
            changed = true;
          }
          j = (j + 1);
        }
      }
      if (Bcheck(G)) {
        done = true;
      } else {
        boolean[] next = nextMincut(G);
        if ((next == null)) {
          return null;
        }
        G = next;
      }
    }
    return G;
  }

  public boolean Bcheck(boolean[] G) {
    return true;
  }

  public boolean forbiddenForB(int j, boolean[] G) {
    return false;
  }

  public boolean[] nextMincut(boolean[] G) {
    return G;
  }

  public static void main(String[] args) {
    boolean[] G = new boolean[] {false, false, false, false, false, false, false, false};
    LLPMincut prog = new LLPMincut();
    boolean[] result = prog.LLPMincut(G);
    System.out.println(Arrays.toString(result));
  }
}