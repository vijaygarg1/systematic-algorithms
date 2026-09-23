// Strassen's matrix multiplication: 7 recursive products instead of 8.
#include <vector>
#include <iostream>
using namespace std;
typedef vector<vector<int>> Matrix;

Matrix add(const Matrix& X, const Matrix& Y) {
  int n = X.size();
  Matrix R(n, vector<int>(n));
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      R[i][j] = X[i][j] + Y[i][j];
  return R;
}

Matrix sub(const Matrix& X, const Matrix& Y) {
  int n = X.size();
  Matrix R(n, vector<int>(n));
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      R[i][j] = X[i][j] - Y[i][j];
  return R;
}

Matrix block(const Matrix& M, int r, int c, int size) {
  Matrix R(size, vector<int>(size));
  for (int i = 0; i < size; i++)
    for (int j = 0; j < size; j++)
      R[i][j] = M[r + i][c + j];
  return R;
}

Matrix combine(const Matrix& C11, const Matrix& C12, const Matrix& C21, const Matrix& C22) {
  int h = C11.size(), n = 2 * h;
  Matrix C(n, vector<int>(n));
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

Matrix multiply(const Matrix& A, const Matrix& B, int n) {
  if (n == 1) {
    return Matrix(1, vector<int>(1, A[0][0] * B[0][0]));
  }
  int h = n / 2;
  Matrix A11 = block(A, 0, 0, h), A12 = block(A, 0, h, h);
  Matrix A21 = block(A, h, 0, h), A22 = block(A, h, h, h);
  Matrix B11 = block(B, 0, 0, h), B12 = block(B, 0, h, h);
  Matrix B21 = block(B, h, 0, h), B22 = block(B, h, h, h);

  Matrix M1 = multiply(add(A11, A22), add(B11, B22), h);
  Matrix M2 = multiply(add(A21, A22), B11, h);
  Matrix M3 = multiply(A11, sub(B12, B22), h);
  Matrix M4 = multiply(A22, sub(B21, B11), h);
  Matrix M5 = multiply(add(A11, A12), B22, h);
  Matrix M6 = multiply(sub(A21, A11), add(B11, B12), h);
  Matrix M7 = multiply(sub(A12, A22), add(B21, B22), h);

  Matrix C11 = add(sub(add(M1, M4), M5), M7);
  Matrix C12 = add(M3, M5);
  Matrix C21 = add(M2, M4);
  Matrix C22 = add(sub(add(M1, M3), M2), M6);
  return combine(C11, C12, C21, C22);
}

int main() {
  Matrix A = {{1, 2}, {3, 4}};
  Matrix B = {{5, 6}, {7, 8}};
  Matrix C = multiply(A, B, 2);
  for (auto& row : C) {
    for (int v : row) cout << v << " ";
    cout << "\n";
  }
  return 0;
}
