// Strassen's matrix multiplication: 7 recursive products instead of 8.

public class Strassen {
  public int[][] multiply(int[][] A, int[][] B, int n) {
    if (n == 1) {
      return new int[][] { { A[0][0] * B[0][0] } };
    }
    int h = n / 2;
    int[][] A11 = sub(A, 0, 0, h), A12 = sub(A, 0, h, h);
    int[][] A21 = sub(A, h, 0, h), A22 = sub(A, h, h, h);
    int[][] B11 = sub(B, 0, 0, h), B12 = sub(B, 0, h, h);
    int[][] B21 = sub(B, h, 0, h), B22 = sub(B, h, h, h);

    int[][] M1 = multiply(add(A11, A22), add(B11, B22), h);
    int[][] M2 = multiply(add(A21, A22), B11, h);
    int[][] M3 = multiply(A11, subtract(B12, B22), h);
    int[][] M4 = multiply(A22, subtract(B21, B11), h);
    int[][] M5 = multiply(add(A11, A12), B22, h);
    int[][] M6 = multiply(subtract(A21, A11), add(B11, B12), h);
    int[][] M7 = multiply(subtract(A12, A22), add(B21, B22), h);

    int[][] C11 = add(subtract(add(M1, M4), M5), M7);
    int[][] C12 = add(M3, M5);
    int[][] C21 = add(M2, M4);
    int[][] C22 = add(subtract(add(M1, M3), M2), M6);
    return combine(C11, C12, C21, C22);
  }

  private int[][] sub(int[][] M, int r, int c, int size) {
    int[][] R = new int[size][size];
    for (int i = 0; i < size; i++)
      for (int j = 0; j < size; j++)
        R[i][j] = M[r + i][c + j];
    return R;
  }

  private int[][] add(int[][] X, int[][] Y) {
    int n = X.length;
    int[][] R = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        R[i][j] = X[i][j] + Y[i][j];
    return R;
  }

  private int[][] subtract(int[][] X, int[][] Y) {
    int n = X.length;
    int[][] R = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        R[i][j] = X[i][j] - Y[i][j];
    return R;
  }

  private int[][] combine(int[][] C11, int[][] C12, int[][] C21, int[][] C22) {
    int h = C11.length, n = 2 * h;
    int[][] C = new int[n][n];
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < h; j++) {
        C[i][j] = C11[i][j];
        C[i][j + h] = C12[i][j];
        C[i + h][j] = C21[i][j];
        C[i + h][j + h] = C22[i][j];
      }
    }
    return C;
  }

  public static void main(String[] args) {
    int[][] A = { { 1, 2 }, { 3, 4 } };
    int[][] B = { { 5, 6 }, { 7, 8 } };
    Strassen prog = new Strassen();
    int[][] C = prog.multiply(A, B, 2);
    for (int[] row : C) {
      StringBuilder sb = new StringBuilder();
      for (int v : row) sb.append(v).append(" ");
      System.out.println(sb.toString().trim());
    }
  }
}
