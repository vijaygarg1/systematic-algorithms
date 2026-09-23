"""Karatsuba multiplication: three half-size products instead of four."""

def multiply(X, Y, n):
    if n == 1:
        return X * Y
    half = n // 2
    divisor = 10 ** half
    x1, x0 = divmod(X, divisor)
    y1, y0 = divmod(Y, divisor)
    p1 = multiply(x0, y0, half)
    p2 = multiply(x1, y1, half)
    p3 = multiply(x0 + x1, y0 + y1, half)
    middle = p3 - p1 - p2
    return p2 * (10 ** n) + middle * divisor + p1


if __name__ == "__main__":
    for X, Y, n in [(1234, 5678, 4), (12, 34, 2), (8, 9, 1)]:
        got = multiply(X, Y, n)
        print(f"{X} * {Y} = {got} (expected {X * Y})")
