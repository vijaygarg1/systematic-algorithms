// GCD1: descending GCD via Euclidean reduction.
// Forbidden when G[j] > G[i]; advance reduces G[j] by mod.

#include <iostream>
#include <vector>

std::vector<int> gcd1(std::vector<int> A) {
    int n = (int)A.size();
    std::vector<int> G = A;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int picked = -1;
            for (int i = 0; i < n; ++i)
                if (G[j] > G[i]) { picked = i; break; }
            if (picked == -1) continue;
            int i = picked;
            G[j] = (G[j] % G[i] == 0) ? G[i] : G[j] % G[i];
            changed = true;
        }
    }
    return G;
}

int main() {
    auto G = gcd1({48, 36, 60});
    std::cout << "gcd=" << G[0] << '\n';
    return 0;
}
