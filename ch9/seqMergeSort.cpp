// Sequential MergeSort.

#include <iostream>
#include <vector>

void merge(std::vector<int>& A, int lo, int mid, int hi) {
    std::vector<int> B(A);
    int i = lo, j = mid + 1, k = lo;
    while (i <= mid && j <= hi) {
        if (B[i] <= B[j]) A[k++] = B[i++];
        else              A[k++] = B[j++];
    }
    while (i <= mid) A[k++] = B[i++];
    while (j <= hi)  A[k++] = B[j++];
}

void seqMergeSort(std::vector<int>& A, int lo, int hi) {
    if (lo < hi) {
        int mid = (lo + hi) / 2;
        seqMergeSort(A, lo, mid);
        seqMergeSort(A, mid + 1, hi);
        merge(A, lo, mid, hi);
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    seqMergeSort(A, 0, (int)A.size() - 1);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
