// Ord: multiplicative order of a modulo n via descending LLP on
// divisor lattice.  G[i] = exponent of p[i] in the current candidate
// k = prod p[i]^G[i].

fn modpow(base: i64, mut exp: i64, modulus: i64) -> i64 {
    let mut result = 1i64;
    let mut b = base % modulus;
    while exp > 0 {
        if exp & 1 == 1 { result = result * b % modulus; }
        exp >>= 1;
        b = b * b % modulus;
    }
    result
}

fn ord(a: i64, n: i64, p: &[i64], e: &[i32]) -> i64 {
    let s = p.len();
    let mut g: Vec<i32> = e.to_vec();
    let mut k = 1i64;
    for i in 0..s {
        for _ in 0..e[i] { k *= p[i]; }
    }

    let mut changed = true;
    while changed {
        changed = false;
        for i in 0..s {
            if g[i] > 0 && modpow(a, k / p[i], n) == 1 {
                g[i] -= 1;
                k /= p[i];
                changed = true;
            }
        }
    }
    k
}

fn main() {
    let p = [2i64, 3];
    let e = [1i32, 1];
    println!("ord_7(2) = {}", ord(2, 7, &p, &e));
}
