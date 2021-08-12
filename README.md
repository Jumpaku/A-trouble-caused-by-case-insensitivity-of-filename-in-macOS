# A trouble caused by case-insensitivity of filename in macOS

Although I used the same docker image to run docker containers in both local and remote environment, the behaviors were different.
A docker container in macOS succeeded to run gradle build.
Another docker container in GitHub Actions failed to run it.


## Problem

https://github.com/Jumpaku/A-trouble-caused-by-case-insensitivity-of-filename-in-macOS/tree/a7656c48e3eae79206c08621f8576375fa3542db

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

There is a typo `testData.json`, which should be `TestData.json`, in `src/test/kotlin/jumpaku/ConcatenateTest.kt`.
Unfortunately, unit tests run by the docker container in macOS do not detect this typo.
This is because macOS is not case-sensitive regarding filename, and the docker container runs on macOS ignores case of filenames of mounted files.
However, unit tests run by the docker container run by GitHub Actions detects the typo and fails.

## Fix

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

BUILD SUCCESSFUL

...
```

## Cause


