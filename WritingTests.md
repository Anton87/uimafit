**under construction**

# Introduction #

Writing tests without uimaFIT can be a laborious process that results in fragile tests that are ver verbose and break easily when code is refactored.  This page demonstrates how you can write tests that are both concise and robust.  Here's an outline of how you might create a test for a UIMA component _**without**_ uimaFIT:

  1. write a descriptor file that configures your component appropriately for the test.  This requires a minimum of 30-50 lines of XML.
  1. begin a test with 5-10 lines of code that instantiate the e.g. analysis engine.
  1. run the analysis engine against some text and test the contents of the CAS.
  1. repeat steps 1-3 for your next test usually by copying the descriptor file, renaming it, and changing e.g. configuration parameters.

If you have gone through the pain of creating tests like these and then decided you should refactor your code, then you know how tedious it is to maintain them.

Instead of pasting variants of the setup code (see step 2) into other tests we began to create a library of utility methods that we could call which helped shorten our code.  We extended these methods so that we could instantiate our components directly without a descriptor file.  These utility methods became the initial core of uimaFIT.

# Examples #

  * There are a number of examples of unit tests in both the test suite for the uimafit project and the uimafit-examples project.  In particular, there are some well-documented unit tests in the latter which can be found in [RoomNumberAnnotator1Test.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/test/java/org/uimafit/examples/tutorial/ex1/RoomNumberAnnotator1Test.java)
  * You can improve your testing strategy by introducing a TestBase class such as the one found in [ExamplesTestBase.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/test/java/org/uimafit/examples/tutorial/ExamplesTestBase.java).  This class is intended as a super class for your other test classes and sets up a JCas that is always ready to use along with a TypeSystemDescription and a TypePriorities.  An example test that subclasses from ExamplesTestBase is [RoomNumberAnnotator2Test.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/test/java/org/uimafit/examples/tutorial/ex1/RoomNumberAnnotator2Test.java)
  * Most AnalysisEngines that you want to test will generally be downstream of many other components that add annotations to the CAS.  These annotations will likely need to be in the CAS so that a downstream AnalysisEngine will do something sensible.  This poses a problem for tests because it may be undesirable to set up and run an entire pipeline every time you want to test a downstream AnalysisEngine.  Furthermore, such tests can become fragile in the face of behavior changes to upstream components.  For this reason, it can be advantageous to serialize a CAS as an XMI file and use this as a starting point rather than running an entire pipeline.  An example of this approach can be found in [XmiTest.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/test/java/org/uimafit/examples/xmi/XmiTest.java).

# Tips & Tricks #
The package org.uimafit.testing provides some utility classes that can be handy when writing tests for UIMA components.  You may find the following suggestions useful:
  * add a org.uimafit.testing.factory.TokenBuilder instance to your TestBase class.  An example of this can be found in [ComponentTestBase.java](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT/src/test/java/org/uimafit/ComponentTestBase.java).  This makes it easy to add tokens and sentences to the CAS you are testing which is a common task for many tests.