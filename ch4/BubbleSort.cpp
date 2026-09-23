// BubbleSort: repeated passes of adjacent compare-and-swap.

#include <iostream>
#include <vector>
#include <utility>

void bubbleSort(std::vector<int>& A) {
    int n = (int)A.size();
    bool swapped = true;
    while (swapped) {
        swapped = false;
        for (int i = 0; i + 1 < n; ++i) {
            if (A[i] > A[i + 1]) {
                std::swap(A[i], A[i + 1]);
                swapped = true;
            }
        }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    bubbleSort(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
