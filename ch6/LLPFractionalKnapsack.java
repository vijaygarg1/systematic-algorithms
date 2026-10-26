// LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each
// fraction G[j] in [0,1] to its target G*[j], read in O(1) from one
// prefix scan of the weights.

import java.util.*;

public class LLPFractionalKnapsack {
  int n;
  double[] v;
  double[] w;
  double W;
  double[] G;
  double[] S;
  int i;
  int j;

  private boolean _forbidden0(int j) {
    if (!((G[j] < target(j)))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = target(j);
  }

  public double[] LLPFractionalKnapsack(double[] v, double[] w, double W) {
    this.v = v;
    this.w = w;
    this.W = W;
    this.n = v.length;
    this.G = new double[n];
    this.S = new double[(n + 1)];
    this.i = 0;
    while ((i < n)) {
      S[(i + 1)] = (S[i] + w[i]);
      i = (i + 1);
    }
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

  public double target(int j) {
    if ((S[(j + 1)] <= W)) {
      return 1.0;
    }
    if ((S[j] >= W)) {
      return 0.0;
    }
    return ((W - S[j]) / w[j]);
  }

  public static void main(String[] args) {
    double[] v = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double[] w = new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    double W = 10.0;
    LLPFractionalKnapsack prog = new LLPFractionalKnapsack();
    double[] result = prog.LLPFractionalKnapsack(v, w, W);
    System.out.println(Arrays.toString(result));
  }
}