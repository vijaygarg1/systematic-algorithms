"""Move n disks from source to target through aux (Towers of Hanoi).

Auto-generated from Hanoi.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Hanoi:
    def Hanoi(self, n, source, aux, target):
        if (n == 1):
            pass
        else:
            self.Hanoi((n - 1), source, target, aux)
            self.Hanoi((n - 1), aux, source, target)


if __name__ == '__main__':
    n = 3
    prog = Hanoi()
    prog.Hanoi(n, 0, 1, 2)

