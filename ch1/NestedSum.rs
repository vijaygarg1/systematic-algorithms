// Sum of products i*j over a nested loop -- a Theta(n^2) toy.

fn nested_sum(n: u32) -> u64 {
    let mut total: u64 = 0;
    for i in 0..n { for j in 0..n { total += i as u64 * j as u64; } }
    total
}

fn main() {
    for &n in &[0, 1, 5, 10] {
        println!("nestedSum({}) = {}", n, nested_sum(n));
    }
}
