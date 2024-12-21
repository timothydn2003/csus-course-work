def bellman_ford(graph, source):
    # Find the number of unique nodes in the graph
    # Start
    unique_nodes = set()

    for i in graph:
        unique_nodes.add(i[0])
        unique_nodes.add(i[1])

    num_nodes = len(unique_nodes)
    # End

    # Initialize distance vector with infinity and set distance to self is 0
    # Start
    distance = {i: float("Inf") for i in unique_nodes}
    # set distance to self as 0
    distance[source] = 0
    # End

    # Iteratively update distance vector for the source and destination.
    # graph = graph + undirected_graph_edges
    for _ in range(num_nodes - 1):
        for source, dest, cost in graph:
            # Start
            # original vector
            if distance[source] + cost < distance[dest]:
                distance[dest] = distance[source] + cost

            # other direction of original vector
            if distance[dest] + cost < distance[source]:
                distance[source] = distance[dest] + cost

            # End

    # Check for negative weight cycles
    for source, dest, cost in graph:
        # If the sum of the distance to the source node and the cost of the edge is less than
        # the distance to the destination node, or if the sum of the distance to the destination
        # node and the cost of the edge is less than the distance to the source node.
        # Start
        if distance[source] + cost < distance[dest] or distance[dest] + cost < distance[source]:
            print("Graph contains a negative-weight cycle")
            return
        # End

    print(list(distance.values()))

# Define the undirected graph edges with their costs.

# Graph shown in Figure 1
# graph = [
#     (1, 2, 2),
#     (1, 3, 7),
#     (2, 3, 1)
# ]

# Graph with negative weight cycle shown in Figure 2
# graph = [
#     (1, 2, -2),
#     (1, 3, -2),
#     (2, 3, 1),
# ]

graph = [
    (1,2,3),
    (1,3,5),
    (2,5,7),
    (2,4,6),
    (5,4,2),
    (4,3,4)
]

# Test the function
shortest_path = bellman_ford(graph, 3)
shortest_path
