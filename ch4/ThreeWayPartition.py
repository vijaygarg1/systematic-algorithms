"""Dutch-flag partition: split A[low..high) into <, =, > pivot regions."""

def partition(A, pivot, low, high):
    p = low
    q = low
    k = high
    while q < k:
        if A[q] < pivot:
            A[p], A[q] = A[q], A[p]
            p += 1
            q += 1
        elif A[q] > pivot:
            k -= 1
            A[q], A[k] = A[k], A[q]
        else:
            q += 1
    return p, q


if __name__ == "__main__":
    A = [3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5]
    p, q = partition(A, 5, 0, len(A))
    print(A, p, q)
