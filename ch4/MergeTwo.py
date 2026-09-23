"""Merge two sorted arrays B and C into a new sorted D."""

def merge(B, C):
    m = len(B)
    n = len(C)
    D = [0] * (m + n)
    i = 0
    j = 0
    k = 0
    while i < m and j < n:
        if B[i] < C[j]:
            D[k] = B[i]
            i += 1
        else:
            D[k] = C[j]
            j += 1
        k += 1
    while i < m:
        D[k] = B[i]
        i += 1
        k += 1
    while j < n:
        D[k] = C[j]
        j += 1
        k += 1
    return D


if __name__ == "__main__":
    print(merge([1, 4, 7], [2, 3, 5, 8]))
