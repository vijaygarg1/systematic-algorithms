"""Counting inversions by divide and conquer, matching bxx-divideConquer.tex
Algorithm CountingInversions (algo:counting-inversions): inversions in the
left half, plus those in the right half, plus those spanning the two,
which a merge counts for free -- when A[j] from the right half is taken
first, it is inverted with every element still left in the left half.

Laid out as the book prints it: one declaration line for the four
counters, and a single line for each leftover-copy loop.

Auto-generated from CountingInversions.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class CountingInversions:
    def count(self, A, low, high):
        n = len(A)
        if (low >= high):
            return 0
        mid = int((low + high) / 2)
        invLeft = self.count(A, low, mid)
        invRight = self.count(A, (mid + 1), high)
        invMerge = self.mergeAndCount(A, low, mid, high)
        return ((invLeft + invRight) + invMerge)

    def mergeAndCount(self, A, low, mid, high):
        B = [0] * (((high - low) + 1))
        for k in range(low, (high) + 1):
            B[(k - low)] = A[k]
        i = low
        j = (mid + 1)
        k = low
        inv = 0
        while ((i <= mid) and (j <= high)):
            if (B[(i - low)] <= B[(j - low)]):
                A[k] = B[(i - low)]
                i = (i + 1)
            else:
                A[k] = B[(j - low)]
                j = (j + 1)
                inv = (inv + ((mid - i) + 1))
            k = (k + 1)
        while (i <= mid):
            A[k] = B[(i - low)]
            i = (i + 1)
            k = (k + 1)
        while (j <= high):
            A[k] = B[(j - low)]
            j = (j + 1)
            k = (k + 1)
        return inv


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = CountingInversions()
    result = prog.count(A, 0, len(A) - 1)
    print(result)

