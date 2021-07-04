# GitHub Avatar Generator

<div>
    <a href="https://github.com/FlyAndNotDown/github-avatar-generator/actions"><img alt="Building Status" src="https://img.shields.io/github/workflow/status/FlyAndNotDown/github-avatar-generator/Java%20CI%20with%20Gradle?style=for-the-badge"/></a>
    <a><img alt="Building Status" src="https://img.shields.io/github/repo-size/FlyAndNotDown/github-avatar-generator?style=for-the-badge"/></a>
    <a href="https://github.com/FlyAndNotDown/github-avatar-generator/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/FlyAndNotDown/github-avatar-generator?style=for-the-badge"/></a>
</div>

<br/>

Github avatar generator is a java application which is used to generate github-style avatars. There is a sample:

<img src="docs/img/sample1.png" width="200"/>

# Build

Installing at first, here is home page of Gradle: [Gradle]([Gradle](https://gradle.org/install/#manually)).

Using Gradle to build the application:

```shell
./gradlew build
```

Install application and dependencies:

```shell
./gradlew install
```

# Usage

After building the application, you can execute it like this:

```shell
.\build\install\gh-avatar-generator\bin\gh-avatar-generator -s amazing_seed -o sample.png
```

The arguments were specificed as follow:

* `-s`: seed string, used to generate random color and block info
* `-o`: output file name

# Gallery

<div>
    <img src="docs/img/sample1.png" width="200"/>
    <img src="docs/img/sample2.png" width="200"/>
    <img src="docs/img/sample3.png" width="200"/>
</div>
<div>
    <img src="docs/img/sample4.png" width="200"/>
    <img src="docs/img/sample5.png" width="200"/>
    <img src="docs/img/sample6.png" width="200"/>
</div>
