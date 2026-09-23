// VertexImplications: if v is in the cover and (v,w) is an implication,
// then w must also be in the cover.
// forbidden(w): exists v with (v,w) in I and G[v]=1 and G[w]=0.
// advance: G[w] := 1.

import java.util.*;

public class VertexImplications {
  int n;
  int[][] I;
  boolean[] G;
  int w;

  private boolean forbidden(int w) {
    boolean t1 = false;
    for (int v = 0; v < n; v++) {
      if ((((I[v][w] == 1) && G[v]) && (!G[w]))) { t1 = true; break; }
    }
    return t1;
  }

  private void advance() {
    G[w] = true;
  }

  public void VertexImplications(int[][] I, boolean[] G) {
    this.I = I;
    this.G = G;
    this.n = G.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int w = 0; w < n; w++) {
          if (forbidden(w)) {
            this.w = w; advance();
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[][] I = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    boolean[] G = new boolean[] {false, false, false, false};
    VertexImplications prog = new VertexImplications();
    prog.VertexImplications(I, G);
    System.out.println(Arrays.deepToString(I));
    System.out.println(Arrays.toString(G));
  }
}