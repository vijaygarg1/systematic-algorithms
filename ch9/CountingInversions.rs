// Count inversions in O(n log n) via merge sort.

fn merge_and_count(a: &mut [i32], low: usize, mid: usize, high: usize) -> i32 {
    let b: Vec<i32> = a.to_vec();
    let mut i = low;
    let mut j = mid + 1;
    let mut k = low;
    let mut inv = 0i32;
    while i <= mid && j <= high {
        if b[i] <= b[j] { a[k] = b[i]; i += 1; }
        else { a[k] = b[j]; j += 1; inv += (mid - i + 1) as i32; }
        k += 1;
    }
    while i <= mid { a[k] = b[i]; i += 1; k += 1; }
    while j <= high { a[k] = b[j]; j += 1; k += 1; }
    inv
}

fn count(a: &mut [i32], low: usize, high: usize) -> i32 {
    if low >= high { return 0; }
    let mid = (low + high) / 2;
    let inv_l = count(a, low, mid);
    let inv_r = count(a, mid + 1, high);
    let inv_m = merge_and_count(a, low, mid, high);
    inv_l + inv_r + inv_m
}

fn main() {
    let mut a = vec![5, 2, 4, 6, 1, 3];
    let n = a.len();
    let inv = count(&mut a, 0, n - 1);
    println!("sorted: {:?}, inversions: {}", a, inv);
}
