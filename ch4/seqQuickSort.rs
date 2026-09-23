// Sequential QuickSort with Lomuto partition.

fn partition(a: &mut [i32], lo: usize, hi: usize) -> usize {
    let pivot = a[hi];
    let mut i = lo;
    for j in lo..hi {
        if a[j] <= pivot { a.swap(i, j); i += 1; }
    }
    a.swap(i, hi);
    i
}

fn seq_quick_sort(a: &mut [i32], lo: usize, hi: usize) {
    if lo < hi {
        let p = partition(a, lo, hi);
        if p > 0 { seq_quick_sort(a, lo, p - 1); }
        seq_quick_sort(a, p + 1, hi);
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    let n = a.len();
    seq_quick_sort(&mut a, 0, n - 1);
    println!("{:?}", a);
}
