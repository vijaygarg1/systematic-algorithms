// ResourceBound: composition program enforcing rho[j] <= B.

import java.util.*;

public class ResourceBound {
  public int[] run(int[] rho, int B, int[] G) {
    for (int j = 0; j < rho.length; j++) if (rho[j] > B) return null;
    return G;
  }

  public static void main(String[] args) {
    int[] rho = {0, 2, 5, 9};
    int[] G   = {0, 4, 8, 15};
    int[] out = new ResourceBound().run(rho, 7, G);
    System.out.println(out == null ? "infeasible" : Arrays.toString(out));
  }
}
