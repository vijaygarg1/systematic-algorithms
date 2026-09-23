// GCD using the mod operation.

fn gcd(mut a: i32, mut b: i32) -> i32 {
    while a != b {
        if a > b {
            if a % b == 0 { a = b; }
            else           { a = a % b; }
        } else {
            if b % a == 0 { b = a; }
            else           { b = b % a; }
        }
    }
    a
}

fn main() {
    for p in &[(48, 18), (100, 75), (17, 5), (12, 12)] {
        println!("gcd({}, {}) = {}", p.0, p.1, gcd(p.0, p.1));
    }
}
