// LLP-GCD: greatest common divisor of two numbers via Euclidean
// reduction, matching bxx-numberTheory.tex Algorithm LLP-GCD. G is the
// two-element vector (a, b); forbidden picks any entry strictly larger
// than another, and advance replaces it by its remainder mod the smaller
// (or by the smaller itself when it divides exactly, so entries stay at

import java.util.*;

public class LLPGCD {
  int n;
  int a;
  int b;
  int[] G;
  int j;
  int picked_i;

  private boolean _forbidden0(int j) {
    for (int i = 0; i <= 1; i++) {
      if ((G[j] > G[i])) { this.picked_i = i; return true; }
    }
    return false;
  }

  private void _advance0() {
    int i = picked_i;
    if (((G[j] % G[i]) == 0)) {
      G[j] = G[i];
    } else {
      G[j] = (G[j] % G[i]);
    }
  }

  public int LLPGCD(int a, int b) {
    this.a = a;
    this.b = b;
    this.G = new int[2];
    n = 2;
    G[0] = a;
    G[1] = b;
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
    return G[0];
  }

  public static void main(String[] args) {
    int a = 0;
    int b = 0;
    LLPGCD prog = new LLPGCD();
    int result = prog.LLPGCD(a, b);
    System.out.println(result);
  }
}