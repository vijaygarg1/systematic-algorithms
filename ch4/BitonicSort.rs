// BitonicSort: bitonic merge network.  Input length must be a power of two.

fn bitonic_merge(a: &mut [i32], lo: usize, len: usize, ascending: bool) {
    if len <= 1 { return; }
    let k = len / 2;
    for i in lo..lo + k {
        if (a[i] > a[i + k]) == ascending { a.swap(i, i + k); }
    }
    bitonic_merge(a, lo, k, ascending);
    bitonic_merge(a, lo + k, k, ascending);
}

fn bitonic_sort(a: &mut [i32], lo: usize, len: usize, ascending: bool) {
    if len <= 1 { return; }
    let k = len / 2;
    bitonic_sort(a, lo, k, true);
    bitonic_sort(a, lo + k, k, false);
    bitonic_merge(a, lo, len, ascending);
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3, 7, 0];
    let n = a.len();
    bitonic_sort(&mut a, 0, n, true);
    println!("{:?}", a);
}
