// Recursive binary search on a sorted vector A within [low..high].
// Returns the index of `key` in A if present, else -1.

#include <iostream>
#include <vector>

int binarySearch(const std::vector<int>& A, int key, int low, int high) {
    if (low > high) return -1;
    int mid = (low + high) / 2;
    if (A[mid] == key) return mid;
    if (A[mid] > key)  return binarySearch(A, key, low, mid - 1);
    return binarySearch(A, key, mid + 1, high);
}

int main() {
    std::vector<int> A = {1, 3, 5, 7, 9, 11, 13, 15};
    for (int key : {7, 1, 15, 4, 16}) {
        std::cout << "key=" << key << ": index = "
                  << binarySearch(A, key, 0, (int)A.size() - 1) << '\n';
    }
    return 0;
}
