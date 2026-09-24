// LLP pointer-jumping kernel of Borůvka's algorithm.

import java.util.*;

public class LLPBoruvka {
  int n;
  int[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] != G[G[j]]))) return false;
    return true;
  }

  private void advance() {
    G[j] = G[G[j]];
  }

  public void LLPBoruvka(int[] G) {
    this.G = G;
    this.n = G.length;
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
  }

  public static void main(String[] args) {
    int[] G = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    LLPBoruvka prog = new LLPBoruvka();
    prog.LLPBoruvka(G);
    System.out.println(Arrays.toString(G));
  }
}