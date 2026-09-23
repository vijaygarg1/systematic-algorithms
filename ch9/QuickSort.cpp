// Parallel-style QuickSort (composed with `;` for the sequential build).

#include <iostream>
#include <vector>
#include <utility>

int partition(int lo, int hi, std::vector<int>& G) {
    int pivot = G[hi];
    int i = lo - 1;
    for (int j = lo; j < hi; ++j) {
        if (G[j] <= pivot) std::swap(G[++i], G[j]);
    }
    std::swap(G[i + 1], G[hi]);
    return i + 1;
}

void quickSort(int lo, int hi, std::vector<int>& G) {
    if (lo < hi) {
        int p = partition(lo, hi, G);
        quickSort(lo, p - 1, G);
        quickSort(p + 1, hi, G);
    }
}

int main() {
    std::vector<int> G = {5, 2, 4, 6, 1, 3};
    quickSort(0, (int)G.size() - 1, G);
    for (int x : G) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
