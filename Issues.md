<h1>External Resources</h1>



# Context injection #

There are several issues with the context injection as it is currently implemented in uimaFIT:

  * The field types supported by the parameter injection mechanism are not extensible. To address this, we imagine to support Java Bean PropertyEditors ([Issue 79](https://code.google.com/p/uimafit/issues/detail?id=79))
  * Default parameters cannot be overwritten by sub-classes. ([Issue 69](https://code.google.com/p/uimafit/issues/detail?id=69))
  * No warning if the context contains a parameter that the component does not know ([Issue 7](https://code.google.com/p/uimafit/issues/detail?id=7))
  * Default names for parameters and resources should be the name of the annotated field ([Issue 70](https://code.google.com/p/uimafit/issues/detail?id=70), [Issue 71](https://code.google.com/p/uimafit/issues/detail?id=71))
  * Parameters and resources should be mandatory by default ([Issue 72](https://code.google.com/p/uimafit/issues/detail?id=72))

## Relation to other dependency injection frameworks ##

JSR 250 defines the `@Resource` annotation. This could be used as an alternative for `@ConfigurationParameter` and `@ExternalResource`, but it does not support all the information provided by the two and provides some that we do not need in UIMA.

| `@Resource`         | `@ConfigurationParameter` | `@ExternalResource` |
|:--------------------|:--------------------------|:--------------------|
| name                | name                      | key                 |
| type                | --                        | api                 |
| shareable           | --                        |                     |
| mappedName          | --                        | --                  |
| description         | description               | --                  |
| authenticationType  | --                        | --                  |
| --                  | mandatory                 | mandatory           |
| --                  | defaultValue              | --                  |

The Spring Framework defines an annotation [@Required](http://static.springsource.org/spring/docs/2.0.x/api/org/springframework/beans/factory/annotation/Required.html) which can probably be used in conjunction with `@Resource` to incidate that a value is mandatory (I didn't try this out, but I expect it should work). [Apache Camel](http://camel.apache.org/maven/current/camel-core/apidocs/org/apache/camel/spi/Required.html) seems to define a similar annotation.

We do not know of any substitute for defaultValue. However, this information is not used during injection, only during descriptor generation. It may be sensible to have a separate annotation (like `@Required` for 'mandatory') if a JSR 250 compatible approach is desired.