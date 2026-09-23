"""Fractional knapsack: greedy by value-density v/w (last item may be split)."""

def solve(v, w, W):
    n = len(v)
    x = [0.0] * n
    rem = W
    for i in range(n):
        if w[i] <= rem:
            x[i] = 1.0
            rem -= w[i]
        else:
            x[i] = rem / w[i]
            break
    return x


if __name__ == "__main__":
    v = [60, 100, 120, 50]
    w = [10, 20, 30, 40]
    W = 50
    x = solve(v, w, W)
    total = sum(xi * vi for xi, vi in zip(x, v))
    print("fractions:", x)
    print(f"total value: {total}")
