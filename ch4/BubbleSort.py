"""Bubble sort: repeated adjacent compare-and-swap until a clean pass."""

def sort(A):
    found = True
    while found:
        found = False
        j = 0
        while j < len(A) - 1:
            if A[j] > A[j + 1]:
                found = True
                A[j], A[j + 1] = A[j + 1], A[j]
            j += 1


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    sort(A)
    print(A)
