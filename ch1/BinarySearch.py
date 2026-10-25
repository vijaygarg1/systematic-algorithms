"""Return any index of key in a sorted array A, or -1 if absent.

Auto-generated from BinarySearch.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BinarySearch:
    def BinarySearch(self, A, key, low, high):
        n = len(A)
        if (low > high):
            return (-1)
        mid = int((low + high) / 2)
        if (A[mid] == key):
            return mid
        if (A[mid] > key):
            return self.BinarySearch(A, key, low, (mid - 1))
        return self.BinarySearch(A, key, (mid + 1), high)


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    key = 0
    prog = BinarySearch()
    result = prog.BinarySearch(A, key, 0, len(A) - 1)
    print(result)

