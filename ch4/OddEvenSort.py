"""Odd-Even Sort: alternating odd- and even-indexed compare-swap passes."""

def sort(A):
    found = True
    while found:
        found = False
        # Odd-indexed pass: j = 1, 3, 5, ...
        j = 1
        while j < len(A) - 1:
            if A[j] > A[j + 1]:
                found = True
                A[j], A[j + 1] = A[j + 1], A[j]
            j += 2
        # Even-indexed pass: j = 0, 2, 4, ...
        j = 0
        while j < len(A) - 1:
            if A[j] > A[j + 1]:
                found = True
                A[j], A[j + 1] = A[j + 1], A[j]
            j += 2


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    sort(A)
    print(A)
