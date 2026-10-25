/*
 * Move n disks from source to target through aux (Towers of Hanoi).
 *
 * Algorithm Hanoi in bxx-introduction.tex is a recursive procedure whose
 * else-branch is three steps IN THIS ORDER:
 *
 * Hanoi(n-1, source, target, aux)
 * Move disk from source to target
 * Hanoi(n-1, aux, source, target)
 *
 * and whose base case is that same single move. Its declared output is
 * "the sequence of moves transferring all n disks from source to target".
 *
 * So this program keeps the book's signature and writes those three steps
 * as three consecutive statements, in the book's order, with `move` as the
 * book's "Move disk from source to target". An earlier version collapsed
 * the else-branch into one nested expression,
 * return concat(concat(Hanoi(...), one(...)), Hanoi(...));
 * which is the same computation but reads inside-out: the two recursive
 * calls are buried as arguments, and the book's "recurse, move, recurse"
 * sequence is no longer visible on the page. (An earlier version still
 * left both moves as comments, so the program did nothing at all.)
 *
 * WHY THE MOVES ARE RETURNED RATHER THAN APPENDED TO ONE BUFFER. LL has
 * no I/O, so the sequence has to come back as a value. A single shared
 * buffer would need a move counter visible to every recursive call, and
 * LL offers no way to have one: a class-level field is a syntax error
 * (`list<int> moves;` outside a method -> "expected LBRACE, got SEMI"),
 * and a declaration at the top of the method body is a fresh local in
 * every recursive call. The only remaining route is an extra accumulator
 * parameter -- and that is exactly what would stop the signature from
 * being the book's Hanoi(n, source, aux, target). Concatenating instead
 * costs O(n 2^n) where a buffer would be O(2^n); the book's own bound is
 * on the number of MOVES, which is 2^n - 1 either way.
 *
 * Auto-generated from Hanoi.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Hanoi {
  Hanoi(n, source, aux, target) {
    if ((n === 1)) {
        return this.move(source, target);
    } else {
        let before = this.Hanoi((n - 1), source, target, aux);
        let here = this.move(source, target);
        let after = this.Hanoi((n - 1), aux, source, target);
        return this.concat(this.concat(before, here), after);
    }
  }

  move(source, target) {
    let m = Array.from({length: 1}, () => new Array(2).fill(0));
    m[0][0] = source;
    m[0][1] = target;
    return m;
  }

  concat(X, Y) {
    let Z = Array.from({length: (X.length + Y.length)}, () => new Array(2).fill(0));
    for (let i = 0; i <= ((X.length - 1)); i++) {
        Z[i][0] = X[i][0];
        Z[i][1] = X[i][1];
    }
    for (let i = 0; i <= ((Y.length - 1)); i++) {
        Z[(X.length + i)][0] = Y[i][0];
        Z[(X.length + i)][1] = Y[i][1];
    }
    return Z;
  }
}

if (typeof module !== 'undefined') module.exports = Hanoi;

if (typeof require !== 'undefined' && require.main === module) {
    const n = 3;
    const prog = new Hanoi();
    const result = prog.Hanoi(n, 0, 1, 2);
    console.log(JSON.stringify(result));
}
