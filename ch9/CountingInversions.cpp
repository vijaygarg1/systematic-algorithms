// Count inversions in O(n log n) via merge sort.
//
// Step for step as bxx-divideConquer.tex prints Algorithm
// CountingInversions: the merge reads A and writes the scratch array
// `temp`, then a final loop copies temp back into A[left..right]. An
// earlier version ran it the other way round (snapshot A into B, merge B
// back into A), which computes the same thing but is the mirror image of
// the book's MergeAndCount and has no copy-back step.
//
// Unlike the LL source, C++ can write the book's `temp[k++] := A[i++]`
// directly, and can reuse the dead merge cursor `k` as the copy-back
// loop variable the way the book does -- the LL version has to use a
// fresh `t` there, because `for k in [...]` would re-declare it.
//
// temp is sized to the sub-range. Copying all of A on every one of the
// Theta(n) merge calls costs Theta(n^2) by itself, swamping the
// O(n log n) the merging achieves.

#include <iostream>
#include <vector>

int MergeAndCount(std::vector<int>& A, int left, int mid, int right) {
    int i = left, j = mid + 1, k = 0, count = 0;
    std::vector<int> temp(right - left + 1);
    while (i <= mid && j <= right) {
        if (A[i] <= A[j]) {
            temp[k++] = A[i++];
        } else {
            temp[k++] = A[j++];
            count += (mid - i + 1);   // count inversions
        }
    }
    while (i <= mid)   { temp[k++] = A[i++]; }
    while (j <= right) { temp[k++] = A[j++]; }
    for (k = 0; k <= right - left; ++k) A[left + k] = temp[k];
    return count;
}

int CountInversions(std::vector<int>& A, int left, int right) {
    if (left >= right) return 0;
    int mid = (left + right) / 2;
    int invLeft  = CountInversions(A, left, mid);       // sort the left half, count
    int invRight = CountInversions(A, mid + 1, right);  // sort the right half, count
    int invMerge = MergeAndCount(A, left, mid, right);
    return invLeft + invRight + invMerge;
}

int main() {
    std::vector<int> A = {5, 2, 4, 6, 1, 3};
    int inv = CountInversions(A, 0, (int)A.size() - 1);
    std::cout << "sorted:";
    for (int x : A) std::cout << ' ' << x;
    std::cout << ", inversions: " << inv << '\n';
    return 0;
}
