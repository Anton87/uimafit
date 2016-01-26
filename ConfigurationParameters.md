<h1>Configuration Parameters</h1>



uimaFIT defines the `@ConfigurationParameter` annotation which can be used to annotate the fields of an analysis engine or collection reader. The purpose of this annotation is twofold:

  * injection of parameters from the UIMA context into fields
  * declaration of parameter metadata (mandatory, default value, description) which can be used to generate XML descriptors

In a regular UIMA component, parameters need to be manually extracted from the UIMA context, typically requiring a type cast.

```
class MyAnalysisEngine extends CasAnnotator_ImplBase {
  public static final String PARAM_SOURCE_DIRECTORY = "sourceDirectory";
  private File sourceDirectory;

  public void initialize(UimaContext context) throws ResourceInitializationException {
    sourceDirectory = new File((String) context.getConfigParameterValue(PARAM_SOURCE_DIRECTORY));
  }
}
```

The component has no way to declare a default value or to declare if a parameter is optional or mandatory. In addition, any documentation needs to be maintained in JavaDoc and in the XML descriptor for the component.

With uimaFIT, all this information can be declared in the component using the `@ConfigurationParameter` annotation.

**`@ConfigurationParameter`**
| **Parameter**    | **Description**                             | **Default** |
|:-----------------|:--------------------------------------------|:------------|
| _name_           | Parameter name                              | classname.field |
| _description_    | Description of the parameter                |             |
| _mandatory_      | Wether a value must be specified            | false       |
| _defaultValue_   | The defaul value if no value is specified   |             |


```
class MyAnalysisEngine extends org.uimafit.component.CasAnnotator_ImplBase {
  public static final String PARAM_SOURCE_DIRECTORY = "sourceDirectory";
  @ConfigurationParameter(name=PARAM_SOURCE_DIRECTORY, mandatory=true, defaultValue=".", 
    description="Directory to read the data from")
  private File sourceDirectory;
}
```

Note, that it is no longer necessary to implement the initialize() method. uimaFIT takes care of locating the parameter "sourceDirectory" in the UIMA context. It recognizes that the File class has a String constructor and uses that to instantiate a new File object from the parameter. If a mandatory parameter is not specified in the context, an exception is thrown.

The other information (defaultValue and description) are used when generating an UIMA component description from the class. It should be pointed out in particular, that uimaFIT does not make use of the default value when injecting parameters into fields. For this reason, it is possible to have a parameter that is mandatory but does have a default value. The default value is used as a parameter value when a component description is generated via the uimaFIT factories unless a parameter is specified in the factory call. If a component description in created manually without specifying a value for a mandatory parameter, uimaFIT will generate an exception.


The parameter injection mechanism is implemented in the `ConfigurationParameterInitializer` class. uimaFIT provides several base classes that already come with an `initialize()` method using the initializer:

  * `CasAnnotator_ImplBase`
  * `CasCollectionReader_ImplBase`
  * `CasConsumer_ImplBase`
  * `CasFlowController_ImplBase`
  * `CasMultiplier_ImplBase`
  * `JCasAnnotator_ImplBase`
  * `JCasCollectionReader_ImplBase`
  * `JCasConsumer_ImplBase`
  * `JCasFlowController_ImplBase`
  * `JCasMultiplier_ImplBase`
  * `Resource_ImplBase`

The `ConfigurationParameterInitializer` can also be used with shared resources:

```
class MySharedResourceObject implements SharedResourceObject {
  public static final String PARAM_VALUE = "Value";
  @ConfigurationParameter(name = PARAM_VALUE, mandatory = true)
  private String value;

  public void load(DataResource aData) throws ResourceInitializationException {
    ConfigurationParameterInitializer.initialize(this, aData);
  }
}
```

Fields that can be annotated with the `@ConfigurationParameter` annotation are any array or collection types of primitive types (`int`, `boolean`, `float`, `double`), any enum types, any types that define a constructor accepting a single `String` (e.g. `File`), as well as, fields of the types `Pattern` and `Locale`.