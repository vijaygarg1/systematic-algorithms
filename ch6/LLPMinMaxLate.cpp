// LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each
// start time G[j] to the prefix sum of earlier processing times.

#include <iostream>
#include <vector>

std::vector<int> llpMinMaxLate(const std::vector<int>& t,
                               const std::vector<int>& /*d*/) {
    int n = (int)t.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        int prefix = 0;
        for (int j = 0; j < n; ++j) {
            if (G[j] < prefix) { G[j] = prefix; changed = true; }
            prefix += t[j];
        }
    }
    return G;
}

int main() {
    std::vector<int> t = {1, 4, 3};
    std::vector<int> d = {2, 5, 8};
    auto G = llpMinMaxLate(t, d);
    std::cout << "start times:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
