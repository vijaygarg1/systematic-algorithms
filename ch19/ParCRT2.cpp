// Par-CRT2: descending parallel Chinese Remainder Theorem.
// Searches for the largest solution below M.

#include <iostream>
#include <vector>

std::vector<long long> parCRT2(const std::vector<long long>& m,
                               const std::vector<long long>& b,
                               long long M) {
    int n = (int)m.size();
    std::vector<long long> G(n);
    for (int j = 0; j < n; ++j) {
        long long r = (M - 1) % m[j];
        G[j] = (r >= b[j]) ? (M - 1) - r + b[j] : (M - 1) - r + b[j] - m[j];
    }
    bool changed = true;
    while (changed) {
        changed = false;
        long long minVal = G[0];
        for (int i = 1; i < n; ++i) if (G[i] < minVal) minVal = G[i];
        for (int j = 0; j < n; ++j) {
            if (G[j] > minVal) {
                long long diff = G[j] - minVal;
                long long steps = (diff + m[j] - 1) / m[j];
                G[j] -= steps * m[j];
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<long long> m = {3, 5, 7};
    std::vector<long long> b = {2, 3, 2};
    auto G = parCRT2(m, b, /*M=*/200);
    std::cout << "max < M: " << G[0] << '\n';
    return 0;
}
