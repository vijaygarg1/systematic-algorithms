// LLP-CountInversions: count inversions to the left of each index.

#include <iostream>
#include <vector>

int countLeft(const std::vector<int>& A, int j) {
    int count = 0;
    for (int i = 0; i < j; ++i) if (A[i] > A[j]) ++count;
    return count;
}

std::vector<int> llpCountInversions(const std::vector<int>& A) {
    int n = (int) A.size();
    std::vector<int> G(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            int c = countLeft(A, j);
            if (G[j] < c) { G[j] = c; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> A = {3, 1, 4, 1, 5};
    std::vector<int> G = llpCountInversions(A);
    std::cout << "inversions to the left:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
