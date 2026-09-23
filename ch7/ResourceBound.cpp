// ResourceBound: composition program enforcing rho[j] <= B.

#include <iostream>
#include <vector>

std::vector<int> resourceBound(const std::vector<int>& rho, int B,
                               std::vector<int> G) {
    for (int r : rho) if (r > B) return {};
    return G;
}

int main() {
    std::vector<int> rho = {0, 2, 5, 9};
    std::vector<int> G   = {0, 4, 8, 15};
    auto out = resourceBound(rho, 7, G);
    if (out.empty()) { std::cout << "infeasible\n"; return 0; }
    std::cout << "G:";
    for (int g : out) std::cout << ' ' << g;
    std::cout << '\n';
    return 0;
}
