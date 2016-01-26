<table>
<tr>
<td valign='top'>

<table cellpadding='4' border='2'><tr><td>
<b>Newsflash: Apache uimaFIT 2.0.0 has been released.</b> This marks the end of active development on this project. Further  versions of uimaFIT will be released under the umbrella of the Apache UIMA project. Please direct any questions to the <a href='http://uima.apache.org/mail-lists.html'>UIMA users mailing list</a> and any issue reports to the <a href='https://issues.apache.org/jira'>Apache issue tracker</a>.<br>
</td></tr></table>

<b>2013-08-31: <a href='http://uima.apache.org/uimafit.html'>Apache uimaFIT version 2.0.0</a> is released! (<a href='http://uima.apache.org/d/uimafit-2.0.0/README.txt'>Release Notes</a>, <a href='http://uima.apache.org/d/uimafit-2.0.0/issuesFixed/jira-report.html'>Changes</a>)</b>

To see how uimaFIT works please see the <a href='GettingStarted.md'>getting started</a> page, <a href='TwoSidesOfUimaFIT.md'>conceptual overview</a>, or the <a href='Documentation.md'>documentation</a>.  uimaFIT requires Java 1.5 or higher and UIMA 2.4.0 or higher.<br>
<br>
Configuring UIMA components is generally achieved by creating XML descriptor files which tell the framework at runtime how components should be instantiated and deployed. These XML descriptor files are very tightly coupled with the Java implementation of the components they describe. We have found that it is very difficult to keep the two consistent with each other especially when code refactoring is very frequent.   uimaFIT provides Java annotations for describing UIMA components which can be used to directly describe the UIMA components in the code. This greatly simplifies refactoring a component definition (e.g. changing a configuration parameter name). It also makes it possible to generate XML descriptor files as part of the build cycle rather than being performed manually in parallel with code creation.  uimaFIT also makes it easy to instantiate UIMA components without using XML descriptor files at all by providing a number of convenience factory methods which allow programmatic/dynamic instantiation of UIMA components. This makes uimaFIT an ideal library for testing UIMA components because the component can be easily instantiated and invoked without requiring a descriptor file to be created first.  uimaFIT is also helpful in research environments in which programmatic/dynamic instantiation of a pipeline can simplify experimentation. For example, when performing 10-fold cross-validation across a number of experimental conditions it can be quite laborious to create a different set of descriptor files for each run or even a script that generates such descriptor files.  uimaFIT is type system agnostic and does not depend on (or provide) a specific type system.<br>
<br>
<b><a href='http://uima.apache.org/uimafit.html'>Apache uimaFIT 2.0.0</a> has been released. Please consider upgrading using the <a href='http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.uima%22%20AND%20uimafit*'>new Maven coordinates</a>.</b>
Legacy uimaFIT continues to be available via <a href='http://search.maven.org/#search%7Cga%7C1%7Cuimafit'>Maven Central</a>.  If you use Maven as your build tool, then you can add uimaFIT as a dependency in your <code>pom.xml</code> file:<br>
<br>
<pre><code>&lt;dependency&gt;<br>
  &lt;groupId&gt;org.uimafit&lt;/groupId&gt;<br>
  &lt;artifactId&gt;uimafit&lt;/artifactId&gt;<br>
  &lt;version&gt;1.4.0&lt;/version&gt;<br>
&lt;/dependency&gt;<br>
</code></pre>
</td>
<td valign='top'>
<wiki:gadget url="http://www.ohloh.net/p/483931/widgets/project_basic_stats.xml" width="360" height="230" border="1"/><br />
<table>
<tr>
<td valign='middle'><wiki:gadget url="http://www.ohloh.net/p/483931/widgets/project_users_logo.xml" height="43" border="0"/><br>
</td>
</tr>
</table>
</td>
</tr>
</table>

#### Who is using uimaFIT? ####

uimaFIT is an integral part of [ClearTK](http://code.google.com/p/cleartk/) and [DKPro Core](http://code.google.com/p/dkpro-core-asl/admin).

#### Citing uimaFIT ####

If you use uimaFIT to support academic research, then please cite the following [paper](http://aclweb.org/anthology-new/W/W09/W09-1501.pdf) as noted in the "REFERENCE" section of the project's [README file](http://code.google.com/p/uimafit/source/browse/trunk/uimaFIT/README).

If we may list your project as a reference for uimaFIT, please contact us via the [uimaFIT users group](http://groups.google.com/group/uimafit-users).