"""Bitonic sorting network: ascending half + descending half + merge."""

def sort(A, low, n, direction):
    if n > 1:
        m = n // 2
        sort(A, low, m, 1)
        sort(A, low + m, m, 0)
        bitonic_merge(A, low, n, direction)


def bitonic_merge(A, low, n, direction):
    """Merges a bitonic A[low..low+n-1] into a monotonic sequence."""
    if n > 1:
        m = n // 2
        i = low
        while i < low + m:
            j = i + m
            if direction == 1 and A[i] > A[j]:
                A[i], A[j] = A[j], A[i]
            if direction == 0 and A[i] < A[j]:
                A[i], A[j] = A[j], A[i]
            i += 1
        bitonic_merge(A, low, m, direction)
        bitonic_merge(A, low + m, m, direction)


if __name__ == "__main__":
    A = [3, 7, 4, 8, 6, 2, 1, 5]
    sort(A, 0, len(A), 1)
    print(A)
