// Karatsuba multiplication: three half-size products instead of four.

#include <iostream>

long long pow10ll(int k) {
    long long r = 1;
    for (int i = 0; i < k; ++i) r *= 10;
    return r;
}

long long multiply(long long X, long long Y, int n) {
    if (n == 1) return X * Y;
    int half = n / 2;
    long long divisor = pow10ll(half);
    long long x1 = X / divisor, x0 = X - x1 * divisor;
    long long y1 = Y / divisor, y0 = Y - y1 * divisor;
    long long p1 = multiply(x0, y0, half);
    long long p2 = multiply(x1, y1, half);
    long long p3 = multiply(x0 + x1, y0 + y1, half);
    long long middle = p3 - p1 - p2;
    return p2 * pow10ll(n) + middle * divisor + p1;
}

int main() {
    struct C { long long x, y; int n; };
    for (auto t : {C{1234, 5678, 4}, C{12, 34, 2}, C{8, 9, 1}}) {
        long long got = multiply(t.x, t.y, t.n);
        std::cout << t.x << " * " << t.y << " = " << got
                  << " (expected " << t.x * t.y << ")\n";
    }
    return 0;
}
