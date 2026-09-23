// Single-pass scan for the maximum of a non-empty vector.

#include <iostream>
#include <vector>

int maxElement(const std::vector<int>& A) {
    int best = A[0];
    for (int i = 1; i < (int)A.size(); ++i) {
        if (A[i] > best) best = A[i];
    }
    return best;
}

int main() {
    std::vector<int> A = {4, 2, 7, 1, 9, 3, 8};
    std::cout << "max = " << maxElement(A) << '\n';
    return 0;
}
