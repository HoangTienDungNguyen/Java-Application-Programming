:: ---------------------------------------------------------------------
:: Battleship Game - SCRIPT
:: SCRIPT CST8221 - Fall 2023
:: ---------------------------------------------------------------------
:: Begin of Script
:: ---------------------------------------------------------------------

CLS

:: LOCAL VARIABLES ....................................................

:: Paths
SET LIBDIR=lib
SET SRCDIR=src
SET BINDIR=bin
SET DOCDIR=doc
SET ASSETSDIR=assets
SET ICONSDIR=icons
SET DISTDIR=dist

:: File names
SET BINERR=battleship-javac.err
SET JARNAME=BattleshipGame.jar
SET JAROUT=battleship-jar.out
SET JARERR=battleship-jar.err
SET DOCERR=battleship-javadoc.err

:: Main class (change to your main class file)
SET MAINCLASSSRC=src\Game.java
SET MAINCLASSBIN=Game

@echo off

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                                                                   @"
ECHO "@                   #       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@                  ##       @  A L G O N Q U I N  C O L L E G E  @  @"
ECHO "@                ##  #      @    JAVA APPLICATION PROGRAMMING    @  @"
ECHO "@             ###    ##     @         F A L L  -  2 0 2 3        @  @"
ECHO "@          ###    ##        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  @"
ECHO "@        ###    ##                                                  @"
ECHO "@        ##    ###                 ###                              @"
ECHO "@         ##    ###                ###                              @"
ECHO "@           ##    ##               ###   #####  ##     ##  #####    @"
ECHO "@         (     (      ((((()      ###       ## ###   ###      ##   @"
ECHO "@     ((((     ((((((((     ()     ###   ######  ###  ##   ######   @"
ECHO "@        ((                ()      ###  ##   ##   ## ##   ##   ##   @"
ECHO "@         ((((((((((( ((()         ###   ######    ###     ######   @"
ECHO "@         ((         ((           ###                               @"
ECHO "@          (((((((((((                                              @"
ECHO "@   (((                      ((                                     @"
ECHO "@    ((((((((((((((((((((() ))                                      @"
ECHO "@         ((((((((((((((((()                                        @"
ECHO "@                                                                   @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"

ECHO "[BATTLESHIP GAME SCRIPT ---------------------]"

ECHO "1. Compiling ......................"
javac -encoding UTF-8 -Xlint -cp ".;%SRCDIR%;%LIBDIR%/*" %SRCDIR%\*.java -d %BINDIR% 2> %BINERR%

:: Check if compilation was successful
if %errorlevel% neq 0 (
    echo Compilation failed. Check the error file %BINERR% for details.
    pause
    exit /b %errorlevel%
)

ECHO "2. Creating Jar ..................."
if not exist %DISTDIR% (
    mkdir %DISTDIR%
)
cd %BINDIR%
jar cvfe ../%DISTDIR%/%JARNAME% %MAINCLASSBIN% . -C ../%ASSETSDIR% . -C ../%ICONSDIR% . > ../%JAROUT% 2> ../%JARERR%

:: Check if JAR creation was successful
if %errorlevel% neq 0 (
    echo JAR creation failed. Check the error file %JARERR% for details.
    pause
    exit /b %errorlevel%
)

ECHO "3. Creating Javadoc ..............."
cd ..
javadoc -encoding UTF-8 -cp ".;%BINDIR%;%LIBDIR%/*" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages . 2> %DOCERR%

:: Check if Javadoc creation was successful
if %errorlevel% neq 0 (
    echo Javadoc creation failed. Check the error file %DOCERR% for details.
    pause
    exit /b %errorlevel%
)

ECHO "4. Running Jar ...................."
cd %DISTDIR%
start java -jar %JARNAME%

:: Check if JAR running was successful
if %errorlevel% neq 0 (
    echo JAR running failed. Check the console output for details.
    pause
    exit /b %errorlevel%
)
ECHO "JAR running successful."

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

:: ---------------------------------------------------------------------
:: End of Script
:: ---------------------------------------------------------------------
