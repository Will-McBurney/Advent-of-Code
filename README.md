# McBurney's Advent of Code - Kotlin

Hi everyone, welcome to my advent of code repo. I discovered advent of code in December 2023, and am immediately hooked.
I plan to use this repo to write Advent of Code. For the time being, I plan to implement all solutions using the 
[Kotlin programming language](https://kotlinlang.org/).

## Current solved (last updated Dec 20, 2023)

* 2015 - 1 through 20
* 2016 - 1 through 3
* 2023 - 1 through 20

## Advent of Code

My goal is to complete all Advent of Code projects in this repo, including previous years. This includes clearing
all part 1s and part 2s

## Project structure

All source code is in `src/main/kotlin/yearXX/dayYY` where `XX` is the two-digit year (i.e., 15, 23) and
`YY` is the two digit day (from `00` to `25`)

All input files are intended to be read out of the resources folder, so input files for, say, Year 15 Day 09, should be
stored in `src/main/resources/year15/day09`, typically as `input.txt`

## Rules for completion

The following are my rules for considering a Part "done":
1) The solution should generally solve all input files to the problem 
    1) Note that some problems use data that intentionally allows for non-general solutions. For example,
   [AoC '23 Day 08](https://adventofcode.com/2023/day/8)'s input is intentionally designed to all for a 
   least-common-denominator based approach for Part 2, so my Part 2 wouldn't work on general input that 
   didn't allow for that.
   
2) I have cleaned up the code to ensure it is "readable" to my standards (that is, this code is at the level I would
be willing to share it with my students as an example solution to a given problem. This doesn't include documentation
3) I have removed any obvious bad optimizations (bad data structure choices, bad algorithms, unnecessary recalculations, etc.)

These rules weren't initially established, and so I'm looking to clean up those early implementations.

## Feedback

If you have any feedback, you can contact me or leave a comment on the code. However, because I want this to be my
personal project, I will not be accepting any pull requests.