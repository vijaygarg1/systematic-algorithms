"""MergeSort as an example of recursion, matching bxx-introduction.tex
Algorithm MergeSort-Intro (algo:mergesort): split, sort each half, merge.
Merge copies the range into a scratch array B and then walks the two
halves back into A; its two leftover-copy loops are one line each, as
the book prints them. B is sized to the sub-range, not to all of A:
allocating and copying the whole array on every one of the Theta(n)
merge calls costs Theta(n^2) by itself, which swamps the O(n log n) the
merging achieves.

Auto-generated from MergeSortIntro.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MergeSortIntro:
    def MergeSortIntro(self, A, low, high):
        n = len(A)
        if (low < high):
            mid = int((low + high) / 2)
            self.MergeSortIntro(A, low, mid)
            self.MergeSortIntro(A, (mid + 1), high)
            self.Merge(A, low, mid, high)

    def Merge(self, A, low, mid, high):
        B = [0] * (((high - low) + 1))
        for k in range(low, (high) + 1):
            B[(k - low)] = A[k]
        i = low
        j = (mid + 1)
        k = low
        while ((i <= mid) and (j <= high)):
            if (B[(i - low)] <= B[(j - low)]):
                A[k] = B[(i - low)]
                i = (i + 1)
            else:
                A[k] = B[(j - low)]
                j = (j + 1)
            k = (k + 1)
        while (i <= mid):
            A[k] = B[(i - low)]
            i = (i + 1)
            k = (k + 1)
        while (j <= high):
            A[k] = B[(j - low)]
            j = (j + 1)
            k = (k + 1)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = MergeSortIntro()
    prog.MergeSortIntro(A, 0, len(A) - 1)
    print(A)

