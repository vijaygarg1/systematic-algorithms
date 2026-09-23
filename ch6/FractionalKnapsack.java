// Fractional knapsack: greedy by value-density v/w (last item may be split).

import java.util.*;

public class FractionalKnapsack {
  public double[] solve(double[] v, double[] w, double W) {
    int n = v.length;
    double[] x = new double[n];
    double rem = W;
    boolean done = false;
    int i = 0;
    while (((i < n) && (!done))) {
      if ((w[i] <= rem)) {
        x[i] = 1.0;
        rem = (rem - w[i]);
      } else {
        x[i] = (rem / w[i]);
        done = true;
      }
      i = (i + 1);
    }
    return x;
  }

  public static void main(String[] args) {
    double[] v = new double[] {1.0, 2.0, 3.0, 4.0};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0};
    double W = 10.0;
    FractionalKnapsack prog = new FractionalKnapsack();
    double[] result = prog.solve(v, w, W);
    System.out.println(Arrays.toString(result));
  }
}