// Extended Euclidean algorithm: maintains Bezout coefficients
// alongside GCD reduction.  Returns {gcd, x, y} with x*a + y*b = gcd.

#include <iostream>
#include <vector>
#include <array>

std::array<long long, 3> extGCD(long long a, long long b) {
    long long G[2] = { a, b };
    long long H0[2] = { 1, 0 }, H1[2] = { 0, 1 };
    while (G[0] != G[1]) {
        if (G[0] > G[1]) {
            long long q = (G[1] != 0 && G[0] % G[1] == 0) ? G[0] / G[1] - 1 : G[0] / G[1];
            G[0]  -= q * G[1];
            H0[0] -= q * H1[0];
            H0[1] -= q * H1[1];
        } else {
            long long q = (G[0] != 0 && G[1] % G[0] == 0) ? G[1] / G[0] - 1 : G[1] / G[0];
            G[1]  -= q * G[0];
            H1[0] -= q * H0[0];
            H1[1] -= q * H0[1];
        }
    }
    return { G[0], H0[0], H0[1] };
}

int main() {
    auto r = extGCD(48, 18);
    std::cout << "gcd=" << r[0] << "  x=" << r[1] << "  y=" << r[2] << '\n';
    return 0;
}
