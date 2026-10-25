/*
 * Karatsuba multiplication: three half-size products instead of four.
 *
 * The book writes the middle product as Karatsuba(x0+x1, y0+y1, ceil(n/2)+1),
 * where ceil(n/2)+1 is a BOUND on the digit length of the two sums, not a
 * recursion driver: at n = 2 it is again 2, so taking it literally never
 * reaches the n = 1 base case (confirmed: StackOverflowError). `half` is
 * passed instead. Correctness does not depend on the third argument being
 * the exact digit count -- the split X = x1*10^h + x0 with x0 = X - x1*10^h
 * is valid for any X and h, and the base case multiplies whatever it is
 * given -- so the only effect is that the recursion terminates.
 *
 * Auto-generated from Karatsuba.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Karatsuba {
  Karatsuba(X, Y, n) {
    if ((n === 1)) {
        return (X * Y);
    }
    let half = Math.trunc(n / 2);
    let divisor = this.pow10(half);
    let x1 = Math.trunc(X / divisor);
    let x0 = (X - (x1 * divisor));
    let y1 = Math.trunc(Y / divisor);
    let y0 = (Y - (y1 * divisor));
    let p1 = this.Karatsuba(x0, y0, half);
    let p2 = this.Karatsuba(x1, y1, half);
    let p3 = this.Karatsuba((x0 + x1), (y0 + y1), half);
    let middle = ((p3 - p1) - p2);
    return (((p2 * this.pow10(n)) + (middle * divisor)) + p1);
  }

  pow10(k) {
    let r = 1;
    for (let i = 0; i <= ((k - 1)); i++) {
        r = (r * 10);
    }
    return r;
  }
}

if (typeof module !== 'undefined') module.exports = Karatsuba;

if (typeof require !== 'undefined' && require.main === module) {
    const X = 0;
    const Y = 0;
    const n = 3;
    const prog = new Karatsuba();
    const result = prog.Karatsuba(X, Y, n);
    console.log(JSON.stringify(result));
}
