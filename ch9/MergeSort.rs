// Parallel-style MergeSort (composes the two halves with `;` here for
// the sequential build).

fn merge(lo: usize, mid: usize, hi: usize, g: &mut [i32]) {
    let b: Vec<i32> = g.to_vec();
    let mut i = lo;
    let mut j = mid + 1;
    let mut k = lo;
    while i <= mid && j <= hi {
        if b[i] <= b[j] { g[k] = b[i]; i += 1; }
        else            { g[k] = b[j]; j += 1; }
        k += 1;
    }
    while i <= mid { g[k] = b[i]; i += 1; k += 1; }
    while j <= hi  { g[k] = b[j]; j += 1; k += 1; }
}

fn merge_sort(lo: usize, hi: usize, g: &mut [i32]) {
    if lo < hi {
        let mid = (lo + hi) / 2;
        merge_sort(lo, mid, g);
        merge_sort(mid + 1, hi, g);
        merge(lo, mid, hi, g);
    }
}

fn main() {
    let mut g = vec![5, 2, 4, 6, 1, 3];
    let n = g.len();
    merge_sort(0, n - 1, &mut g);
    println!("{:?}", g);
}
