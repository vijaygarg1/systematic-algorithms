// Recursive MergeSort skeleton (sequential).  Sorts A[low..high] in place.

#include <iostream>
#include <vector>

void merge(std::vector<int>& A, int low, int mid, int high) {
    std::vector<int> B(A);
    int i = low, j = mid + 1, k = low;
    while (i <= mid && j <= high) {
        if (B[i] <= B[j]) A[k++] = B[i++];
        else              A[k++] = B[j++];
    }
    while (i <= mid)  A[k++] = B[i++];
    while (j <= high) A[k++] = B[j++];
}

void mergeSort(std::vector<int>& A, int low, int high) {
    if (low < high) {
        int mid = (low + high) / 2;
        mergeSort(A, low, mid);
        mergeSort(A, mid + 1, high);
        merge(A, low, mid, high);
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    mergeSort(A, 0, (int)A.size() - 1);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
