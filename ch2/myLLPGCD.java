import java.util.*;

public class myLLPGCD {
  int n;
  int[] A;
  int[] G;
  int pickedI = -1;

  private boolean forbidden(int j) {
    for (int i = 1; i <= n; i++) {
      if ((G[j] > G[i])) { pickedI = i; return true;
    }
    return false;
  }

  private void advance(int j) {
    int i = pickedI;
    if (((G[j] % G[i]) == 0)) {
      G[j] = G[i];
    } else {
      G[j] = (G[j] % G[i]);
    }
  }

  public int[] LLPGCD(int[] A) {
    this.A = A;
    this.n = A.length - 1;
    if (n < 0) n = 0;
    this.G = new int[n + 1];
    for (int k = 0; k <= n; k++) {
      G[k] = A[k];
    }
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int j = 1; j <= n; j++) {
        if (forbidden(j)) { advance(j); changed = true;}
      }
     }
    return G;
  }

  public static void main(String[] args) {
    // Demo harness for LLPGCD.
    // Construct with inputs from System.in or hard-coded data,
    // then call run() and print output fields.
  }
}
