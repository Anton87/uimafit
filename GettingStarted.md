<h1>Getting Started</h1>



This quick start tutorial demonstrates how to use uimaFIT to define and set a configuration parameter in an analysis engine, run it, and generate a descriptor file for it.  The complete code for this example can be found **[here](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT-examples/src/main/java/org/uimafit/examples/getstarted)**.

## A simple analysis engine implementation ##

Here is the complete analysis engine implementation for this example.

```
public class GetStartedQuickAE extends org.uimafit.component.JCasAnnotator_ImplBase {
  public static final String PARAM_STRING = "stringParam";
  @ConfigurationParameter(name = PARAM_STRING)
  private String stringParam;
	
  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    System.out.println("Hello world!  Say 'hi' to " + stringParam);
  }
}
```

The first thing to note is that the member variable 'stringParam' is annotated with `@ConfigurationParameter` which tells uimaFIT that this is an analysis engine configuration parameter. It is best practice to create a public constant for the parameter name, here `PARAM_STRING` The second thing to note is that we extend uimaFIT's version of the `JCasAnnotator\_ImplBas. The initialize method of this super class calls:

```
ConfigurationParameterInitializer.initializeConfigurationParameters(Object, UimaContext) 
```

which populates the configuration parameters with the appropriate contents of the UimaContext.  If you do not want to extend uimaFIT's `JCasAnnotator_ImplBase`, then you can call this method directly in the initialize method of your analysis engine or any class that implements [org.uimafit.factory.initializable.Initializable](http://uimafit.googlecode.com/svn/trunk/uimaFIT/apidocs/org/uimafit/factory/initializable/Initializable.html).  You can call this method for an instance of any class that has configuration parameters.

## Running the analysis engine ##

The following lines of code demonstrate how to instantiate and run the analysis engine from a main method:

```
JCas jCas = JCasFactory.createJCas();

AnalysisEngine analysisEngine = 
    AnalysisEngineFactory.createPrimitive(GetStartedQuickAE.class,
    GetStartedQuickAE.PARAM_STRING, "uimaFIT");

analysisEngine.process(jCas);
```

In a more involved example, we would probably instantiate a collection reader and run this analysis engine over a collection of documents.  Here, it suffices to simply create a JCas.  Line 3 instantiates the analysis engine using `AnalysisEngineFactory` and sets the string parameter named "stringParam" to the value "uimaFIT".  Running this simple program sends the following output to the console:

```
Hello world!  Say 'hi' to uimaFIT
```

Normally you would be using a type system with your analysis components. When using uimaFIT, it is easiest to keep your type system descriptors in your source folders and make them known to uimaFIT. To do so, create a file `META-INF/org.uimafit/types.txt` in a source folder and add references to all your type descriptors to the file, one per line. You can also use wildcards. For example:

```
classpath*:org/uimafit/examples/type/Token.xml
classpath*:org/uimafit/examples/type/Sentence.xml
classpath*:org/uimafit/examples/tutorial/type/*.xml
```

## Generate a descriptor file ##

The following lines of code demonstrate how a descriptor file can be generated using the class definition:

```
AnalysisEngine analysisEngine = 
    AnalysisEngineFactory.createPrimitive(GetStartedQuickAE.class,
    GetStartedQuickAE.PARAM_STRING, "uimaFIT");
    
analysisEngineDescription.toXML(new FileOutputStream("GetStartedQuickAE.xml"));
```

If you open the resulting descriptor file you will see that the configuration parameter "stringParam" is defined with the value set to "uimaFIT". We could now instantiate an analysis engine using this descriptor file with a line of code like this:

```
AnalysisEngineFactory.createAnalysisEngine("GetStartedQuickAE");
```

But, of course, we really wouldn't want to do that now that we can instantiate analysis engines using the class definition as was done above!

This page, of course, did not demonstrate every feature of uimaFIT which provides support for annotating external resources, creating aggregate engines, running pipelines, testing components, among others.