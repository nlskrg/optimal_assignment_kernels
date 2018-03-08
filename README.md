# Optimal Assignment Kernels
Source code for the paper [On Valid Optimal Assignment Kernels and Applications to Graph Classification](http://papers.nips.cc/paper/6166-on-valid-optimal-assignment-kernels-and-applications-to-graph-classification.pdf), Nils M. Kriege, Pierre-Louis Giscard, Richard C. Wilson, NIPS 2016.

## Usage
The graph kernels contained in this package can be computed via a command line interface. Run the shell script `kkernel` to see a list of all available kernels and parameters.

### Example
The following command computes the Weisfeiler-Lehman optimal assignment kernel with 0 to 4 refinement steps for the data set ENZYMES:
```
./kkernel -d ENZYMES wloa -h 0,1,2,3,4
```
For each value of `h` the kernel matrix is computed and stored in the directory `gram` using the [LIBSVM](https://www.csie.ntu.edu.tw/~cjlin/libsvm/) file format.

## Building from source
Run `ant` to build `kgraph.jar` from source. 

## Data sets
The repository contains the data set ENZYMES only. Further data sets are available from the website [Benchmark Data Sets for Graph Kernels](http://graphkernels.cs.tu-dortmund.de). Please note that in our experimental comparison the edge labels, if present, were ignored. In order to reproduce the published results, please delete the files `DS_edge_labels.txt`, where `DS` is the name of the data set.

## Terms and conditions
When using our code please cite:

	@InCollection{NIPS2016_6166,
	  title                    = {On Valid Optimal Assignment Kernels and Applications to Graph Classification},
	  author                   = {Kriege, Nils M. and Giscard, Pierre-Louis and Wilson, Richard},
	  booktitle                = {Advances in Neural Information Processing Systems 29},
	  publisher                = {Curran Associates, Inc.},
	  year                     = {2016},
	  editor                   = {D. D. Lee and M. Sugiyama and U. V. Luxburg and I. Guyon and R. Garnett},
	  pages                    = {1623--1631}
	}

## Contact information
If you have any questions, please contact [Nils Kriege](https://ls11-www.cs.tu-dortmund.de/staff/kriege).

## Links
A Matlab implementation of the Weisfeiler-Lehman optimal assignment kernel is available from [Matlab File Exchange](http://de.mathworks.com/matlabcentral/fileexchange/64711-weisfeiler-lehman-optimal-assignment-kernel).

