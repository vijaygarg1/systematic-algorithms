"""Compute n! via straight recursion."""

def factorial(n):
    if n == 0:
        return 1
    return n * factorial(n - 1)


if __name__ == "__main__":
    for n in range(7):
        print(f"{n}! = {factorial(n)}")
