The [uimafit-examples](http://code.google.com/p/uimafit/source/browse/#svn/trunk/uimaFIT-examples/src/main/java/org/uimafit/examples) project contains a package org.uimafit.examples.experiment.pos which demonstrates a very simple experimental setup for testing a part-of-speech tagger.  You may find this example more accessible if you check out the code from subversion and build it in your own environment.  Instructions for doing this can be found [here](DeveloperSetup.md).

The documentation for this example can be found in the code itself.  Please refer to [RunExperiment.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/main/java/org/uimafit/examples/experiment/pos/RunExperiment.java) as a starting point.  The following is copied from the javadoc comments of that file:

RunExperiment.java demonstrates a very common (though simplified) experimental setup in which gold standard data is available for some task and you want to evaluate how well your analysis engine works against that data. Here we are evaluating "BaselineTagger" which is a (ridiculously) simple part-of-speech tagger against the part-of-speech tags found in "src/main/resources/org/uimafit/examples/pos/sample.txt.pos".

The basic strategy is as follows:
  * post the data "as is" into the default view
  * parse the gold-standard tokens and part-of-speech tags and put the results into another view we will call GOLD\_VIEW
  * create another view called SYSTEM\_VIEW and copy the text and Token annotations from the GOLD\_VIEW into this view
  * Run the BaselineTagger on the SYSTEM\_VIEW over the copied Token annoations
  * Evaluate the part-of-speech tags found in the SYSTEM\_VIEW with those in the GOLD\_VIEW