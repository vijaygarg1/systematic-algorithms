"""LLP-Sort2: forbidden when some k > j has A[j] > A[k]; swap them."""

def forbidden(j, A):
    """Returns the picked k (k > j with A[j] > A[k]) or -1 if none."""
    for k in range(j + 1, len(A)):
        if A[j] > A[k]:
            return k
    return -1


def advance(j, k, A):
    A[j], A[k] = A[k], A[j]


def llp_sort2(A):
    changed = True
    while changed:
        changed = False
        for j in range(len(A)):
            k = forbidden(j, A)
            if k >= 0:
                advance(j, k, A)
                changed = True


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    llp_sort2(A)
    print(A)
