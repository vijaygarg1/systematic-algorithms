// Count pairs (i, j) with i < j whose values sum to target.

#include <iostream>
#include <vector>

int pairSum(const std::vector<int>& A, int target) {
    int c = 0;
    int n = (int)A.size();
    for (int i = 0; i < n; ++i)
        for (int j = i + 1; j < n; ++j)
            if (A[i] + A[j] == target) ++c;
    return c;
}

int main() {
    std::vector<int> A = {1, 5, 2, 7, 3, 4, 6};
    std::cout << "pairSum(A, 8) = " << pairSum(A, 8) << '\n';
    return 0;
}
