"""Compute n! via straight recursion.

Auto-generated from Factorial.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Factorial:
    def Factorial(self, n):
        if (n == 0):
            return 1
        return (n * self.Factorial((n - 1)))


if __name__ == '__main__':
    n = 3
    prog = Factorial()
    result = prog.Factorial(n)
    print(result)

