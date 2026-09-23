"""Huffman coding: repeatedly merge the two least-probable nodes into a binary tree."""

import heapq


class Node:
    __slots__ = ("prob", "symbol", "left", "right", "order")

    def __init__(self, prob, symbol=-1, left=None, right=None, order=0):
        self.prob = prob
        self.symbol = symbol
        self.left = left
        self.right = right
        self.order = order  # tie-breaker so heapq never compares Node objects directly

    def __lt__(self, other):
        return (self.prob, self.order) < (other.prob, other.order)


def huffman_codes(p):
    n = len(p)
    Q = [Node(p[i], symbol=i, order=i) for i in range(n)]
    heapq.heapify(Q)
    next_order = n
    for _ in range(n - 1):
        x = heapq.heappop(Q)
        y = heapq.heappop(Q)
        z = Node(x.prob + y.prob, left=x, right=y, order=next_order)
        next_order += 1
        heapq.heappush(Q, z)
    root = Q[0]
    codes = [None] * n
    _assign_codes(root, "", codes)
    return codes


def _assign_codes(node, prefix, codes):
    if node.symbol != -1:
        codes[node.symbol] = prefix or "0"
        return
    _assign_codes(node.left, prefix + "0", codes)
    _assign_codes(node.right, prefix + "1", codes)


if __name__ == "__main__":
    p = [0.45, 0.13, 0.12, 0.16, 0.09, 0.05]
    print("codes:", huffman_codes(p))
