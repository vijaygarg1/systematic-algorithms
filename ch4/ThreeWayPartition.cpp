// Dutch-flag (Three-Way) Partition: less / equal / greater regions.

#include <iostream>
#include <vector>
#include <utility>

void threeWayPartition(std::vector<int>& A, int pivot) {
    int lo = 0, mid = 0, hi = (int)A.size() - 1;
    while (mid <= hi) {
        if      (A[mid] < pivot) std::swap(A[lo++], A[mid++]);
        else if (A[mid] > pivot) std::swap(A[mid],  A[hi--]);
        else                     ++mid;
    }
}

int main() {
    std::vector<int> A = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
    threeWayPartition(A, 5);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
