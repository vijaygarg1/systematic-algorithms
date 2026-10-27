// Classical O(n²) LIS DP.

import java.util.*;

public class LIS {
  public int[] solve(int[] A) {
    int n = A.length;
    int[] G = new int[n];
    for (int i = 0; i < n; i++) {
      G[i] = 1;
    }
    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if ((A[j] < A[i])) {
          G[i] = Math.max(G[i], (G[j] + 1));
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LIS prog = new LIS();
    int[] result = prog.solve(A);
    System.out.println(Arrays.toString(result));
  }
}