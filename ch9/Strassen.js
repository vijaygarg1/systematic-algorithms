/*
 * Strassen's divide-and-conquer matrix multiplication over int[][].
 * Splitting each n x n operand into four n/2 quadrants, the product needs
 * only SEVEN recursive multiplications instead of the obvious eight, which
 * is what drops the exponent from 3 to log2(7) ~ 2.81. The saving comes
 * from M1..M7 below: each is one product of sums, and the four output
 * quadrants are recovered from them by addition alone.
 *
 * Sizes are assumed to be powers of two, so every split is exact.
 *
 * Auto-generated from Strassen.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Strassen {
  multiply(A, B) {
    this.n = A.length;
    if ((this.n === 1)) {
        let C1 = Array.from({length: 1}, () => new Array(1).fill(0));
        C1[0][0] = (A[0][0] * B[0][0]);
        return C1;
    }
    let h = Math.trunc(this.n / 2);
    let A11 = this.quadrant(A, 0, 0, h);
    let A12 = this.quadrant(A, 0, h, h);
    let A21 = this.quadrant(A, h, 0, h);
    let A22 = this.quadrant(A, h, h, h);
    let B11 = this.quadrant(B, 0, 0, h);
    let B12 = this.quadrant(B, 0, h, h);
    let B21 = this.quadrant(B, h, 0, h);
    let B22 = this.quadrant(B, h, h, h);
    let M1 = this.multiply(this.add(A11, A22), this.add(B11, B22));
    let M2 = this.multiply(this.add(A21, A22), B11);
    let M3 = this.multiply(A11, this.sub(B12, B22));
    let M4 = this.multiply(A22, this.sub(B21, B11));
    let M5 = this.multiply(this.add(A11, A12), B22);
    let M6 = this.multiply(this.sub(A21, A11), this.add(B11, B12));
    let M7 = this.multiply(this.sub(A12, A22), this.add(B21, B22));
    let C11 = this.add(this.sub(this.add(M1, M4), M5), M7);
    let C12 = this.add(M3, M5);
    let C21 = this.add(M2, M4);
    let C22 = this.add(this.add(this.sub(M1, M2), M3), M6);
    return this.combine(C11, C12, C21, C22);
  }

  quadrant(M, r, c, h) {
    let Q = Array.from({length: h}, () => new Array(h).fill(0));
    for (let i = 0; i <= ((h - 1)); i++) {
        for (let j = 0; j <= ((h - 1)); j++) {
            Q[i][j] = M[(r + i)][(c + j)];
        }
    }
    return Q;
  }

  add(X, Y) {
    let h = X.length;
    let S = Array.from({length: h}, () => new Array(h).fill(0));
    for (let i = 0; i <= ((h - 1)); i++) {
        for (let j = 0; j <= ((h - 1)); j++) {
            S[i][j] = (X[i][j] + Y[i][j]);
        }
    }
    return S;
  }

  sub(X, Y) {
    let h = X.length;
    let D = Array.from({length: h}, () => new Array(h).fill(0));
    for (let i = 0; i <= ((h - 1)); i++) {
        for (let j = 0; j <= ((h - 1)); j++) {
            D[i][j] = (X[i][j] - Y[i][j]);
        }
    }
    return D;
  }

  combine(C11, C12, C21, C22) {
    let h = C11.length;
    let C = Array.from({length: (2 * h)}, () => new Array((2 * h)).fill(0));
    for (let i = 0; i <= ((h - 1)); i++) {
        for (let j = 0; j <= ((h - 1)); j++) {
            C[i][j] = C11[i][j];
            C[i][(j + h)] = C12[i][j];
            C[(i + h)][j] = C21[i][j];
            C[(i + h)][(j + h)] = C22[i][j];
        }
    }
    return C;
  }
}

if (typeof module !== 'undefined') module.exports = Strassen;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const B = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new Strassen();
    const result = prog.multiply(A, B);
    console.log(JSON.stringify(result));
}
