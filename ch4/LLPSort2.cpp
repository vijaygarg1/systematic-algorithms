// LLP-Sort2: forbidden(j) iff some k>j has A[j] > A[k]; advance: swap.

#include <iostream>
#include <vector>
#include <utility>

int firstWitness(int j, const std::vector<int>& A) {
    for (int k = j + 1; k < (int)A.size(); ++k) {
        if (A[j] > A[k]) return k;
    }
    return -1;
}

void llpSort2(std::vector<int>& A) {
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < (int)A.size(); ++j) {
            int k = firstWitness(j, A);
            if (k != -1) { std::swap(A[j], A[k]); changed = true; }
        }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    llpSort2(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
