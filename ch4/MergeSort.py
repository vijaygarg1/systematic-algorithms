"""Sequential merge sort, in the book's shape: each call returns a newly
sorted list and the merge step is MergeTwo.

merge_two below is the same algorithm as MergeTwo.py; it is repeated here
rather than imported because every companion Python program has to run as
a single standalone file.
"""

def merge_two(B, C):
    m = len(B)
    n = len(C)
    D = [0] * (m + n)
    i = 0
    j = 0
    k = 0
    while i < m and j < n:
        # <= , not < : ties go to B, which is what makes the sort stable.
        if B[i] <= C[j]:
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


def sort(A, lo, hi):
    if lo < hi:
        mid = (lo + hi) // 2
        B = sort(A, lo, mid)
        C = sort(A, mid + 1, hi)
        return merge_two(B, C)
    return [A[lo]]


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    print(sort(A, 0, len(A) - 1))
