// HopBound: composition program enforcing h[j] <= k.  On infeasibility
// returns an empty vector.

#include <iostream>
#include <vector>

std::vector<int> hopBound(const std::vector<int>& h, int k,
                          std::vector<int> G) {
    for (int hi : h) if (hi > k) return {};
    return G;
}

int main() {
    std::vector<int> h = {0, 1, 2, 3};
    std::vector<int> G = {0, 4, 9, 15};
    auto out = hopBound(h, 2, G);
    if (out.empty()) { std::cout << "infeasible\n"; return 0; }
    std::cout << "G:";
    for (int g : out) std::cout << ' ' << g;
    std::cout << '\n';
    return 0;
}
