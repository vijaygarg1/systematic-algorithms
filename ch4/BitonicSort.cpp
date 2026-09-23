// BitonicSort: bitonic merge network.  Input length must be a power of two.

#include <iostream>
#include <vector>
#include <utility>

void bitonicMerge(std::vector<int>& A, int lo, int len, bool ascending) {
    if (len <= 1) return;
    int k = len / 2;
    for (int i = lo; i < lo + k; ++i) {
        if ((A[i] > A[i + k]) == ascending) std::swap(A[i], A[i + k]);
    }
    bitonicMerge(A, lo, k, ascending);
    bitonicMerge(A, lo + k, k, ascending);
}

void bitonicSort(std::vector<int>& A, int lo, int len, bool ascending) {
    if (len <= 1) return;
    int k = len / 2;
    bitonicSort(A, lo, k, true);            // ascending half
    bitonicSort(A, lo + k, k, false);       // descending half
    bitonicMerge(A, lo, len, ascending);
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3, 7, 0};   // length must be power of 2
    bitonicSort(A, 0, (int)A.size(), true);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
