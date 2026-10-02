"""LLP-Sort1 with alternating odd/even-phase scheduling for parallelism."""

def forbidden(j, A):
    return j < len(A) - 1 and A[j] > A[j + 1]


def advance(j, A):
    A[j], A[j + 1] = A[j + 1], A[j]


def llp_oddeven_sort(A):
    changed = True
    while changed:
        changed = False
        for j in range(1, len(A) - 1, 2):  # odd-indexed pass
            if forbidden(j, A):
                advance(j, A)
                changed = True
        for j in range(0, len(A) - 1, 2):  # even-indexed pass
            if forbidden(j, A):
                advance(j, A)
                changed = True


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    llp_oddeven_sort(A)
    print(A)
