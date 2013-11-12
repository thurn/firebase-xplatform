This is an implementation of the Firebase Java interface for Google Web
Toolkit. It is an incomplete work-in-progress, but still largely functional.
There are no special runtime dependencies, but tests depend on my common base
class library (https://github.com/thurn/shared-gwttestcase). This needs to be
added as a separate Eclipse project and then depended on by the firebase
project in order to run tests. There are two Eclipse run configurations for
tests, one to run the tests in JS, and another to run the tests in Java against
the actual Firebase java jarfile (in order to verify that the behavior is the
same). This depends on having firebase.jar available on the classpath, of
course.

When you use this library in javascript, you need to have firebase.js already
injected into the GWT iFrame (injecting it at the top-level will not work!).
The best way to accomplish this is via a GWT ScriptInjector.

# Known Limitations #

By definition, this library can only support the common subset of functionality
available between the three Firebase client libraries it wraps. That means that
several methods from the regular Java API are not available. The Java types
that are supported are: List, Map, Long, Double, String, and Boolean. Because
Javascript only has one numeric type, the original type associated with a
number you insert is lost. For simplicity, all numbers are returned as
instances of `java.lang.Long` if they can be represented as such, otherwise
`java.lang.Double` is used. The variants of `getValue()` on `DataSnapshot` that take
a type literal as an argument are simply implemented as casts - unlike in
firebase.jar, `getValue(Integer.class)` *will not* work.

Because of how transactions work in Javascript, the methods on MutableData
other than getValue and setValue do not work there. Similarly, because of some
omissions in the Objective C API, a few other methods don't work:

* `Query#getRef()`
* `OnDisconnect#cancel()`

This isn't an exhaustive list of the things that won't work cross-platform,
merely the ones that you're most likely to run into.

# Testing #

You should be able to import a clone of the project as an Eclipse project via
Import > Existing Projects into Workspace. You then also need to clone
[shared test case](http://github.com/thurn/shared-test-case) and import it as a separate Eclipse project,
since it's a depedency of the tests for firebase. The tests also require the
Google Eclipse plugin to be installed to get the Google Web Toolkit SDK.

## Java Tests ##

The goal of the Java tests is to run the test suite against the actual Firebase
java implementation to verify that they're correct. In the Eclipse project,
firebase.jar should be first on the build path, so running the tests should
just work. There should be a configuration in the run menu called
"FirebaseTest - Java" which does this.

## Javascript Tests ##

The javascript tests are for the GWT implementation. This should also work
automatically, with an entry in the run menu called "FirebaseTest - JS". The
tests don't work in GWT Production Mode, however. I think this is because of
some problems with the setUp method & injecting firebase.js via a
`ScriptInjector`.

There's also a really simple example GWT application configured in
`TestEntryPoint.java`. You can run this via Run As > Web Application.

## Objective C Tests ##

If you import Firebase.xcodeproj into XCode, you should get a project with two
targets. One of the targets is called FirebaseJUnit, and it should run on your
Mac (you need a Mac to run these tests). The Objective C Firebase wrapper
relies on j2objc to convert Java into Objective C code. You need to download
j2objc and extract the zip file to `/usr/local/j2objc` (I eventually intend to
stop hardcoding this location).

Assuming all goes according to plan, the tests
should compile and run. You will get a bunch of "SSLHandshake failed" errors,
but you can ignore them (they're just because the tests try and connect to a
domain where there's no actual Firebase running). There's also a simple
application target that you can run on an iOS simulator, which performs a 
simple task using the API.

# License #

Unless otherwise noted in a source file, code is released under the
[Creative Commons Zero License](http://creativecommons.org/publicdomain/zero/1.0/)
