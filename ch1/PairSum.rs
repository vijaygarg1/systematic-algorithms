// Count pairs (i, j) with i < j whose values sum to target.

fn pair_sum(a: &[i32], target: i32) -> u32 {
    let n = a.len();
    let mut count = 0u32;
    for i in 0..n {
        for j in i+1..n {
            if a[i] + a[j] == target { count += 1; }
        }
    }
    count
}

fn main() {
    let a = [1, 5, 2, 7, 3, 4, 6];
    println!("pairSum(A, 8) = {}", pair_sum(&a, 8));
}
