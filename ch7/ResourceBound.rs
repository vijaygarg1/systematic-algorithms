// ResourceBound: composition program enforcing rho[j] <= B.

fn resource_bound(rho: &[i32], b: i32, g: Vec<i32>) -> Option<Vec<i32>> {
    if rho.iter().any(|&r| r > b) { None } else { Some(g) }
}

fn main() {
    let rho = [0, 2, 5, 9];
    let g = vec![0, 4, 8, 15];
    match resource_bound(&rho, 7, g) {
        Some(out) => println!("G: {:?}", out),
        None => println!("infeasible"),
    }
}
