"""Σ_{i,j < n} i*j computed by a doubly-nested loop."""

def nested_sum(n):
    total = 0
    for i in range(n):
        for j in range(n):
            total += i * j
    return total


if __name__ == "__main__":
    for n in [0, 1, 5, 10]:
        print(f"nested_sum({n}) = {nested_sum(n)}")
