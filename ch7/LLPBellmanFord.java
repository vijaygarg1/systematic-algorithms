// LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
// The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
// recomputes every other G[k] from its predecessors, as in the book's box.
//
// The book writes the weight as w(i,j), with parentheses, to say that it
// comes from the edge and not from an n x n array -- an array would cost
// Theta(n^2) to build before Bellman-Ford's own O(nm) could start. So the
// edges arrive as two parallel ragged arrays: pre[j] lists j's predecessors
// and pw[j][t] is the weight of the edge pre[j][t] -> j. Both are O(m).
// LLPJohnson.llp uses the same pair.
//
// G is double, so an unreached predecessor contributes infinity + w =
// infinity under IEEE arithmetic and never wins the min. (The int version
// of this pattern overflows instead -- see LLPBFS.llp.)

import java.util.*;

public class LLPBellmanFord {
  int n;
  int[][] pre;
  int[][] pw;
  double[] G;
  int j;

  private boolean _forbidden0(int j) {
    boolean t1 = false;
    for (int t = 0; t < pre[j].length; t++) {
      if ((G[j] > (G[pre[j][t]] + pw[j][t]))) { t1 = true; break; }
    }
    return t1;
  }

  private void _advance0() {
    for (int k = 1; k < n; k++) {
      double t2 = Double.POSITIVE_INFINITY;
      for (int t = 0; t < pre[k].length; t++) t2 = Math.min(t2, (G[pre[k][t]] + pw[k][t]));
      G[k] = t2;
    }
  }

  public double[] LLPBellmanFord(int[][] pre, int[][] pw) {
    this.pre = pre;
    this.pw = pw;
    this.n = pre.length;
    this.G = new double[n];
    for (int i = 0; i < n; i++) this.G[i] = Double.POSITIVE_INFINITY;
    G[0] = 0;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; _advance0();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] pre = new int[][] {{}, {0}, {0, 1}, {0, 1, 2}, {0, 1, 2, 3}, {0, 1, 2, 3, 4}, {0, 1, 2, 3, 4, 5}, {0, 1, 2, 3, 4, 5, 6}};
    int[][] pw = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    LLPBellmanFord prog = new LLPBellmanFord();
    double[] result = prog.LLPBellmanFord(pre, pw);
    System.out.println(Arrays.toString(result));
  }
}