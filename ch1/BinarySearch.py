"""Return any index of key in a sorted array A, or -1 if absent."""

def binary_search(A, key, low, high):
    if low > high:
        return -1
    mid = (low + high) // 2
    if A[mid] == key:
        return mid
    if A[mid] > key:
        return binary_search(A, key, low, mid - 1)
    return binary_search(A, key, mid + 1, high)


if __name__ == "__main__":
    A = [1, 3, 5, 7, 9, 11, 13, 15]
    for key in [7, 1, 15, 4, 16]:
        print(f"key={key}: index = {binary_search(A, key, 0, len(A) - 1)}")
