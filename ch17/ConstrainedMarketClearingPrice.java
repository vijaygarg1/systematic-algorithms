// LLP market clearing price: forbidden when item j is in a minimal
// overdemanded set; advance raises its price by 1.

import java.util.*;

public class ConstrainedMarketClearingPrice {
  int n;
  int[][] v;
  int m;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    if (!(isOverDemanded(j, v, G))) return false;
    return true;
  }

  private void _advance0() {
    G[j] = (G[j] + 1);
  }

  public int[] ConstrainedMarketClearingPrice(int[][] v) {
    this.v = v;
    this.n = v[0].length;
    this.m = v.length;
    this.G = new int[n];
    for (int k = 0; k < n; k++) {
      G[k] = 0;
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

  public boolean isOverDemanded(int j, int[][] v, int[] G) {
    int n = G.length;
    int m = v.length;
    int demandCount = 0;
    int b = 0;
    while ((b < m)) {
      int best = (v[b][j] - G[j]);
      boolean isBest = true;
      int i = 0;
      while ((i < n)) {
        if (((v[b][i] - G[i]) > best)) {
          isBest = false;
        }
        i = (i + 1);
      }
      if (isBest) {
        demandCount = (demandCount + 1);
      }
      b = (b + 1);
    }
    return (demandCount > 1);
  }

  public static void main(String[] args) {
    int[][] v = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    ConstrainedMarketClearingPrice prog = new ConstrainedMarketClearingPrice();
    int[] result = prog.ConstrainedMarketClearingPrice(v);
    System.out.println(Arrays.toString(result));
  }
}