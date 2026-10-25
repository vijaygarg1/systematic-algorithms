// Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping.

import java.util.*;

public class ChainCoverFromMatching {
  public int[] ChainCoverFromMatching(int[] matchPartner) {
    int n = matchPartner.length;
    int[] C = new int[n];
    for (int i = 0; i < n; i++) {
      {
        C[i] = i;
      }
    }
    for (int u = 0; u < n; u++) {
      {
        int v = matchPartner[u];
        if ((v != (0 - 1))) {
          C[u] = v;
        }
      }
    }
    parentPointerJumping(C);
    return C;
  }

  public void parentPointerJumping(int[] C) {
    int n = C.length;
    boolean changed = true;
    while (changed) {
      changed = false;
      for (int i = 0; i < n; i++) {
        {
          int p = C[i];
          if ((C[p] != p)) {
            C[i] = C[p];
            changed = true;
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] matchPartner = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    ChainCoverFromMatching prog = new ChainCoverFromMatching();
    int[] result = prog.ChainCoverFromMatching(matchPartner);
    System.out.println(Arrays.toString(result));
  }
}