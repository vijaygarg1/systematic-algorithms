// Recursive MergeSort skeleton (sequential).  Sorts A[low..=high] in place.

fn merge(a: &mut [i32], low: usize, mid: usize, high: usize) {
    let b: Vec<i32> = a.to_vec();
    let (mut i, mut j, mut k) = (low, mid + 1, low);
    while i <= mid && j <= high {
        if b[i] <= b[j] { a[k] = b[i]; i += 1; }
        else            { a[k] = b[j]; j += 1; }
        k += 1;
    }
    while i <= mid  { a[k] = b[i]; i += 1; k += 1; }
    while j <= high { a[k] = b[j]; j += 1; k += 1; }
}

fn merge_sort(a: &mut [i32], low: usize, high: usize) {
    if low < high {
        let mid = (low + high) / 2;
        merge_sort(a, low, mid);
        merge_sort(a, mid + 1, high);
        merge(a, low, mid, high);
    }
}

fn main() {
    let mut a = vec![5, 2, 4, 6, 1, 3];
    let n = a.len();
    merge_sort(&mut a, 0, n - 1);
    println!("{:?}", a);
}
