"""Karatsuba multiplication: three half-size products instead of four.

The book writes the middle product as Karatsuba(x0+x1, y0+y1, ceil(n/2)+1),
where ceil(n/2)+1 is a BOUND on the digit length of the two sums, not a
recursion driver: at n = 2 it is again 2, so taking it literally never
reaches the n = 1 base case (confirmed: StackOverflowError). `half` is
passed instead. Correctness does not depend on the third argument being
the exact digit count -- the split X = x1*10^h + x0 with x0 = X - x1*10^h
is valid for any X and h, and the base case multiplies whatever it is
given -- so the only effect is that the recursion terminates.

Auto-generated from Karatsuba.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Karatsuba:
    def Karatsuba(self, X, Y, n):
        if (n == 1):
            return (X * Y)
        half = int(n / 2)
        divisor = self.pow10(half)
        x1 = int(X / divisor)
        x0 = (X - (x1 * divisor))
        y1 = int(Y / divisor)
        y0 = (Y - (y1 * divisor))
        p1 = self.Karatsuba(x0, y0, half)
        p2 = self.Karatsuba(x1, y1, half)
        p3 = self.Karatsuba((x0 + x1), (y0 + y1), half)
        middle = ((p3 - p1) - p2)
        return (((p2 * self.pow10(n)) + (middle * divisor)) + p1)

    def pow10(self, k):
        r = 1
        for i in range(0, ((k - 1)) + 1):
            r = (r * 10)
        return r


if __name__ == '__main__':
    X = 0
    Y = 0
    n = 3
    prog = Karatsuba()
    result = prog.Karatsuba(X, Y, n)
    print(result)

