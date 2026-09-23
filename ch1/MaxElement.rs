// Single-pass scan for the maximum of a non-empty slice.

fn max_element(a: &[i32]) -> i32 {
    *a.iter().max().expect("non-empty")
}

fn main() {
    let a = [4, 2, 7, 1, 9, 3, 8];
    println!("max = {}", max_element(&a));
}
