# Strassen's matrix multiplication: 7 recursive products instead of 8.

def add(X, Y):
    n = len(X)
    return [[X[i][j] + Y[i][j] for j in range(n)] for i in range(n)]

def sub(X, Y):
    n = len(X)
    return [[X[i][j] - Y[i][j] for j in range(n)] for i in range(n)]

def split(M, n):
    h = n // 2
    A11 = [row[:h] for row in M[:h]]
    A12 = [row[h:] for row in M[:h]]
    A21 = [row[:h] for row in M[h:]]
    A22 = [row[h:] for row in M[h:]]
    return A11, A12, A21, A22

def combine(C11, C12, C21, C22):
    h = len(C11)
    C = [[0] * (2 * h) for _ in range(2 * h)]
    for i in range(h):
        for j in range(h):
            C[i][j] = C11[i][j]
            C[i][j + h] = C12[i][j]
            C[i + h][j] = C21[i][j]
            C[i + h][j + h] = C22[i][j]
    return C

def multiply(A, B, n):
    if n == 1:
        return [[A[0][0] * B[0][0]]]
    h = n // 2
    A11, A12, A21, A22 = split(A, n)
    B11, B12, B21, B22 = split(B, n)

    M1 = multiply(add(A11, A22), add(B11, B22), h)
    M2 = multiply(add(A21, A22), B11, h)
    M3 = multiply(A11, sub(B12, B22), h)
    M4 = multiply(A22, sub(B21, B11), h)
    M5 = multiply(add(A11, A12), B22, h)
    M6 = multiply(sub(A21, A11), add(B11, B12), h)
    M7 = multiply(sub(A12, A22), add(B21, B22), h)

    C11 = add(sub(add(M1, M4), M5), M7)
    C12 = add(M3, M5)
    C21 = add(M2, M4)
    C22 = add(sub(add(M1, M3), M2), M6)
    return combine(C11, C12, C21, C22)

if __name__ == "__main__":
    A = [[1, 2], [3, 4]]
    B = [[5, 6], [7, 8]]
    C = multiply(A, B, 2)
    for row in C:
        print(" ".join(str(v) for v in row))
