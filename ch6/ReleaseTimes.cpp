// ReleaseTimes: composition program enforcing G[j] >= r[j].

#include <iostream>
#include <vector>

std::vector<int> releaseTimes(const std::vector<int>& r, std::vector<int> G) {
    int n = (int)G.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (G[j] < r[j]) { G[j] = r[j]; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> r = {0, 3, 2, 7};
    std::vector<int> G = {0, 0, 0, 0};
    auto out = releaseTimes(r, G);
    std::cout << "G:";
    for (int g : out) std::cout << ' ' << g;
    std::cout << '\n';
    return 0;
}
