// MergeTwo: out-of-place merge of two sorted slices into a Vec.

fn merge_two(b: &[i32], c: &[i32]) -> Vec<i32> {
    let (mut i, mut j) = (0, 0);
    let mut d = Vec::with_capacity(b.len() + c.len());
    while i < b.len() && j < c.len() {
        if b[i] < c[j] { d.push(b[i]); i += 1; } else { d.push(c[j]); j += 1; }
    }
    d.extend_from_slice(&b[i..]);
    d.extend_from_slice(&c[j..]);
    d
}

fn main() {
    let b = [1, 4, 5, 8];
    let c = [2, 3, 6, 7];
    println!("{:?}", merge_two(&b, &c));
}
