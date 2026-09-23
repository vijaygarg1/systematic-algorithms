// Min-heap operations on a flat Vec<i32>.

fn heapify(a: &mut [i32], i: usize, n: usize) {
    let (mut smallest, left, right) = (i, 2 * i + 1, 2 * i + 2);
    if left  < n && a[left]  < a[smallest] { smallest = left; }
    if right < n && a[right] < a[smallest] { smallest = right; }
    if smallest != i {
        a.swap(i, smallest);
        heapify(a, smallest, n);
    }
}

fn build(a: &mut [i32], n: usize) {
    if n < 2 { return; }
    for i in (0..=n/2 - 1).rev() { heapify(a, i, n); }
}

fn extract_min(a: &mut Vec<i32>) -> Option<i32> {
    if a.is_empty() { return None; }
    let m = a[0];
    let last = a.len() - 1;
    a[0] = a[last];
    a.pop();
    let n = a.len();
    if n > 0 { heapify(a, 0, n); }
    Some(m)
}

fn main() {
    let mut a: Vec<i32> = vec![9, 4, 7, 1, 8, 3, 6, 2, 5];
    let n = a.len();
    build(&mut a, n);
    println!("after build:  {:?}", a);
    println!("extractMin -> {}", extract_min(&mut a).unwrap());
    println!("after extract: {:?}", a);
}
