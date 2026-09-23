// HopBound: composition program enforcing h[j] <= k.

fn hop_bound(h: &[i32], k: i32, g: Vec<i32>) -> Option<Vec<i32>> {
    if h.iter().any(|&hi| hi > k) { None } else { Some(g) }
}

fn main() {
    let h = [0, 1, 2, 3];
    let g = vec![0, 4, 9, 15];
    match hop_bound(&h, 2, g) {
        Some(out) => println!("G: {:?}", out),
        None => println!("infeasible"),
    }
}
