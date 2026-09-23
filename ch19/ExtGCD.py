"""Extended Euclidean algorithm: maintains Bezout coefficients alongside GCD reduction."""


def ext_gcd(a, b):
    G = [a, b]
    H = [[1, 0], [0, 1]]
    while G[0] != G[1]:
        if G[0] > G[1]:
            j, i = 0, 1
        else:
            j, i = 1, 0
        if G[i] != 0 and G[j] % G[i] == 0:
            q = G[j] // G[i] - 1
        else:
            q = G[j] // G[i]
        G[j] -= q * G[i]
        H[j][0] -= q * H[i][0]
        H[j][1] -= q * H[i][1]
    return G[0], H[0][0], H[0][1]


if __name__ == "__main__":
    for a, b in [(30, 18), (48, 18), (35, 15)]:
        d, x, y = ext_gcd(a, b)
        print(f"ext_gcd({a}, {b}) = (d={d}, x={x}, y={y})  check: {a}*{x} + {b}*{y} = {a*x + b*y}")
