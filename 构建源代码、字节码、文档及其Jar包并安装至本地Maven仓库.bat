@echo off
chcp 65001 > nul
setlocal enabledelayedexpansion
REM ============================================================
REM  工具包构建脚本：编译源码、生成文档、打包Jar、安装至本地Maven仓库
REM  使用方法：将此脚本放在项目根目录下运行
REM ============================================================
REM ---- 1. 定位到脚本所在目录（即项目根目录） ----
cd /d "%~dp0"
set "PROJECT_DIR=%~dp0"
echo [INFO] 项目目录: %PROJECT_DIR%
REM ---- 配置变量 ----
set "SRC_DIR=%PROJECT_DIR%src"
set "BIN_DIR=%PROJECT_DIR%bin"
set "DOC_DIR=%PROJECT_DIR%doc"
set "VERSION=1.0.0"
set "GROUP_ID=custom"
set "ARTIFACT_ID=aio"
REM ---- 2. 清理旧的构建产物（可选） ----
echo.
echo [STEP 2] 清理旧的构建产物...
if exist "%BIN_DIR%" (
    rmdir /s /q "%BIN_DIR%"
    echo [INFO] 已删除旧的 bin 目录
)
if exist "%DOC_DIR%" (
    rmdir /s /q "%DOC_DIR%"
    echo [INFO] 已删除旧的 doc 目录
)
if exist "%PROJECT_DIR%%ARTIFACT_ID%.jar"          del /q "%PROJECT_DIR%%ARTIFACT_ID%.jar"
if exist "%PROJECT_DIR%%ARTIFACT_ID%-sources.jar"  del /q "%PROJECT_DIR%%ARTIFACT_ID%-sources.jar"
if exist "%PROJECT_DIR%%ARTIFACT_ID%-javadoc.jar"  del /q "%PROJECT_DIR%%ARTIFACT_ID%-javadoc.jar"
echo [INFO] 清理完成
REM ---- 3. 创建输出目录 ----
echo.
echo [STEP 3] 创建输出目录...
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"
if not exist "%DOC_DIR%" mkdir "%DOC_DIR%"
REM ---- 4. 编译源代码（字节码） ----
echo.
echo [STEP 4] 编译源代码...
dir /s /b "%SRC_DIR%\*.java" > "%PROJECT_DIR%sources.txt"
javac -encoding UTF-8 -d "%BIN_DIR%" @"%PROJECT_DIR%sources.txt"
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 编译失败，请检查源代码错误！
    del /q "%PROJECT_DIR%sources.txt"
    pause
    exit /b 1
)
del /q "%PROJECT_DIR%sources.txt"
echo [INFO] 编译成功
REM ---- 5. 打包字节码 Jar ----
echo.
echo [STEP 5] 打包字节码 Jar: %ARTIFACT_ID%.jar ...
jar cf "%PROJECT_DIR%%ARTIFACT_ID%.jar" -C "%BIN_DIR%" .
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 字节码 Jar 打包失败！
    pause
    exit /b 1
)
echo [INFO] 字节码 Jar 打包成功
REM ---- 6. 打包源代码 Jar ----
echo.
echo [STEP 6] 打包源代码 Jar: %ARTIFACT_ID%-sources.jar ...
jar cf "%PROJECT_DIR%%ARTIFACT_ID%-sources.jar" -C "%SRC_DIR%" .
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 源代码 Jar 打包失败！
    pause
    exit /b 1
)
echo [INFO] 源代码 Jar 打包成功
REM ---- 7. 生成 Javadoc ----
echo.
echo [STEP 7] 生成 Javadoc 文档...
javadoc -encoding UTF-8 -charset UTF-8 -d "%DOC_DIR%" -sourcepath "%SRC_DIR%" -subpackages aio
if %ERRORLEVEL% NEQ 0 (
    echo [WARN] Javadoc 生成有警告，继续执行...
)
echo [INFO] Javadoc 生成完成
REM ---- 8. 打包文档 Jar ----
echo.
echo [STEP 8] 打包文档 Jar: %ARTIFACT_ID%-javadoc.jar ...
jar cf "%PROJECT_DIR%%ARTIFACT_ID%-javadoc.jar" -C "%DOC_DIR%" .
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 文档 Jar 打包失败！
    pause
    exit /b 1
)
echo [INFO] 文档 Jar 打包成功
REM ---- 9. 安装至本地 Maven 仓库 ----
echo.
echo [STEP 9] 安装至本地 Maven 仓库...
REM 9a. 安装主 Jar（字节码）
echo [INFO] 安装主 Jar...
call mvn install:install-file ^
    -Dfile="%PROJECT_DIR%%ARTIFACT_ID%.jar" ^
    -DgroupId=%GROUP_ID% ^
    -DartifactId=%ARTIFACT_ID% ^
    -Dversion=%VERSION% ^
    -Dpackaging=jar
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 主 Jar 安装失败！
    pause
    exit /b 1
)
REM 9b. 安装源代码 Jar
echo [INFO] 安装源代码 Jar...
call mvn install:install-file ^
    -Dfile="%PROJECT_DIR%%ARTIFACT_ID%-sources.jar" ^
    -DgroupId=%GROUP_ID% ^
    -DartifactId=%ARTIFACT_ID% ^
    -Dversion=%VERSION% ^
    -Dclassifier=sources ^
    -Dpackaging=jar
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 源代码 Jar 安装失败！
    pause
    exit /b 1
)
REM 9c. 安装文档 Jar
echo [INFO] 安装文档 Jar...
call mvn install:install-file ^
    -Dfile="%PROJECT_DIR%%ARTIFACT_ID%-javadoc.jar" ^
    -DgroupId=%GROUP_ID% ^
    -DartifactId=%ARTIFACT_ID% ^
    -Dversion=%VERSION% ^
    -Dclassifier=javadoc ^
    -Dpackaging=jar
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] 文档 Jar 安装失败！
    pause
    exit /b 1
)
REM ---- 完成 ----
echo.
echo ============================================================
echo   构建全部完成！

echo   已安装至本地 Maven 仓库：
echo     groupId   : %GROUP_ID%
echo     artifactId: %ARTIFACT_ID%
echo     version   : %VERSION%
echo ============================================================
endlocal