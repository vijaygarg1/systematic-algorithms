// 2-approximation vertex cover: greedily pick both endpoints of an
// uncovered edge, matching bxx-approximate.tex Algorithm
// ApproxVertexCover. The book's input is the edge set E, and the loop
// walks it once, which is what makes the algorithm O(|E|).
//
// The book's "remove all edges incident to u or v from E'" needs no
// bookkeeping: an edge is still in E' exactly when neither of its
// endpoints has been taken into C, so the test below is that membership
// test.

#include <iostream>
#include <vector>

std::vector<char> approxVertexCover(const std::vector<int>& eu,
                                    const std::vector<int>& ev, int n) {
    std::vector<char> C(n, 0);
    int m = (int) eu.size();
    for (int e = 0; e < m; ++e) {
        int u = eu[e];
        int v = ev[e];
        if (!C[u] && !C[v]) {
            C[u] = 1;
            C[v] = 1;
        }
    }
    return C;
}

int main() {
    std::vector<int> eu = {0, 1, 2, 3};
    std::vector<int> ev = {1, 2, 3, 4};
    std::vector<char> C = approxVertexCover(eu, ev, 5);
    std::cout << "cover:";
    for (int i = 0; i < (int) C.size(); ++i) if (C[i]) std::cout << ' ' << i;
    std::cout << '\n';
    return 0;
}
