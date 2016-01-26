Here is the current (but not perfect) procedure for creating a release:

# Prerequisites #

  * GnuPG installed and configured and your public key uploaded to one of the key servers. uimaFIT is deployed to Maven Central, which requires that digitial signatures are in place.
  * Maven 2.2.1+
  * `settings.xml` file set up as explained in the [Sonatype OSS Maven Repository Usage Guide](https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide)

# CHANGES and README #

Be sure that the CHANGES and README files are up-to-date. At least all fixed issues that are tagged with the milestone that is designated to be released should be in the file.

# Maven release #

Prepare the release.

```
$ cd uimafit-parent
$ mvn release:prepare
```

The JavaDoc has been regenerated during the prepare step, but not committed. Commit it and then move it to the tag:

```
$ svn commit -m "Updated JavaDoc" apidocs
$ svn delete -m "Remove old JavaDoc" https://uimafit.googlecode.com/svn/tags/uimafit-parent-VERSION/apidocs
$ svn copy -m "Add release JavaDoc" https://uimafit.googlecode.com/svn/trunk/apidocs https://uimafit.googlecode.com/svn/tags/uimafit-parent-VERSION/apidocs
```

Now continue with the perform step.

```
$ mvn release:perform
```

Build and upload the distribution archive to Google Code (the release checkout is still there from the perform step).

```
$ cd target/checkout/uimaFIT
$ mvn assembly:assembly
```

Now the archive is built in target/uimafit-VERSION-plus-dependencies.zip - upload it with your browser.

If you run into login or signing problems, try specificing your username, password and keyname like this. Mind that if you do that on a machine with other users, it may leak your password via the process list and shell history. So better do this on a private machine.

```
$ mvn -Dusername=GOOGLECODE_USERNAME -Dpassword=GOOGLECODE_PASSWORD -Darguments="-Dkeyname=GOOGLECODE_USERNAME" release:prepare
```

If "mvn release:perform" appears to hang check to see if it is asking for your GPG password.  Enter is carefully if so.

# Deploy to Maven Central via Sonatype OSS #

  * Go to the [Nexus OSS repository](https://oss.sonatype.org) and log in with your Sonatype JIRA account.
  * Open the **Staging Repositories** page, select **uimaFIT** from the list, select the release you have just performed from the lower part
  * Select it and click **Close** in the action bar above the list. Enter a meaningful comment, e.g. "uimaFIT 0.9.14 release".
  * Select it again and click **Release**. Again enter e meaningful comment, e.g. "uimaFIT 0.9.14 release".
  * wait an hour or so for the sync process to run.  Check that the new version is available [here](http://repo1.maven.org/maven2/org/uimafit/uimafit/).

# Update googlecode project #

**Javadoc**:
  * Update the Javadoc link on the main page via the [admin interface](http://code.google.com/p/uimafit/admin)
  * Also update the Javadoc link in the wiki at [Documentation](Documentation.md)
  * ... both should point to the new release, e.g. to
```
http://uimafit.googlecode.com/svn/tags/uimafit-parent-0.9.14/uimaFIT/apidocs/index.html
```

**Project artifacts**: The new artifacts have not yet been uploaded to googlecode via the release steps.  Uploading the new downloadable files can be done by clicking on the Downloads tab above and following the instructions.  The files should be located in the target directory.

**Update current version**: One final thing to do is edit the front page to have the correct xml for a project's pom file - i.e. edit the snippet
```
<version>0.9.14</version>
```
so that it is up-to-date.

# Send an announcement #
After all of the above steps are complete, send an announcement to uimafit-users@googlegroups.com. Here is a template for the email:

```
The uimaFIT development team is pleased to announce the release of uimaFIT 1.3.0.  

Changes in brief

- ...

To view the release notes for this release, please visit:
http://code.google.com/p/uimafit/wiki/ReleaseNotes_1_3_0

To view the full list of changes for this release, please visit:
http://code.google.com/p/uimafit/source/browse/tags/uimafit-parent-1.3.0/uimaFIT/CHANGES

To view the readme for this release, please visit:
http://code.google.com/p/uimafit/source/browse/tags/uimafit-parent-1.3.0/uimaFIT/README

Please visit the project home page for information about how to add uimaFIT as a dependency to your Maven built project:
http://uimafit.googlecode.com

If you do not use Maven, the latest version of uimaFIT can be obtained from the downloads page:
http://code.google.com/p/uimafit/downloads/list
```