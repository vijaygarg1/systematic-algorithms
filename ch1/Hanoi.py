"""Move n disks from source to target through aux (Towers of Hanoi).

Algorithm Hanoi in bxx-introduction.tex is a recursive procedure whose
else-branch is three steps IN THIS ORDER:

Hanoi(n-1, source, target, aux)
Move disk from source to target
Hanoi(n-1, aux, source, target)

and whose base case is that same single move. Its declared output is
"the sequence of moves transferring all n disks from source to target".

So this program keeps the book's signature and writes those three steps
as three consecutive statements, in the book's order, with `move` as the
book's "Move disk from source to target". An earlier version collapsed
the else-branch into one nested expression,
return concat(concat(Hanoi(...), one(...)), Hanoi(...));
which is the same computation but reads inside-out: the two recursive
calls are buried as arguments, and the book's "recurse, move, recurse"
sequence is no longer visible on the page. (An earlier version still
left both moves as comments, so the program did nothing at all.)

WHY THE MOVES ARE RETURNED RATHER THAN APPENDED TO ONE BUFFER. LL has
no I/O, so the sequence has to come back as a value. A single shared
buffer would need a move counter visible to every recursive call, and
LL offers no way to have one: a class-level field is a syntax error
(`list<int> moves;` outside a method -> "expected LBRACE, got SEMI"),
and a declaration at the top of the method body is a fresh local in
every recursive call. The only remaining route is an extra accumulator
parameter -- and that is exactly what would stop the signature from
being the book's Hanoi(n, source, aux, target). Concatenating instead
costs O(n 2^n) where a buffer would be O(2^n); the book's own bound is
on the number of MOVES, which is 2^n - 1 either way.

Auto-generated from Hanoi.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Hanoi:
    def Hanoi(self, n, source, aux, target):
        if (n == 1):
            return self.move(source, target)
        else:
            before = self.Hanoi((n - 1), source, target, aux)
            here = self.move(source, target)
            after = self.Hanoi((n - 1), aux, source, target)
            return self.concat(self.concat(before, here), after)

    def move(self, source, target):
        m = [[0] * (2) for _ in range(1)]
        m[0][0] = source
        m[0][1] = target
        return m

    def concat(self, X, Y):
        Z = [[0] * (2) for _ in range((len(X) + len(Y)))]
        for i in range(0, ((len(X) - 1)) + 1):
            Z[i][0] = X[i][0]
            Z[i][1] = X[i][1]
        for i in range(0, ((len(Y) - 1)) + 1):
            Z[(len(X) + i)][0] = Y[i][0]
            Z[(len(X) + i)][1] = Y[i][1]
        return Z


if __name__ == '__main__':
    n = 3
    prog = Hanoi()
    result = prog.Hanoi(n, 0, 1, 2)
    print(result)

