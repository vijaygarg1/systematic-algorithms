// LLP-OddEven-Sort: same forbidden/advance rule as LLP-Sort1, scheduled
// in alternating odd / even rounds.

#include <iostream>
#include <vector>
#include <utility>

bool forbidden(int j, const std::vector<int>& A) {
    return j + 1 < (int)A.size() && A[j] > A[j + 1];
}
void advance(int j, std::vector<int>& A) {
    std::swap(A[j], A[j + 1]);
}

void llpOddEvenSort(std::vector<int>& A) {
    int n = (int)A.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 1; j + 1 < n; j += 2)
            if (forbidden(j, A)) { advance(j, A); changed = true; }
        for (int j = 0; j + 1 < n; j += 2)
            if (forbidden(j, A)) { advance(j, A); changed = true; }
    }
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    llpOddEvenSort(A);
    for (int x : A) std::cout << x << ' ';
    std::cout << '\n';
    return 0;
}
