// Towers of Hanoi.

fn hanoi(n: u32, source: char, aux: char, target: char) {
    if n == 1 {
        println!("move disk 1 from {} to {}", source, target);
    } else {
        hanoi(n - 1, source, target, aux);
        println!("move disk {} from {} to {}", n, source, target);
        hanoi(n - 1, aux, source, target);
    }
}

fn main() {
    hanoi(3, 'A', 'B', 'C');
}
