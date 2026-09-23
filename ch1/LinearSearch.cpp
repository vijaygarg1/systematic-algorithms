// Linear search for `key` in vector A.  First-occurrence index, or -1.

#include <iostream>
#include <vector>

int linearSearch(const std::vector<int>& A, int key) {
    for (int i = 0; i < (int)A.size(); ++i) {
        if (A[i] == key) return i;
    }
    return -1;
}

int main() {
    std::vector<int> A = {4, 2, 7, 1, 9, 3};
    for (int key : {7, 4, 5, 9}) {
        std::cout << "key=" << key << ": index = "
                  << linearSearch(A, key) << '\n';
    }
    return 0;
}
