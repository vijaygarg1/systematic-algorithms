// LLP-Sort2: forbidden(j) iff some k > j has A[j] > A[k]; advance: swap.

fn first_witness(j: usize, a: &[i32]) -> Option<usize> {
    ((j + 1)..a.len()).find(|&k| a[j] > a[k])
}

fn llp_sort2(a: &mut [i32]) {
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..a.len() {
            if let Some(k) = first_witness(j, a) { a.swap(j, k); changed = true; }
        }
    }
}

fn main() {
    let mut a = [5, 2, 4, 6, 1, 3];
    llp_sort2(&mut a);
    println!("{:?}", a);
}
