// Odd-Even (transposition) sort.

#include <iostream>
#include <vector>
#include <utility>

void oddEvenSort(std::vector<int>& A) {
    int n = (int)A.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 1; j + 1 < n; j += 2)             // odd indices
            if (A[j] > A[j + 1]) { std::swap(A[j], A[j + 1]); changed = true; }
        for (int j = 0; j + 1 < n; j += 2)             // even indices
            if (A[j] > A[j + 1]) { std::swap(A[j], A[j + 1]); changed = true; }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    oddEvenSort(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
