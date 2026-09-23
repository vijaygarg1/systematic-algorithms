// Sequential QuickSort with Lomuto partition.

#include <iostream>
#include <vector>
#include <utility>

int partition(std::vector<int>& A, int lo, int hi) {
    int pivot = A[hi];
    int i = lo - 1;
    for (int j = lo; j < hi; ++j) {
        if (A[j] <= pivot) {
            ++i;
            std::swap(A[i], A[j]);
        }
    }
    std::swap(A[i + 1], A[hi]);
    return i + 1;
}

void seqQuickSort(std::vector<int>& A, int lo, int hi) {
    if (lo < hi) {
        int p = partition(A, lo, hi);
        seqQuickSort(A, lo, p - 1);
        seqQuickSort(A, p + 1, hi);
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    seqQuickSort(A, 0, (int)A.size() - 1);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
