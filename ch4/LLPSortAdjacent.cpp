// LLP-Sort1: forbidden(j) iff A[j] > A[j+1]; advance: swap.

#include <iostream>
#include <vector>
#include <utility>

bool forbidden(int j, const std::vector<int>& A) {
    return j + 1 < (int)A.size() && A[j] > A[j + 1];
}
void advance(int j, std::vector<int>& A) {
    std::swap(A[j], A[j + 1]);
}

void llpSort1(std::vector<int>& A) {
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j + 1 < (int)A.size(); ++j) {
            if (forbidden(j, A)) { advance(j, A); changed = true; }
        }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    llpSort1(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
