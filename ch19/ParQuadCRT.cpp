// Par-QuadCRT: simultaneous quadratic congruences x^2 == a[j] (mod m[j]).
// Generalises Par-CRT to a non-linear local predicate.  G[j] is
// initialised to the smallest non-negative root of x^2 == a[j] (mod
// m[j]); the forbidden / advance pair drives every G[j] up to a
// common value that satisfies all r congruences simultaneously.

#include <iostream>
#include <vector>

std::vector<long long> parQuadCRT(const std::vector<long long>& m,
                                  const std::vector<long long>& /*a*/,
                                  const std::vector<long long>& roots) {
    int n = (int)m.size();
    std::vector<long long> G(roots);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int picked = -1;
            for (int i = 0; i < n; ++i)
                if (G[j] < G[i]) { picked = i; break; }
            if (picked == -1) continue;
            int i = picked;
            G[j] = G[j] + ((G[i] - G[j] + m[j] - 1) / m[j]) * m[j];
            changed = true;
        }
    }
    return G;
}

int main() {
    // x^2 == 1 (mod 3, 5, 7) -- smallest roots are 1.
    std::vector<long long> m = {3, 5, 7};
    std::vector<long long> a = {1, 1, 1};
    std::vector<long long> roots = {1, 1, 1};
    auto G = parQuadCRT(m, a, roots);
    std::cout << "x = " << G[0] << '\n';
    return 0;
}
