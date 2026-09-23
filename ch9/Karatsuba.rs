// Karatsuba multiplication: three half-size products instead of four.

fn pow10(k: u32) -> i64 {
    let mut r: i64 = 1;
    for _ in 0..k { r *= 10; }
    r
}

fn multiply(x: i64, y: i64, n: u32) -> i64 {
    if n == 1 { return x * y; }
    let half = n / 2;
    let divisor = pow10(half);
    let x1 = x / divisor;
    let x0 = x - x1 * divisor;
    let y1 = y / divisor;
    let y0 = y - y1 * divisor;
    let p1 = multiply(x0, y0, half);
    let p2 = multiply(x1, y1, half);
    let p3 = multiply(x0 + x1, y0 + y1, half);
    let middle = p3 - p1 - p2;
    p2 * pow10(n) + middle * divisor + p1
}

fn main() {
    let cases: [(i64, i64, u32); 3] = [(1234, 5678, 4), (12, 34, 2), (8, 9, 1)];
    for &(x, y, n) in &cases {
        let got = multiply(x, y, n);
        println!("{} * {} = {} (expected {})", x, y, got, x * y);
    }
}
