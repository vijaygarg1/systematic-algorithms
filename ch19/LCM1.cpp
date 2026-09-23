// LCM1: ascending LLP to find least common multiple.
// Forbidden when G[j] < G[i]; advance jumps to next multiple of A[j] >= G[i].

#include <iostream>
#include <vector>

std::vector<int> lcm1(const std::vector<int>& A) {
    int n = (int)A.size();
    std::vector<int> G = A;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int picked = -1;
            for (int i = 0; i < n; ++i)
                if (G[j] < G[i]) { picked = i; break; }
            if (picked == -1) continue;
            int i = picked;
            G[j] = G[j] + ((G[i] - G[j] + A[j] - 1) / A[j]) * A[j];
            changed = true;
        }
    }
    return G;
}

int main() {
    auto G = lcm1({4, 6, 8});
    std::cout << "lcm=" << G[0] << '\n';
    return 0;
}
