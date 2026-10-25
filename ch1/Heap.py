"""Min-heap on an int[]: build, heapify, insert, extract-min.

Auto-generated from Heap.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Heap:
    def BuildHeap(self, A, n):
        self.A = A
        self.n = n
        self.i = (int(self.n / 2) - 1)
        while (self.i >= 0):
            self.Heapify(self.A, self.i, self.n)
            self.i = (self.i - 1)

    def Heapify(self, A, i, n):
        smallest = i
        left = ((2 * i) + 1)
        right = ((2 * i) + 2)
        if ((left < n) and (A[left] < A[smallest])):
            smallest = left
        if ((right < n) and (A[right] < A[smallest])):
            smallest = right
        if (smallest != i):
            A[i], A[smallest] = A[smallest], A[i]
            self.Heapify(A, smallest, n)

    def HeapInsert(self, A, key, n):
        n = (n + 1)
        i = (n - 1)
        while ((i > 0) and (A[int((i - 1) / 2)] > key)):
            A[i] = A[int((i - 1) / 2)]
            i = int((i - 1) / 2)
        A[i] = key
        return n

    def HeapExtractMin(self, A, n):
        if (n < 1):
            return (-2147483648)
        min_ = A[0]
        A[0] = A[(n - 1)]
        n = (n - 1)
        self.Heapify(A, 0, n)
        return min_


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = Heap()
    prog.BuildHeap(A, len(A))
    print(A)

