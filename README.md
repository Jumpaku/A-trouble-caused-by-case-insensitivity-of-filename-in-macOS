# A trouble caused by case-insensitivity of filename in macOS

Although I used the same docker image to run docker containers in both local and remote environment, the behaviors were different.
A docker container in macOS succeeded to run gradle build.
Another docker container in GitHub Actions failed to run it.

## Problem

https://github.com/Jumpaku/A-trouble-caused-by-case-insensitivity-of-filename-in-macOS/tree/with-problem

### macOS Big Sur Version 11.2.3

#### Execution command

```shell
docker-compose run build
```

#### Result

```
...

BUILD SUCCESSFUL in 1m 0s
```

### GitHub Actions

#### Result

```
...

> Task :test FAILED
jumpaku.ConcatenateTest > testConcatenate FAILED
    java.lang.NullPointerException at ConcatenateTest.kt:15
1 test completed, 1 failed

FAILURE: Build failed with an exception.
6 actionable tasks: 6 executed

...

BUILD FAILED in 42s
1
Error: Process completed with exit code 1.
```

## Cause

There is a typo `testData.json`, which should be `TestData.json`, [at line 15 in `src/test/kotlin/jumpaku/ConcatenateTest.kt`](https://github.com/Jumpaku/A-trouble-caused-by-case-insensitivity-of-filename-in-macOS/blob/a7656c48e3eae79206c08621f8576375fa3542db/src/test/kotlin/jumpaku/ConcatenateTest.kt#L15).
Unfortunately, a unit test run by the docker container on macOS does not detect this typo.
This is because macOS is not case-sensitive regarding filename, and the docker container on macOS ignores case of filenames which are mounted.
However, a unit test run by the docker container on GitHub Actions detects the typo and fails.

## Fix

https://github.com/Jumpaku/A-trouble-caused-by-case-insensitivity-of-filename-in-macOS/tree/fixed

### macOS Big Sur Version 11.2.3

#### Execution command

```shell
docker-compose run build
```

#### Result

```
...

BUILD SUCCESSFUL in 1m 0s
```

### GitHub Actions

#### Result

```
...

BUILD SUCCESSFUL in 39s

...
```

