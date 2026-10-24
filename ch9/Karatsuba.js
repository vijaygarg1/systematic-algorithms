/*
 * Karatsuba multiplication: three half-size products instead of four.
 *
 * Auto-generated from Karatsuba.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Karatsuba {
  multiply(X, Y, n) {
    if ((n === 1)) {
        return (X * Y);
    }
    let half = Math.trunc(n / 2);
    let divisor = this.pow10(half);
    let x1 = Math.trunc(X / divisor);
    let x0 = (X - (x1 * divisor));
    let y1 = Math.trunc(Y / divisor);
    let y0 = (Y - (y1 * divisor));
    let p1 = this.multiply(x0, y0, half);
    let p2 = this.multiply(x1, y1, half);
    let p3 = this.multiply((x0 + x1), (y0 + y1), half);
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
    const result = prog.multiply(X, Y, n);
    console.log(JSON.stringify(result));
}
