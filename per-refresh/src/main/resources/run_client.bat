set CLASSPATH=.;.\per-refresh.jar;.\lib\commons-beanutils-1.9.2.jar;.\lib\commons-codec-1.6.jar;.\lib\commons-collections-3.2.1.jar;.\lib\commons-lang-2.6.jar;.\lib\commons-lang3-3.3.2.jar;.\lib\commons-logging-1.1.1.jar;.\lib\ezmorph-1.0.6.jar;.\lib\fastjson-1.2.6.jar;.\lib\htmllexer-2.1.jar;.\lib\htmlparser-2.1.jar;.\lib\httpclient-4.3.2.jar;.\lib\httpcore-4.3.2.jar

set JAVA=%JAVA_HOME%\bin\java

"%JAVA%" -classpath "%CLASSPATH%" per.refresh.run.RankTask

pause