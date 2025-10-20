<img width="82" align="left" src="https://raw.githubusercontent.com/kryptonbutterfly/linearAlgebra/master/md/icon.svg" alt="Icon linearAlgebra"/>

# linearAlgebra [![Maven Package](https://github.com/kryptonbutterfly/linearAlgebra/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/kryptonbutterfly/linearAlgebra/actions/workflows/maven-publish.yml)

### Classes:

 * [HyperplaneHesse](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/geometry/HyperplaneHesse.java): A double backed n dimensional [hyperplane](https://en.wikipedia.org/wiki/Hyperplane) in [hesse normal form](https://en.wikipedia.org/wiki/Hesse_normal_form).
 * [Line](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/geometry/Line.java): A generic n dimensional [line](https://en.wikipedia.org/wiki/Line_(geometry)). <br/>
    Defined by a position vector and a directional vector [see (de_DE)](https://de.wikipedia.org/wiki/Gerade#Punkt-Richtungs-Gleichung).
 * [LineSegment2D](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/geometry/LineSegment2D.java): A double backed 2 dimensional line segment.<br/>
    Defined by a start and end position vector.

 * Vector implementations:

dimensions | 2  | 3  | 4  | n where n ∊ ℕ
:--------- | :- | :- | :- | :-
 `int`            | [Vec2i](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_int/Vec2i.java) | [Vec3i](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_int/Vec3i.java) | – | [VecNi](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_int/VecNi.java)
 `long`           | [Vec2l](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_long/Vec2l.java) | [Vec3l](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_long/Vec3l.java) | – | [VecNl](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_long/VecNl.java)
 `float`          | [Vec2f](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_float/Vec2f.java) | [Vec3f](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_float/Vec3f.java) | – | [VecNf](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_float/VecNf.java)
 `double`         | [Vec2d](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_double/Vec2d.java) | [Vec3d](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_double/Vec3d.java) | [Vec4d](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_double/Vec4d.java) | [VecNd](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_double/VecNd.java)

All vectors implement [IVector](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/IVector.java).<br/>
All `int` backed vectors implement [IVecI](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_int/IVecI.java).<br/>
All `long` backed vectors implement [IVecL](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_long/IVecL.java).<br/>
All `float` backed vectors implement [IVecF](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_float/IVecF.java).<br/>
All `double` backed vectors implement [IVecD](https://github.com/kryptonbutterfly/linearAlgebra/blob/master/src/kryptonbutterfly/math/vector/_double/IVecD.java).


## Getting the latest release

```xml
<repository>
  <id>github</id>
  <url>https://maven.pkg.github.com/kryptonbutterfly/maven-repo</url>
</repository>
```

```xml
<dependency>
  <groupId>kryptonbutterfly</groupId>
  <artifactId>linear_algebra</artifactId>
  <version>4.0.1</version>
</dependency>
```

## Download

java version | library version | Download
:----------: | :-------------: | :-------
18+          | 4.0.0           | [linear_algebra-4.0.0.jar](https://github.com/kryptonbutterfly/linearAlgebra/releases/download/v4.0.0/linear_algebra-4.0.0.jar)
18+          | 3.0.0           | [linear_algebra-3.0.0.jar](https://github.com/kryptonbutterfly/linearAlgebra/releases/download/v3.0.0/linear_algebra-3.0.0.jar)
18+          | 2.0.0           | ——
18+          | 1.1.0           | ——