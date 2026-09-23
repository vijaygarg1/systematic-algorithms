// MergeTwo: out-of-place merge of two sorted vectors B and C into D.

#include <iostream>
#include <vector>

std::vector<int> mergeTwo(const std::vector<int>& B,
                          const std::vector<int>& C) {
    int nb = (int)B.size(), nc = (int)C.size();
    std::vector<int> D(nb + nc);
    int i = 0, j = 0, k = 0;
    while (i < nb && j < nc) {
        if (B[i] < C[j]) D[k++] = B[i++];
        else             D[k++] = C[j++];
    }
    while (i < nb) D[k++] = B[i++];
    while (j < nc) D[k++] = C[j++];
    return D;
}

int main() {
    std::vector<int> B = {1, 4, 5, 8};
    std::vector<int> C = {2, 3, 6, 7};
    auto D = mergeTwo(B, C);
    for (int x : D) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
