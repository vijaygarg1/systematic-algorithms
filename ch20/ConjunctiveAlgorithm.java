// Conjunctive predicate detection: forbidden when G[j] -> G[i] (happened-before);
// advance increments G[j] to the next local state.

import java.util.*;

public class ConjunctiveAlgorithm {
  int n;
  private static final int[] _NO_EARLY_EXIT = new int[0];
  int[][][] vc;
  int[] T;
  int[] G;
  int j;

  private boolean _forbidden0(int j) {
    if (!(happenedBefore(j, G, vc))) return false;
    return true;
  }

  private int[] _advance0() {
    if ((G[j] >= T[j])) {
      return null;
    } else {
      G[j] = (G[j] + 1);
    }
    return _NO_EARLY_EXIT;
  }

  public int[] ConjunctiveAlgorithm(int[][][] vc, int[] T) {
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
            this.j = j; int[] _r = _advance0(); if (_r != _NO_EARLY_EXIT) return _r;
            changed = true;
          }
        }
      }
    }
    return G;
  }

  public boolean happenedBefore(int j, int[] G, int[][][] vc) {
    int n = G.length;
    int i = 0;
    while ((i < n)) {
      if (((i != j) && (vc[i][G[i]][j] >= G[j]))) {
        return true;
      }
      i = (i + 1);
    }
    return false;
  }

  public static void main(String[] args) {
    // No runnable example: ConjunctiveAlgorithm's parameters include a type
    // this synthesizer cannot safely construct on its own (e.g. set<int>[],
    // a 3D+ array, or another unsupported shape) -- not a compile error, just
    // nothing to call here. See the .llp source for the real signature, and
    // construct valid inputs by hand to exercise this method.
  }
}