:: ---------------------------------------------------------------------
:: BATTLESHIP GAME - BUILD SCRIPT
:: ---------------------------------------------------------------------
:: Begin of Script
:: ---------------------------------------------------------------------

CLS

:: LOCAL VARIABLES ....................................................

SET SRCDIR=src
SET BINDIR=bin
SET LIBDIR=lib
SET ASSETDIR=assets
SET ICONDIR=icons
SET BINERR=battleship-javac.err
SET JARNAME=BattleshipGame.jar
SET JAROUT=battleship-jar.out
SET JARERR=battleship-jar.err
SET MAINCLASSBIN=SplashScreen

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

ECHO "[BUILD SCRIPT ---------------------]"

ECHO "1. Compiling ......................"
javac -Xlint -encoding UTF-8 -cp ".;%SRCDIR%;%LIBDIR%/*" %SRCDIR%\*.java -d %BINDIR% 2> %BINERR%
if %errorlevel% neq 0 (
    echo Compilation failed. Check %BINERR% for details.
    pause
    exit /b %errorlevel%
)

ECHO "2. Creating Jar ..................."
cd %BINDIR%
jar cvfe ../%JARNAME% %MAINCLASSBIN% *.class -C ../%ASSETDIR% . -C ../%ICONDIR% . > ../%JAROUT% 2> ../%JARERR%
if %errorlevel% neq 0 (
    echo JAR creation failed. Check %JARERR% for details.
    pause
    exit /b %errorlevel%
)
cd ..

ECHO "3. Running Jar ...................."
java -Dfile.encoding=UTF-8 -jar %JARNAME%
if %errorlevel% neq 0 (
    echo JAR running failed. Check the console output for details.
    pause
    exit /b %errorlevel%
)

ECHO "[END OF SCRIPT -------------------]"
ECHO "                                   "
@echo on

:: ---------------------------------------------------------------------
:: End of Script
:: ---------------------------------------------------------------------
