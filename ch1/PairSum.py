"""Count pairs (i, j) with i < j and A[i] + A[j] = target."""

def pair_sum(A, target):
    count = 0
    for i in range(len(A)):
        for j in range(i + 1, len(A)):
            if A[i] + A[j] == target:
                count += 1
    return count


if __name__ == "__main__":
    A = [1, 5, 2, 7, 3, 4, 6]
    print(f"pair_sum({A}, 8) = {pair_sum(A, 8)}")
