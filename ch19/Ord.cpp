// Ord: multiplicative order of a modulo n via descending LLP on
// divisor lattice.  G[i] = exponent of p[i] in the current candidate
// k = prod p[i]^G[i].

#include <iostream>
#include <vector>

long long modpow(long long base, long long exp, long long mod) {
    long long result = 1, b = base % mod;
    while (exp > 0) {
        if (exp & 1) result = result * b % mod;
        exp >>= 1;
        b = b * b % mod;
    }
    return result;
}

long long ord(long long a, long long n,
              const std::vector<long long>& p,
              const std::vector<int>& e) {
    int s = (int)p.size();
    std::vector<int> G(e);
    long long k = 1;
    for (int i = 0; i < s; ++i)
        for (int j = 0; j < e[i]; ++j) k *= p[i];

    bool changed = true;
    while (changed) {
        changed = false;
        for (int i = 0; i < s; ++i)
            if (G[i] > 0 && modpow(a, k / p[i], n) == 1) {
                --G[i];
                k /= p[i];
                changed = true;
            }
    }
    return k;
}

int main() {
    // Order of 2 mod 7: phi(7) = 6 = 2 * 3.
    std::vector<long long> p = {2, 3};
    std::vector<int> e = {1, 1};
    std::cout << "ord_7(2) = " << ord(2, 7, p, e) << '\n';
    return 0;
}
