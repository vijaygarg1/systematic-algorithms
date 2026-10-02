// ParallelCut: find the first consistent cut satisfying a conjunctive predicate.
// Five-step parallel algorithm using state rejection and transitive closure.

import java.util.*;

public class ParallelCut {
  public int[] ParallelCut(int[][] vc, int n, int m) {
    int[] F = new int[n];
    int i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < n)) {
        if (((i != j) && hb(vc, i, 0, j, 0, n))) {
          F[i] = 1;
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    int sz = (n * m);
    boolean[][] R = new boolean[sz][sz];
    int idx = 0;
    while ((idx < sz)) {
      R[idx][idx] = true;
      idx = (idx + 1);
    }
    i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < (m - 1))) {
        int ip = 0;
        while ((ip < n)) {
          if ((ip != i)) {
            int jp = 0;
            while ((jp < m)) {
              if (hb(vc, ip, jp, i, (j + 1), n)) {
                R[((i * m) + j)][((ip * m) + jp)] = true;
              }
              jp = (jp + 1);
            }
          }
          ip = (ip + 1);
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    transitiveClosure(R, sz);
    boolean[][] valid = new boolean[n][m];
    i = 0;
    while ((i < n)) {
      int j = 0;
      while ((j < m)) {
        valid[i][j] = true;
        j = (j + 1);
      }
      i = (i + 1);
    }
    i = 0;
    while ((i < n)) {
      if ((F[i] == 1)) {
        int ip = 0;
        while ((ip < n)) {
          int jp = 0;
          while ((jp < m)) {
            if (R[((i * m) + 0)][((ip * m) + jp)]) {
              valid[ip][jp] = false;
            }
            jp = (jp + 1);
          }
          ip = (ip + 1);
        }
      }
      i = (i + 1);
    }
    int[] cut = new int[n];
    i = 0;
    while ((i < n)) {
      cut[i] = (0 - 1);
      int j = 0;
      while ((j < m)) {
        if (valid[i][j]) {
          if (((j == 0) || (!valid[i][(j - 1)]))) {
            cut[i] = j;
            j = m;
          }
        }
        j = (j + 1);
      }
      i = (i + 1);
    }
    return cut;
  }

  public boolean hb(int[][] vc, int pi, int si, int pj, int sj, int n) {
    int idx1 = ((pi * n) + si);
    int idx2 = ((pj * n) + sj);
    return ((vc[idx1][pj] >= sj) && (!((pi == pj) && (si == sj))));
  }

  public void transitiveClosure(boolean[][] R, int sz) {
    int k = 0;
    while ((k < sz)) {
      int i = 0;
      while ((i < sz)) {
        int j = 0;
        while ((j < sz)) {
          if ((R[i][k] && R[k][j])) {
            R[i][j] = true;
          }
          j = (j + 1);
        }
        i = (i + 1);
      }
      k = (k + 1);
    }
  }

  public static void main(String[] args) {
    int[][] vc = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int n = 0;
    int m = 0;
    ParallelCut prog = new ParallelCut();
    int[] result = prog.ParallelCut(vc, n, m);
    System.out.println(Arrays.toString(result));
  }
}