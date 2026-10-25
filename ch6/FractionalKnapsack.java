// Fractional knapsack: greedy by value-density v/w (last item may be split).

import java.util.*;

public class FractionalKnapsack {
  public double[] solve(double[] v, double[] w, double W) {
    int n = v.length;
    double[] x = new double[n];
    double rem = W;
    for (int i = 0; i < n; i++) {
      {
        if ((w[i] <= rem)) {
          x[i] = 1.0;
          rem = (rem - w[i]);
        } else {
          x[i] = (rem / w[i]);
          break;
        }
      }
    }
    return x;
  }

  public static void main(String[] args) {
    double[] v = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double W = 10.0;
    FractionalKnapsack prog = new FractionalKnapsack();
    double[] result = prog.solve(v, w, W);
    System.out.println(Arrays.toString(result));
  }
}