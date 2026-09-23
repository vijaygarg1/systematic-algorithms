"""Min-heap on an int[]: build, heapify, insert, extract-min."""

def heapify(A, i, n):
    smallest = i
    left = 2 * i + 1
    right = 2 * i + 2
    if left < n and A[left] < A[smallest]:
        smallest = left
    if right < n and A[right] < A[smallest]:
        smallest = right
    if smallest != i:
        A[i], A[smallest] = A[smallest], A[i]
        heapify(A, smallest, n)


def build(A, n):
    for i in range(n // 2 - 1, -1, -1):
        heapify(A, i, n)


def insert(A, key, n):
    n = n + 1
    i = n - 1
    while i > 0 and A[(i - 1) // 2] > key:
        A[i] = A[(i - 1) // 2]
        i = (i - 1) // 2
    A[i] = key
    return n


def extract_min(A, n):
    if n < 1:
        return None
    minimum = A[0]
    A[0] = A[n - 1]
    n -= 1
    heapify(A, 0, n)
    return minimum


if __name__ == "__main__":
    A = [9, 4, 7, 1, 8, 3, 6, 2, 5]
    n = len(A)
    build(A, n)
    print("after build:", A[:n])
    print("extract_min ->", extract_min(A, n))
    n -= 1
    print("after extract:", A[:n])
