"""Return the first index i with A[i] = key, or -1 if absent."""

def linear_search(A, key):
    for i, x in enumerate(A):
        if x == key:
            return i
    return -1


if __name__ == "__main__":
    A = [4, 2, 7, 1, 9, 3]
    for key in [7, 4, 5, 9]:
        print(f"key={key}: index = {linear_search(A, key)}")
