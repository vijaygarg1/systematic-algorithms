// Count inversions in O(n log n) via merge sort.

#include <iostream>
#include <vector>

int mergeAndCount(std::vector<int>& A, int low, int mid, int high) {
    std::vector<int> B(A);
    int i = low, j = mid + 1, k = low, inv = 0;
    while (i <= mid && j <= high) {
        if (B[i] <= B[j]) A[k++] = B[i++];
        else { A[k++] = B[j++]; inv += (mid - i + 1); }
    }
    while (i <= mid)  A[k++] = B[i++];
    while (j <= high) A[k++] = B[j++];
    return inv;
}

int count(std::vector<int>& A, int low, int high) {
    if (low >= high) return 0;
    int mid = (low + high) / 2;
    int invL = count(A, low, mid);
    int invR = count(A, mid + 1, high);
    int invM = mergeAndCount(A, low, mid, high);
    return invL + invR + invM;
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    int inv = count(A, 0, (int)A.size() - 1);
    std::cout << "sorted:";
    for (int x : A) std::cout << ' ' << x;
    std::cout << ", inversions: " << inv << '\n';
    return 0;
}
