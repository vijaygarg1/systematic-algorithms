// Min-heap operations on a flat vector A.

#include <iostream>
#include <vector>
#include <utility>
#include <climits>

void heapify(std::vector<int>& A, int i, int n) {
    int smallest = i;
    int left = 2 * i + 1, right = 2 * i + 2;
    if (left  < n && A[left]  < A[smallest]) smallest = left;
    if (right < n && A[right] < A[smallest]) smallest = right;
    if (smallest != i) {
        std::swap(A[i], A[smallest]);
        heapify(A, smallest, n);
    }
}

void build(std::vector<int>& A, int n) {
    for (int i = n / 2 - 1; i >= 0; --i) heapify(A, i, n);
}

int insert(std::vector<int>& A, int key, int n) {
    n = n + 1;
    int i = n - 1;
    while (i > 0 && A[(i - 1) / 2] > key) {
        A[i] = A[(i - 1) / 2];
        i = (i - 1) / 2;
    }
    A[i] = key;
    return n;
}

int extractMin(std::vector<int>& A, int n) {
    if (n < 1) return INT_MIN;
    int minimum = A[0];
    A[0] = A[n - 1];
    n -= 1;
    heapify(A, 0, n);
    return minimum;
}

int main() {
    std::vector<int> A = {9, 4, 7, 1, 8, 3, 6, 2, 5};
    int n = (int)A.size();
    build(A, n);
    std::cout << "after build:";
    for (int i = 0; i < n; ++i) std::cout << ' ' << A[i];
    std::cout << '\n';
    std::cout << "extractMin -> " << extractMin(A, n) << '\n';
    n -= 1;
    std::cout << "after extract:";
    for (int i = 0; i < n; ++i) std::cout << ' ' << A[i];
    std::cout << '\n';
    return 0;
}
