// Recursive factorial.  factorial(n) = n * factorial(n-1) with base 1.

fn factorial(n: u32) -> u64 {
    if n == 0 { 1 } else { n as u64 * factorial(n - 1) }
}

fn main() {
    for n in 0..7 {
        println!("{}! = {}", n, factorial(n));
    }
}
