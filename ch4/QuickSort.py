"""Sequential quicksort with Lomuto partition (parallel form in progs-divideConquer/).

Auto-generated from QuickSort.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class QuickSort:
    def sort(self, lo, hi, G):
        n = len(G)
        if (lo < hi):
            p = self.Partition(lo, hi, G)
            self.sort(lo, (p - 1), G)
            self.sort((p + 1), hi, G)

    def Partition(self, lo, hi, G):
        pivot = G[hi]
        i = (lo - 1)
        j = lo
        while (j <= (hi - 1)):
            if (G[j] <= pivot):
                i = (i + 1)
                tmp = G[i]
                G[i] = G[j]
                G[j] = tmp
            j = (j + 1)
        tmp = G[(i + 1)]
        G[(i + 1)] = G[hi]
        G[hi] = tmp
        return (i + 1)


if __name__ == '__main__':
    G = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = QuickSort()
    prog.sort(0, len(G) - 1, G)
    print(G)

