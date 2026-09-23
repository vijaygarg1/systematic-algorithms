"""Return the largest element of A."""

def max_element(A):
    best = A[0]
    for x in A[1:]:
        if x > best:
            best = x
    return best


if __name__ == "__main__":
    A = [4, 2, 7, 1, 9, 3, 8]
    print(f"max({A}) = {max_element(A)}")
