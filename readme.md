# Gobbler

## Overview

* Gobbler Server and Gobbler client class have the core logic in them
* My approach doesn't use slurping of files into memory but rather uses an line iterator approach
* I used Apache Common I/O project's lineiterator to do the traversing
* In addition I've added a LRU cache implementation which will cache the values lookedup to improve the speed.
* The project is packaged as a maven project and can be easily imported into an IDE like IntelliJ
* It took about 4 hours to think through the problem and implement the working code and performance test it.
* I've used wiki dumps text files which ranged between 50GB-100GB to test it. The retrieval was in order of milliseconds to traverse millions of lines.
* It can be optimized by more optimized caching in pre-processing steps but it can only be done after collecting the usage patterns to efficiently improve the design.
