// Huffman coding: repeatedly merge the two least-probable nodes into a binary tree.

use std::cmp::Ordering;
use std::collections::BinaryHeap;

struct Node {
    prob: f64,
    symbol: i32,
    left: Option<Box<Node>>,
    right: Option<Box<Node>>,
}

impl Node {
    fn leaf(prob: f64, symbol: i32) -> Self {
        Node { prob, symbol, left: None, right: None }
    }
    fn internal(prob: f64, left: Node, right: Node) -> Self {
        Node { prob, symbol: -1, left: Some(Box::new(left)), right: Some(Box::new(right)) }
    }
}

// Reverse ordering so BinaryHeap (a max-heap) behaves as a min-heap on `prob`.
impl PartialEq for Node {
    fn eq(&self, other: &Self) -> bool { self.prob == other.prob }
}
impl Eq for Node {}
impl PartialOrd for Node {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> { Some(self.cmp(other)) }
}
impl Ord for Node {
    fn cmp(&self, other: &Self) -> Ordering {
        other.prob.partial_cmp(&self.prob).unwrap_or(Ordering::Equal)
    }
}

fn assign_codes(node: &Node, prefix: String, codes: &mut Vec<String>) {
    if node.symbol != -1 {
        codes[node.symbol as usize] = if prefix.is_empty() { "0".to_string() } else { prefix };
        return;
    }
    assign_codes(node.left.as_ref().unwrap(), format!("{}0", prefix), codes);
    assign_codes(node.right.as_ref().unwrap(), format!("{}1", prefix), codes);
}

fn huffman_codes(p: &[f64]) -> Vec<String> {
    let n = p.len();
    let mut q: BinaryHeap<Node> =
        p.iter().enumerate().map(|(i, &pr)| Node::leaf(pr, i as i32)).collect();
    for _ in 1..n {
        let x = q.pop().unwrap();
        let y = q.pop().unwrap();
        q.push(Node::internal(x.prob + y.prob, x, y));
    }
    let root = q.pop().unwrap();
    let mut codes = vec![String::new(); n];
    assign_codes(&root, String::new(), &mut codes);
    codes
}

fn main() {
    let p = vec![0.45, 0.13, 0.12, 0.16, 0.09, 0.05];
    let codes = huffman_codes(&p);
    println!("codes: {:?}", codes);
}
