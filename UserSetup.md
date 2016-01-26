The following instructions describe how to add uimaFIT to your project's classpath.

# Maven Users #

If you use Maven, then uimaFIT can be added to your project by simply adding uimaFIT as a project dependency by adding the following snippet of XML to your pom.xml file:

```
<dependency>
  <groupId>org.uimafit</groupId>
  <artifactId>uimafit</artifactId>
  <version>(version-number-here)</version>
</dependency>
```

Please see the project [front page](http://uimafit.googlecode.com) for the current version number.  uimaFIT distributions are hosted by Maven Central and so no repository needs to be added to your pom.xml file.

# Non-maven Users #

If you do not build with Maven, then download uimaFIT from the [downloads page](http://code.google.com/p/uimafit/downloads/list).  The file name should be uimafit-(version)-plus-dependencies.zip.  Download and unpack this file.  The contents of the resulting upacked directory will contain a directory called "lib".  Add all of the files in this directory to your classpath.