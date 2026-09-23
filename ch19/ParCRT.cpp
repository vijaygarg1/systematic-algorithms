// Par-CRT: ascending parallel Chinese Remainder Theorem.
// Forbidden when G[j] < G[i] for some i;
// advance jumps to next multiple-of-m[j] congruence >= G[i].

#include <iostream>
#include <vector>

std::vector<long long> parCRT(const std::vector<long long>& m,
                              const std::vector<long long>& b) {
    int n = (int)m.size();
    std::vector<long long> G(b);
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
    // x ≡ 2 mod 3, x ≡ 3 mod 5, x ≡ 2 mod 7.  Solution = 23.
    std::vector<long long> m = {3, 5, 7};
    std::vector<long long> b = {2, 3, 2};
    auto G = parCRT(m, b);
    std::cout << "x = " << G[0] << '\n';
    return 0;
}
