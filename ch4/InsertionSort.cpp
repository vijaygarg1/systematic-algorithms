// Insertion Sort: walk each new element into its place via swaps.

#include <iostream>
#include <vector>
#include <utility>

void insertionSort(std::vector<int>& A) {
    int n = (int)A.size();
    for (int i = 1; i < n; ++i) {
        int j = i - 1;
        bool done = false;
        while (j >= 0 && !done) {
            if (A[j] <= A[j + 1]) done = true;
            else { std::swap(A[j], A[j + 1]); --j; }
        }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    insertionSort(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
