"""Insertion sort: walk each new element down to its place."""

def sort(A):
    i = 1
    while i < len(A):
        j = i - 1
        done = False
        while j >= 0 and not done:
            if A[j] <= A[j + 1]:
                done = True
            else:
                A[j], A[j + 1] = A[j + 1], A[j]
                j -= 1
        i += 1


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    sort(A)
    print(A)
