"""Euclid's GCD using the mod operation."""

def gcd(a, b):
    while a != b:
        if a > b:
            if a % b == 0:
                a = b
            else:
                a = a % b
        else:
            if b % a == 0:
                b = a
            else:
                b = b % a
    return a


if __name__ == "__main__":
    for a, b in [(48, 18), (100, 75), (17, 5), (12, 12)]:
        print(f"gcd({a}, {b}) = {gcd(a, b)}")
