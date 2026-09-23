// Parallel-style MergeSort (composes the two halves with `;` here for
// the sequential build).

#include <iostream>
#include <vector>

void merge(int lo, int mid, int hi, std::vector<int>& G) {
    std::vector<int> B(G);
    int i = lo, j = mid + 1, k = lo;
    while (i <= mid && j <= hi) {
        if (B[i] <= B[j]) G[k++] = B[i++];
        else              G[k++] = B[j++];
    }
    while (i <= mid) G[k++] = B[i++];
    while (j <= hi)  G[k++] = B[j++];
}

void mergeSort(int lo, int hi, std::vector<int>& G) {
    if (lo < hi) {
        int mid = (lo + hi) / 2;
        mergeSort(lo, mid, G);
        mergeSort(mid + 1, hi, G);
        merge(lo, mid, hi, G);
    }
}

int main() {
    std::vector<int> G = {5, 2, 4, 6, 1, 3};
    mergeSort(0, (int)G.size() - 1, G);
    for (int x : G) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
