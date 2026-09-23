// LLP-Boruvka pointer-jumping kernel.

#include <iostream>
#include <vector>

std::vector<int> llpBoruvka(std::vector<int> G) {
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < (int)G.size(); ++j) {
            if (G[j] != G[G[j]]) { G[j] = G[G[j]]; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> G = {0, 0, 1, 2, 4, 4, 5, 6};
    auto out = llpBoruvka(G);
    std::cout << "after pointer-jumping:";
    for (int x : out) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
