// LLP-Sort1: forbidden(j) iff A[j] > A[j+1]; advance: swap.

fn llp_sort1(a: &mut [i32]) {
    let n = a.len();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n.saturating_sub(1) {
            if a[j] > a[j + 1] { a.swap(j, j + 1); changed = true; }
        }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    llp_sort1(&mut a);
    println!("{:?}", a);
}
