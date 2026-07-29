# SwarmX

<p align="center">
    ![Logo](logo.png)
</p>

<div style="text-align: center;">
  <img src="https://github.com/kolithawarnakulasooriya/SwarmX/blob/develop/logo.png" alt="Description">
</div>

SwarmX is an open-source Java framework for swarm intelligence, optimization, and multi-agent simulation. It brings together a collection of swarm-based optimization algorithms, benchmark functions, visualization tools, and example programs in a single, extensible codebase.

The project is designed for researchers, students, and developers who want to experiment with nature-inspired optimization techniques such as Particle Swarm Optimization, Ant Colony Optimization, Grey Wolf Optimizer, and many others.

## Why SwarmX?

SwarmX aims to make swarm intelligence experimentation easy and approachable:

- Explore a wide range of swarm-based optimization algorithms
- Test algorithms against standard benchmark functions
- Visualize convergence behavior and optimization progress
- Build and extend multi-agent systems with a modular architecture
- Use the project as a learning resource or a starting point for research

## Features

- Support for numerous swarm intelligence algorithms, including PSO, ACO, GWO, CS, FA, ABC, BA, TSA, SSA, ZOA, and more
- Built-in objective functions and benchmark problems
- Visualization support for function landscapes and optimization trends
- A multi-agent framework for simulating coordinated behaviors
- Maven-based build and test workflow
- Runnable example programs under the examples package

## Project Structure

The repository is organized into a few main areas:

- `src/main/java/org/usa/soc/si` – single-objective swarm intelligence algorithms and engine components
- `src/main/java/org/usa/soc/multiagent` – multi-agent abstractions and execution utilities
- `src/main/java/org/usa/soc/core` – shared core types, actions, and helpers
- `src/main/java/examples` – runnable example applications and demonstrations
- `src/test/java` – test coverage for the library and examples

## Getting Started

### Prerequisites

- Java 11 or newer
- Maven 3.6 or newer

### Build the project

```bash
git clone <your-fork-or-repo-url>
cd SwarmX
mvn compile
mvn test
```

### Run the examples

Several example entry points are available under the examples package. You can run them from your IDE by launching the `main` methods in classes such as:

- `examples.si.PlotAlgorithmExample`
- `examples.si.AllDisplay`
- `examples.multiagent.initial.InitialTest`

## Example Use Cases

SwarmX can be used for:

- Comparing swarm optimization algorithms on benchmark functions
- Visualizing algorithm performance over iterations
- Studying multi-agent coordination and interaction patterns
- Teaching and demonstrating optimization concepts in Java

## Contributing

Contributions are welcome.

If you would like to improve the library, add new algorithms, fix issues, or improve documentation, please open an issue or submit a pull request. A clear description of the change and relevant tests are appreciated.

## License

This project is licensed under the MIT License. See the [LICENSE.md](LICENSE.md) file for details.
