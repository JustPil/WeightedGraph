# WeightedGraph
Summary

Graph ADT implemented as a Directed or Undirected Graph.

Design

Directed Graph implementation uses an adjacency list for internal edge storage, where connected vertices are represented with a linked list of connected vertices.

Undirected Graph implementation uses an adjacency matrix for internal edge storage, where the indices of a two-dimensional array represent any two vertices, and the value at the index represents the edge weight between those two vertices.

Includes methods for adding, removing, and retrieving vertices/edges, marking vertices for graph traversal, and retrieving all connected vertices to any one particular vertex.
