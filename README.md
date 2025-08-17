development enviroment

https://github.com/sarang628/TorangToml.git

```
/AndroidStudioProjects/TorangDatabase/gradle/

git submodule add https://github.com/sarang628/TorangToml.git
Cloning into '/Users/sarangyang/AndroidStudioProjects/TorangDatabase/gradle/TorangToml'...
remote: Enumerating objects: 275, done.
remote: Counting objects: 100% (113/113), done.
remote: Compressing objects: 100% (42/42), done.
remote: Total 275 (delta 39), reused 108 (delta 35), pack-reused 162 (from 1)
Receiving objects: 100% (275/275), 30.46 KiB | 10.15 MiB/s, done.
Resolving deltas: 100% (92/92), done.
```

settings

```
dependencyResolutionManagement{
...
versionCatalogs {
        create("libs") {
            from(files("gradle/TorangToml/libs.versions.toml"))
        }
    }
...
}
```

안드로이드 학습을 해야겠다고 생각

안드로이드 아키텍처 영상 시청
https://developer.android.com/topic/architecture/intro


Now In Android 프로젝트를 보고 database를 옮겨야겠다고 생각

https://github.com/android/nowinandroid/tree/main/core/database

기존 프로젝트에 영향을 덜 받게 하려면

패키지명을 일단 그대로 옮겨서 데이터베이스만 마이그레이션 해보기


마이그레이션 하면서 얻은 것

서버 API에서 받은 모델을 그대로 DB에 넣거나

데이터 변환하는데 사용하고 있었음.

데이터 베이스 모듈에서는 서버에서 받은 데이터 타입에 관해서는 알 필요가 없음.




now in android 따라하기


# 패키지명 
앱 패키지.core.database
