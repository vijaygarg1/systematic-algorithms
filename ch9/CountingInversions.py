"""Count inversions in O(n log n) via merge sort."""

def count(A, low, high):
    if low >= high:
        return 0
    mid = (low + high) // 2
    inv_left  = count(A, low, mid)
    inv_right = count(A, mid + 1, high)
    inv_merge = merge_and_count(A, low, mid, high)
    return inv_left + inv_right + inv_merge


def merge_and_count(A, low, mid, high):
    B = list(A)
    i, j, k = low, mid + 1, low
    inv = 0
    while i <= mid and j <= high:
        if B[i] <= B[j]:
            A[k] = B[i]; i += 1
        else:
            A[k] = B[j]; j += 1
            inv += (mid - i + 1)
        k += 1
    while i <= mid:
        A[k] = B[i]; i += 1; k += 1
    while j <= high:
        A[k] = B[j]; j += 1; k += 1
    return inv


if __name__ == "__main__":
    A = [5, 2, 4, 6, 1, 3]
    inv = count(A, 0, len(A) - 1)
    print(f"sorted: {A}, inversions: {inv}")
