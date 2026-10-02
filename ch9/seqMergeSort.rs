// Sequential MergeSort.

fn merge(a: &mut [i32], lo: usize, mid: usize, hi: usize) {
    let b: Vec<i32> = a.to_vec();
    let (mut i, mut j, mut k) = (lo, mid + 1, lo);
    while i <= mid && j <= hi {
        if b[i] <= b[j] { a[k] = b[i]; i += 1; } else { a[k] = b[j]; j += 1; }
        k += 1;
    }
    while i <= mid { a[k] = b[i]; i += 1; k += 1; }
    while j <= hi  { a[k] = b[j]; j += 1; k += 1; }
}

fn seq_merge_sort(a: &mut [i32], lo: usize, hi: usize) {
    if lo < hi {
        let mid = (lo + hi) / 2;
        seq_merge_sort(a, lo, mid);
        seq_merge_sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    let n = a.len();
    seq_merge_sort(&mut a, 0, n - 1);
    println!("{:?}", a);
}
