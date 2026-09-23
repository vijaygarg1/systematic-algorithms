// Strassen's matrix multiplication: 7 recursive products instead of 8.

type Matrix = Vec<Vec<i64>>;

fn add(x: &Matrix, y: &Matrix) -> Matrix {
    let n = x.len();
    (0..n).map(|i| (0..n).map(|j| x[i][j] + y[i][j]).collect()).collect()
}

fn sub(x: &Matrix, y: &Matrix) -> Matrix {
    let n = x.len();
    (0..n).map(|i| (0..n).map(|j| x[i][j] - y[i][j]).collect()).collect()
}

fn block(m: &Matrix, r: usize, c: usize, size: usize) -> Matrix {
    (0..size).map(|i| (0..size).map(|j| m[r + i][c + j]).collect()).collect()
}

fn combine(c11: &Matrix, c12: &Matrix, c21: &Matrix, c22: &Matrix) -> Matrix {
    let h = c11.len();
    let n = 2 * h;
    let mut c = vec![vec![0i64; n]; n];
    for i in 0..h {
        for j in 0..h {
            c[i][j] = c11[i][j];
            c[i][j + h] = c12[i][j];
            c[i + h][j] = c21[i][j];
            c[i + h][j + h] = c22[i][j];
        }
    }
    c
}

fn multiply(a: &Matrix, b: &Matrix, n: usize) -> Matrix {
    if n == 1 {
        return vec![vec![a[0][0] * b[0][0]]];
    }
    let h = n / 2;
    let (a11, a12, a21, a22) = (block(a, 0, 0, h), block(a, 0, h, h), block(a, h, 0, h), block(a, h, h, h));
    let (b11, b12, b21, b22) = (block(b, 0, 0, h), block(b, 0, h, h), block(b, h, 0, h), block(b, h, h, h));

    let m1 = multiply(&add(&a11, &a22), &add(&b11, &b22), h);
    let m2 = multiply(&add(&a21, &a22), &b11, h);
    let m3 = multiply(&a11, &sub(&b12, &b22), h);
    let m4 = multiply(&a22, &sub(&b21, &b11), h);
    let m5 = multiply(&add(&a11, &a12), &b22, h);
    let m6 = multiply(&sub(&a21, &a11), &add(&b11, &b12), h);
    let m7 = multiply(&sub(&a12, &a22), &add(&b21, &b22), h);

    let c11 = add(&sub(&add(&m1, &m4), &m5), &m7);
    let c12 = add(&m3, &m5);
    let c21 = add(&m2, &m4);
    let c22 = add(&sub(&add(&m1, &m3), &m2), &m6);
    combine(&c11, &c12, &c21, &c22)
}

fn main() {
    let a: Matrix = vec![vec![1, 2], vec![3, 4]];
    let b: Matrix = vec![vec![5, 6], vec![7, 8]];
    let c = multiply(&a, &b, 2);
    for row in c {
        println!("{}", row.iter().map(|v| v.to_string()).collect::<Vec<_>>().join(" "));
    }
}
