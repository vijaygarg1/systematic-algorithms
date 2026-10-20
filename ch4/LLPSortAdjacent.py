"""LLP-Sort1 (transposition form): forbidden = inversion, advance = swap."""

def forbidden(j, A):
    return j < len(A) - 1 and A[j] > A[j + 1]


def advance(j, A):
    A[j], A[j + 1] = A[j + 1], A[j]


def llp_sort1(A):
    changed = True
    while changed:
        changed = False
        for j in range(len(A) - 1):
            if forbidden(j, A):
                advance(j, A)
                changed = True


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    llp_sort1(A)
    print(A)
