// Conjunctive predicate detection: forbidden when G[j] -> G[i] (happened-before);
// advance increments G[j] to the next local state.

import java.util.*;

public class ConjunctiveAlgorithm {
  int n;
  int[][] vc;
  int[] T;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    if (!(happenedBefore(j, G, vc))) return false;
    return true;
  }

  private int[] _advance0() {
    if ((G[j] >= T[j])) {
      return G;
    } else {
      G[j] = (G[j] + 1);
    }
    return null;
  }

  public int[] ConjunctiveAlgorithm(int[][] vc, int[] T) {
    this.vc = vc;
    this.T = T;
    this.n = T.length;
    this.n = vc.length;
    this.G = new int[n];
    for (int k = 0; k < n; k++) {
      G[k] = 1;
    }
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int j = 0; j < n; j++) {
          if (_forbidden0(j)) {
            this.j = j; int[] _r = _advance0(); if (_r != null) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean happenedBefore(int j, int[] G, int[][] vc) {
    int n = G.length;
    int i = 0;
    while ((i < n)) {
      if (((i != j) && (vc[((j * n) + G[j])][i] >= G[i]))) {
        return true;
      }
      i = (i + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    int[][] vc = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] T = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    ConjunctiveAlgorithm prog = new ConjunctiveAlgorithm();
    int[] result = prog.ConjunctiveAlgorithm(vc, T);
    System.out.println(Arrays.toString(result));
  }
}