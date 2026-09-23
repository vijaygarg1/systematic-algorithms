"""Recursive merge sort: split, sort halves, merge."""

def merge_sort(A, low, high):
    if low < high:
        mid = (low + high) // 2
        merge_sort(A, low, mid)
        merge_sort(A, mid + 1, high)
        merge(A, low, mid, high)


def merge(A, low, mid, high):
    B = list(A)
    i, j, k = low, mid + 1, low
    while i <= mid and j <= high:
        if B[i] <= B[j]:
            A[k] = B[i]
            i += 1
        else:
            A[k] = B[j]
            j += 1
        k += 1
    while i <= mid:
        A[k] = B[i]
        i += 1
        k += 1
    while j <= high:
        A[k] = B[j]
        j += 1
        k += 1


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    merge_sort(A, 0, len(A) - 1)
    print(A)
