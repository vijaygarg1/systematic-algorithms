// LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each fraction G[j] in [0,1] to its target G*[j] derived from prefix-sum of weights vs capacity W.

import java.util.*;

public class LLPFractionalKnapsack {
  int n;
  double[] v;
  double[] w;
  double W;
  double[] G;
  int j;

  private boolean forbidden(int j) {
    if (!((G[j] < target(j)))) return false;
    return true;
  }

  private void advance() {
    G[j] = target(j);
  }

  public double[] LLPFractionalKnapsack(double[] v, double[] w, double W) {
    this.v = v;
    this.w = w;
    this.W = W;
    this.n = v.length;
    this.G = new double[n];
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (forbidden(j)) {
            this.j = j; advance();
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public double target(int j) {
    double prev = prefixWeight((j - 1));
    double cur = (prev + w[j]);
    if ((cur <= W)) {
      return 1.0;
    } else {
      if ((prev >= W)) {
        return 0.0;
      } else {
        return ((W - prev) / w[j]);
      }
    }
  }

  public double prefixWeight(int upto) {
    double s = 0.0;
    int i = 0;
    while ((i <= upto)) {
      s = (s + w[i]);
      i = (i + 1);
    }
    return s;
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