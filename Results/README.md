# How to understand this data?

Here are all the results obtained using the 'Experiment' script of this project. In each file we have all the names of the test classes tat were selected.
Also, the file naming follow a convention:

(ProjectName)\_[(List Of All Smells Selected)]\_(Number of selected Classes)\_of\_(Total number of classes).txt

So, for project Cli, using GodClass and Long Method smells, with 10 of 100 classes selected we would have:

Cli_[GodClass,LongMethod]_10_of_100.txt

- For the cases where the modified classes and the smelly classes where considered, there is a "_WithModified" ammendment in the end of the file name
- For the case of only using modified classes, the name hass: "_OnlyModified_"

The folder structure is:

- Results
-- GodClass+LargeMethod+Comments+Modified => Experiment using God Class, LargeMethod, Comments and the modified classes.
-- OnlySmells-GodClass_Comments_and_Large_Method => Experiment using only God Class, LargeMethod, Comments.
-- Original => Experiment using only modified classes.
-- PossibleSelectedFixed => Only for reference, have all the classes the cound be selected for a given project.
