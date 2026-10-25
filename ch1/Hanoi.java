// Move n disks from source to target through aux (Towers of Hanoi).
//
// Algorithm Hanoi in bxx-introduction.tex is a recursive procedure whose
// else-branch is three steps IN THIS ORDER:
//
// Hanoi(n-1, source, target, aux)
// Move disk from source to target
// Hanoi(n-1, aux, source, target)
//
// and whose base case is that same single move. Its declared output is
// "the sequence of moves transferring all n disks from source to target".
//
// So this program keeps the book's signature and writes those three steps
// as three consecutive statements, in the book's order, with `move` as the
// book's "Move disk from source to target". An earlier version collapsed
// the else-branch into one nested expression,
// return concat(concat(Hanoi(...), one(...)), Hanoi(...));
// which is the same computation but reads inside-out: the two recursive
// calls are buried as arguments, and the book's "recurse, move, recurse"
// sequence is no longer visible on the page. (An earlier version still
// left both moves as comments, so the program did nothing at all.)
//
// WHY THE MOVES ARE RETURNED RATHER THAN APPENDED TO ONE BUFFER. LL has
// no I/O, so the sequence has to come back as a value. A single shared
// buffer would need a move counter visible to every recursive call, and
// LL offers no way to have one: a class-level field is a syntax error
// (`list<int> moves;` outside a method -> "expected LBRACE, got SEMI"),
// and a declaration at the top of the method body is a fresh local in
// every recursive call. The only remaining route is an extra accumulator
// parameter -- and that is exactly what would stop the signature from
// being the book's Hanoi(n, source, aux, target). Concatenating instead
// costs O(n 2^n) where a buffer would be O(2^n); the book's own bound is
// on the number of MOVES, which is 2^n - 1 either way.

import java.util.*;

public class Hanoi {
  public int[][] Hanoi(int n, int source, int aux, int target) {
    if ((n == 1)) {
      return move(source, target);
    } else {
      int[][] before = Hanoi((n - 1), source, target, aux);
      int[][] here = move(source, target);
      int[][] after = Hanoi((n - 1), aux, source, target);
      return concat(concat(before, here), after);
    }
  }

  public int[][] move(int source, int target) {
    int[][] m = new int[1][2];
    m[0][0] = source;
    m[0][1] = target;
    return m;
  }

  public int[][] concat(int[][] X, int[][] Y) {
    int[][] Z = new int[(X.length + Y.length)][2];
    for (int i = 0; i < X.length; i++) {
      {
        Z[i][0] = X[i][0];
        Z[i][1] = X[i][1];
      }
    }
    for (int i = 0; i < Y.length; i++) {
      {
        Z[(X.length + i)][0] = Y[i][0];
        Z[(X.length + i)][1] = Y[i][1];
      }
    }
    return Z;
  }

  public static void main(String[] args) {
    int n = 3;
    Hanoi prog = new Hanoi();
    int[][] result = prog.Hanoi(n, 0, 1, 2);
    System.out.println(Arrays.deepToString(result));
  }
}