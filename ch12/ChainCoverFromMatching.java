// Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping.

import java.util.*;

public class ChainCoverFromMatching {
  public int[] ChainCoverFromMatching(int[] matchPartner) {
    int n = matchPartner.length;
    int[] C = new int[n];
    int i = 0;
    while ((i < n)) {
      C[i] = i;
      i = (i + 1);
    }
    int u = 0;
    while ((u < n)) {
      int v = matchPartner[u];
      if ((v != (0 - 1))) {
        C[u] = v;
      }
      u = (u + 1);
    }
    parentPointerJumping(C);
    return C;
  }

  public void parentPointerJumping(int[] C) {
    int n = C.length;
    boolean changed = true;
    while (changed) {
      changed = false;
      int i = 0;
      while ((i < n)) {
        int p = C[i];
        if ((C[p] != p)) {
          C[i] = C[p];
          changed = true;
        }
        i = (i + 1);
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