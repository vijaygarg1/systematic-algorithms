// Strassen's divide-and-conquer matrix multiplication over int[][].
// Splitting each n x n operand into four n/2 quadrants, the product needs
// only SEVEN recursive multiplications instead of the obvious eight, which
// is what drops the exponent from 3 to log2(7) ~ 2.81. The saving comes
// from M1..M7 below: each is one product of sums, and the four output
// quadrants are recovered from them by addition alone.
//
// Sizes are assumed to be powers of two, so every split is exact.

import java.util.*;

public class Strassen {
  public int[][] Strassen(int[][] A, int[][] B) {
    int n = A.length;
    if ((n == 1)) {
      int[][] C1 = new int[1][1];
      C1[0][0] = (A[0][0] * B[0][0]);
      return C1;
    }
    int h = (n / 2);
    int[][] A11 = quadrant(A, 0, 0, h);
    int[][] A12 = quadrant(A, 0, h, h);
    int[][] A21 = quadrant(A, h, 0, h);
    int[][] A22 = quadrant(A, h, h, h);
    int[][] B11 = quadrant(B, 0, 0, h);
    int[][] B12 = quadrant(B, 0, h, h);
    int[][] B21 = quadrant(B, h, 0, h);
    int[][] B22 = quadrant(B, h, h, h);
    int[][] M1 = Strassen(add(A11, A22), add(B11, B22));
    int[][] M2 = Strassen(add(A21, A22), B11);
    int[][] M3 = Strassen(A11, sub(B12, B22));
    int[][] M4 = Strassen(A22, sub(B21, B11));
    int[][] M5 = Strassen(add(A11, A12), B22);
    int[][] M6 = Strassen(sub(A21, A11), add(B11, B12));
    int[][] M7 = Strassen(sub(A12, A22), add(B21, B22));
    int[][] C11 = add(sub(add(M1, M4), M5), M7);
    int[][] C12 = add(M3, M5);
    int[][] C21 = add(M2, M4);
    int[][] C22 = add(add(sub(M1, M2), M3), M6);
    return combine(C11, C12, C21, C22);
  }

  public int[][] quadrant(int[][] M, int r, int c, int h) {
    int[][] Q = new int[h][h];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < h; j++) {
        Q[i][j] = M[(r + i)][(c + j)];
      }
    }
    return Q;
  }

  public int[][] add(int[][] X, int[][] Y) {
    int h = X.length;
    int[][] S = new int[h][h];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < h; j++) {
        S[i][j] = (X[i][j] + Y[i][j]);
      }
    }
    return S;
  }

  public int[][] sub(int[][] X, int[][] Y) {
    int h = X.length;
    int[][] D = new int[h][h];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < h; j++) {
        D[i][j] = (X[i][j] - Y[i][j]);
      }
    }
    return D;
  }

  public int[][] combine(int[][] C11, int[][] C12, int[][] C21, int[][] C22) {
    int h = C11.length;
    int[][] C = new int[(2 * h)][(2 * h)];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < h; j++) {
        {
          C[i][j] = C11[i][j];
          C[i][(j + h)] = C12[i][j];
          C[(i + h)][j] = C21[i][j];
          C[(i + h)][(j + h)] = C22[i][j];
        }
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] A = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] B = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    Strassen prog = new Strassen();
    int[][] result = prog.Strassen(A, B);
    System.out.println(Arrays.deepToString(result));
  }
}