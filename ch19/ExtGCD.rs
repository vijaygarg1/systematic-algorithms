// Extended Euclidean algorithm: maintains Bezout coefficients
// alongside GCD reduction.  Returns {gcd, x, y} with x*a + y*b = gcd.

fn ext_gcd(a: i64, b: i64) -> [i64; 3] {
    let mut g = [a, b];
    let mut h0 = [1i64, 0];
    let mut h1 = [0i64, 1];
    while g[0] != g[1] {
        if g[0] > g[1] {
            let q = if g[1] != 0 && g[0] % g[1] == 0 { g[0] / g[1] - 1 } else { g[0] / g[1] };
            g[0]  -= q * g[1];
            h0[0] -= q * h1[0];
            h0[1] -= q * h1[1];
        } else {
            let q = if g[0] != 0 && g[1] % g[0] == 0 { g[1] / g[0] - 1 } else { g[1] / g[0] };
            g[1]  -= q * g[0];
            h1[0] -= q * h0[0];
            h1[1] -= q * h0[1];
        }
    }
    [g[0], h0[0], h0[1]]
}

fn main() {
    let r = ext_gcd(48, 18);
    println!("gcd={}  x={}  y={}", r[0], r[1], r[2]);
}
